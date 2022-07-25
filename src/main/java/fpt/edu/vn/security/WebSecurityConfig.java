package fpt.edu.vn.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpStatusRequestRejectedHandler;
import org.springframework.security.web.firewall.RequestRejectedHandler;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/detail/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .antMatchers("/file/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .antMatchers("/forums/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .antMatchers("/doctors/all").hasRole("PATIENT")
                .antMatchers("/patients/all").hasRole("DOCTOR")
                .antMatchers("/doctors/**").hasAnyRole("DOCTOR", "ADMIN")
                .antMatchers("/patients/**").hasAnyRole("PATIENT", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/works/**").hasRole("ADMIN")
                .antMatchers("/notifications").hasAnyRole("PATIENT", "DOCTOR")
                .antMatchers("/appointments/new/**").hasRole("PATIENT")
                .antMatchers("/appointments/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .antMatchers("/recordMedical/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .antMatchers("/invoices/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .antMatchers("/api/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .and()
                .formLogin()
	                .loginPage("/login")
	                .loginProcessingUrl("/perform_login")
	                .successHandler(customAuthenticationSuccessHandler)
	                .permitAll()
                .and()
                	.logout().logoutUrl("/perform_logout")
                .and()
                	.exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/register/**");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }
    
    @Bean
    RequestRejectedHandler requestRejectedHandler() {
        return new HttpStatusRequestRejectedHandler();
    }
    
}