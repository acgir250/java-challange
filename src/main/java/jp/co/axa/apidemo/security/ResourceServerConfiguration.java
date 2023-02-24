package jp.co.axa.apidemo.security;

import jp.co.axa.apidemo.errors.security.CustomAccessDeniedHandler;
import jp.co.axa.apidemo.errors.security.CustomAuthenticationExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 *
 *  This class is for the configuration management of the resource server in oauth components.
 *  Mainly purpose is for the server access management,endpoints management etc..
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private final CustomAuthenticationExceptionHandler customAuthenticationExceptionHandler;


    public ResourceServerConfiguration(CustomAuthenticationExceptionHandler customAuthenticationExceptionHandler) {
        this.customAuthenticationExceptionHandler = customAuthenticationExceptionHandler;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("api");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/signin**")
                .permitAll()
                .antMatchers("/api/sigin/**")
                .permitAll()
                .antMatchers("/api/v1/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/api/users**").hasAuthority("ADMIN")
                .antMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationExceptionHandler)
                .accessDeniedHandler(new CustomAccessDeniedHandler());

    }
}
