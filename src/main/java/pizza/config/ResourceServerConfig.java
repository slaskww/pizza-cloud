package pizza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 *  Klasa konfigurująca Serwer zasobu ResourceServerConfig. Naszym zasobem w tym kontekście jest wystawione przez nas REST API dla operacji CRUDowych.
 * 	By otrzymać dostęp do tych zasobów, klient musi zostać uwierzytelniony.
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
/*        http

                .authorizeRequests()
                .antMatchers("/order","/order/**").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());*/

        http
                .requestMatchers()
                .antMatchers("/api", "/api/**")
                .and()
                .authorizeRequests().anyRequest().access("#oauth2.hasScope('read')");
    }

}