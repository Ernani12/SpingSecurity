# Aplicação de Registro e Login com Spring Security em MongoDB

## Visão Geral
Este projeto é uma demonstração de como implementar um sistema de registro e login utilizando **Spring Security** em uma aplicação **Spring Boot**, com armazenamento de dados em **MongoDB**. A aplicação permite que usuários se registrem e façam login, com diferentes níveis de acesso com base em suas funções.

---

## Funcionalidades

| Funcionalidade      | Descrição                                                       |
|---------------------|---------------------------------------------------------------|
| **Registro de Usuário** | Usuários podem se registrar na aplicação.                 |
| **Login**           | Usuários podem fazer login utilizando suas credenciais.       |
| **Controle de Acesso** | Diferentes permissões para usuários com base em suas funções (por exemplo, apenas usuários com a função "ADMIN" podem acessar certas páginas). |
| **Logout**          | Usuários podem sair da aplicação de forma segura.             |

---

## Configuração do Spring Security

A configuração do Spring Security é feita através da classe de configuração onde o `UserDetailsService` é injetado e as regras de segurança são definidas.

### Código da Configuração

```java
@Autowired
private UserDetailsService userDetailsService;

@Bean
public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((authorize) ->
            authorize.requestMatchers("/register/**").permitAll()
                .requestMatchers("/index").permitAll()
                .requestMatchers("/users").hasRole("ADMIN")
        ).formLogin(
            form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/users")
                .permitAll()
        ).logout(
            logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
        );
    return http.build();
}
```

## Explicação da Configuração

| Componente              | Descrição                                                                 |
|-------------------------|---------------------------------------------------------------------------|
| **`@Autowired`**        | Injeção de dependência para o serviço que carrega os detalhes do usuário. |
| **`PasswordEncoder`**   | Um bean para codificar senhas utilizando BCrypt, garantindo a segurança das credenciais. |
| **`SecurityFilterChain`** | Configuração das regras de segurança, incluindo o que é permitido para usuários não autenticados e como o login e logout devem ser tratados. |

