package com.example.demo.config;

import com.example.demo.user.domain.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private UserDetailsServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager(), userService);
        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(), userService);

        CustomLoginFilter filter = new CustomLoginFilter(authenticationManager());
        filter.setFilterProcessesUrl("/api/login");

        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(failureHandler());
        filter.afterPropertiesSet();

        http
                .csrf().disable()
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin().disable()
//                .failureHandler(failureHandler())
//                .and()

                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {

                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException authException) throws IOException, ServletException {
                        String exception = (String)request.getAttribute("exception");
                        //String errorCode = (String)request.getAttribute("errorCode");

                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setStatus(403);

                        if (exception == null){
                            response.getWriter().println("{ \"message\" : \"" + "로그인 하세요"
                                    + "\", \"code\" : \"" + "required login"
                                    + "\", \"status\" : " + 404
                                    + ", \"errors\" : [ ] }");
                        }else{

                        response.getWriter().println("{ \"message\" : \"" + exception
                                + "\", \"code\" : \"" + "expired token"
                                + "\", \"status\" : " + 404
                                + ", \"errors\" : [ ] }");
                        }
                    }});


        http.csrf()
               .ignoringAntMatchers("/api/**");
        http    //.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests(request->
                        request.antMatchers("/api/login/", "/api/loginf", "/errorjwt").permitAll()

                                .antMatchers("/api/signup").permitAll()
                                .antMatchers(HttpMethod.GET,"/api/post").permitAll()
                                .anyRequest().authenticated()
                );
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler();
    }

}
