package com.shop.config.security;

//import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeRequests()
                .antMatchers(
                        "/",
                        "/home",
                        "/welcome",
                        "/test/**",
                        "/registration**",
                        "/forgot-password**",
                        "/reset-password**",
                        "/products/",
                        "/product/show/*"
                        /*, "/public/**", "/favicon.ico", "/resources/**", "/static/**", "/h2-console/**"*/)
                    .permitAll()
                .antMatchers(
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**")
                    .permitAll()
                .antMatchers(
                        "/user/**")
                    .hasAnyRole("USER")
                .antMatchers(
                        "/admin/**"
                        )
                    .hasAnyRole("ADMIN")
                .antMatchers(
                        "/product/**")
                    .hasAnyRole("ADMIN", "MANAGER")
                    //.hasAuthority("ADMIN")
                //.anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                //.usernameParameter("email")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                //.deleteCookies("remember-me")
                //.logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
            //.rememberMe();

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("p").roles("USER")
//                .and()
//                .withUser("admin").password("p").roles("ADMIN");
//
//        //User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
//    }

//    @SuppressWarnings("deprecation")
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }


}
