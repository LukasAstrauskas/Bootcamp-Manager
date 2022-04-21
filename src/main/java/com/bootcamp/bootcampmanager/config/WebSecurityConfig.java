package com.bootcamp.bootcampmanager.config;

import com.bootcamp.bootcampmanager.config.encoder.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return Encoder.get();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/css/**").permitAll()
                .antMatchers( "/js/**").permitAll()
                .antMatchers("/bootcamps").hasRole("ADMIN")
                .antMatchers("/bootcamp").hasRole("ADMIN")
                .antMatchers("/link-lecturer").hasRole("ADMIN")
                .antMatchers("/enroll-lecturer").hasRole("ADMIN")
                .antMatchers("/link-student").hasRole("ADMIN")
                .antMatchers("/enroll-student").hasRole("ADMIN")
                .antMatchers("/link-task").hasRole("ADMIN")
                .antMatchers("/tasks").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                .antMatchers("/task").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                .antMatchers("/students").hasRole("LECTURER")
                .antMatchers("/student").hasRole("LECTURER")
                .antMatchers("/student-task").hasRole("STUDENT")
                .antMatchers("/student-homepage").hasRole("STUDENT")
                .antMatchers("/lecturer-tasks").hasRole("LECTURER")
                // .antMatchers("/groups").hasAnyRole("ADMIN", "LECTURER")
                // .antMatchers("/group").hasAnyRole("ADMIN", "LECTURER")
                // .antMatchers("/courses").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                // .antMatchers("/course").hasAnyRole("ADMIN", "LECTURER", "STUDENT")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and().csrf().disable();
    }
}
