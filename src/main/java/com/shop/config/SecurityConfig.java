package com.shop.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.access.AccessDeniedHandler;

//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//            .authorizeRequests()
//                .antMatchers("/", "/home", "/welcome", "/translate"/*, "/public/**", "/favicon.ico", "/resources/**", "/static/**", "/h2-console/**"*/)
//                    .permitAll()
//                .antMatchers("/user/**")
//                    .hasAnyRole("USER")
//                .antMatchers("/admin/**")
//                    .hasAnyRole("ADMIN")
//                    //.hasAuthority("ADMIN")
//                //.anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .failureUrl("/login?error")
//                //.usernameParameter("email")
//                .permitAll()
//                .and()
//            .logout()
//                .logoutUrl("/logout")
//                //.deleteCookies("remember-me")
//                //.logoutSuccessUrl("/")
//                .permitAll()
//                .and()
//            .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
//            //.rememberMe();
//
//        httpSecurity.csrf().disable();
//        httpSecurity.headers().frameOptions().disable();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("p").roles("USER")
//                .and()
//                .withUser("admin").password("p").roles("ADMIN");
//    }
//
//}
