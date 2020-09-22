package pizza.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pizza.props.config.SecurityProperties;

/**
 *  Klasa konfigurująca Serwer zasobu ResourceServerConfig. Naszym zasobem w tym kontekście jest wystawione przez nas REST API dla operacji CRUDowych.
 * 	By otrzymać dostęp do tych zasobów, klient musi zostać uwierzytelniony.
 */

@Configuration
@EnableResourceServer
@Import({SecurityProperties.class})
@ConditionalOnProperty(prefix = "restowe.security", value = "enabled", havingValue = "true")
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "pizza-service";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
       // resources.resourceId(RESOURCE_ID).stateless(false);
        resources.resourceId(resourceServerProperties.getResourceId());
    }

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
/*        http

                .authorizeRequests()
                .antMatchers("/order","/order/**").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());*/

       /* http
                .requestMatchers()
                .antMatchers("/api", "/api/**")
                .and()
                .authorizeRequests().anyRequest().access("#oauth2.hasScope('read')");*/

       http.cors().configurationSource(corsConfigurationSource())
               .and()
               .headers()
               .frameOptions()
               .disable()
               .and()
               .csrf()
               .disable()
               .authorizeRequests()
               .antMatchers(securityProperties.getApiMatcher())
               .authenticated();
    }

    //keycloak
    @Bean
    public JwtAccessTokenCustomizer jwtAccessTokenCustomizer(ObjectMapper mapper) {
        return new JwtAccessTokenCustomizer(mapper);
    }

    //keycloak
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        if (null != securityProperties.getCorsConfiguration()) {
            source.registerCorsConfiguration("/**", securityProperties.getCorsConfiguration());
        }
        return source;
    }

    @Bean
    @ConditionalOnProperty(prefix = "security.oauth2.client", value = "grant-type", havingValue = "client_credentials")
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails details) {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);

        //Prepare by getting access token once
        oAuth2RestTemplate.getAccessToken();
        return oAuth2RestTemplate;
    }
}