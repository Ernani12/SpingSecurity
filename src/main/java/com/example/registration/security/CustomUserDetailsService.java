package com.example.registration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.registration.entity.Role;
import com.example.registration.entity.User;
import com.example.registration.repository.RoleRepository;
import com.example.registration.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

      @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoleIds()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    // Método para converter IDs de papéis em Collection<GrantedAuthority>
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roleIds) {
        // Obter os papéis a partir dos IDs
        List<Role> roles = roleRepository.findAllById(roleIds);
        
        // Mapear papéis para authorities
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

