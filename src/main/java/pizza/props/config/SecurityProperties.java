package pizza.props.config;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "restowe.security")
public class SecurityProperties {

    private boolean enabled;
    private boolean enabledAuthorizationServer;
    private String apiMatcher;
    private Cors cors = new Cors();
    private String issuerUri;


    public CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(cors.getAllowedOrigins());
        corsConfiguration.setAllowedMethods(cors.getAllowedMethods());
        corsConfiguration.setAllowedHeaders(cors.getAllowedHeaders());
        corsConfiguration.setExposedHeaders(cors.getExposedHeaders());
        corsConfiguration.setAllowCredentials(cors.getAllowCredentials());
        corsConfiguration.setMaxAge(cors.getMaxAge());

        return corsConfiguration;
    }

    @Getter
    @Setter
    public static class Cors {

        private List<String> allowedOrigins;
        private List<String> allowedMethods;
        private List<String> allowedHeaders;
        private List<String> exposedHeaders;
        private Boolean allowCredentials;
        private Long maxAge;
    }
}
