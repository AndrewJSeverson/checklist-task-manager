package com.severson.taskmanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;


@Configuration
@EnableWebSecurity(debug = true)
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                .antMatchers("/admin/system/state/*", "/v2/api-docs", "/configuration/ui", "/swagger-resources",
                		"/configuration/security", "/swagger-ui.html", "/webjars/**", "/favicon.ico", "/**/favicon.ico").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/checklistCategories/**").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/api/v1/checklistCategories/**").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/checklistCategories/**").hasAuthority("admin")
                .antMatchers(HttpMethod.DELETE, "/api/v1/checklistCategories/**").hasAuthority("admin")
                .antMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("user")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/api/v1/checklistTasks/**").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/api/v1/checklistTasks/**").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/api/v1/checklistTasks/**").hasAuthority("user")
                .antMatchers(HttpMethod.DELETE, "/api/v1/checklistTasks/**").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/api/v1/checklists/**").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/api/v1/checklists/**").hasAuthority("user")
                .antMatchers(HttpMethod.PUT, "/api/v1/checklists/**").hasAuthority("user")
                .antMatchers(HttpMethod.DELETE, "/api/v1/checklists/**").hasAuthority("user")
                .anyRequest().authenticated();
    }

}