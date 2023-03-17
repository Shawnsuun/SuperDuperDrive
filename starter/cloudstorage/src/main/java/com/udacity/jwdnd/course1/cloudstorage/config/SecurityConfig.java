package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Managing User Access with Spring Security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Tell Spring to use our AuthenticationService to check user logins
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    /**
     * Used to configure the HttpSecurity object by chaining methods to express security requirements
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Allows all users to access the /signup page, as well as the css and js files.
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated();

        //Generates a login form at /login and allows anyone to access it.
        http.formLogin()
                .loginPage("/login")
                .permitAll();

        //Redirects successful logins to the /home page.
        http.formLogin()
                .defaultSuccessUrl("/home", true);

        //logout.
        http.logout()
                .logoutUrl("/logout");
    }
}
