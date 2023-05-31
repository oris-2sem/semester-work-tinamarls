package ru.itis.delivery.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.itis.delivery.security.handler.CustomAuthenticationFailureHandler;
import ru.itis.delivery.security.handler.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //    @Autowired
//    @Qualifier("customUserDetailsService")
    private final UserDetailsService userDetailsService;

    //    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final CustomAuthenticationSuccessHandler successHandler;

    private final CustomAuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()

                .authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/bag/**").permitAll()
                .antMatchers("/product").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/profile/**").hasAuthority("CLIENT")
                .antMatchers("/order/**").hasAuthority("CLIENT")
                .antMatchers("/courier/**").hasAuthority("COURIER")

                .and()

                .formLogin()
                .loginPage("/login")
                .usernameParameter("login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()

                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());

    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            if (request.getUserPrincipal() == null) {
                response.sendRedirect("/for-anonymous");
            } else {
                response.sendRedirect("/no-access");
            }
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            if (request.getUserPrincipal() == null) {
                response.sendRedirect("/for-anonymous");
            } else {
                response.sendRedirect("/no-access");
            }
        };
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
