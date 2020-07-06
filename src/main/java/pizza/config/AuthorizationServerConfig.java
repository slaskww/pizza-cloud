package pizza.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 *    Klasa konfigurująca serwer autoryzacyjny, rozszerzająca klasę AuthorizationServerConfigurerAdapter.
 *    Podstawowym zadaniem tej klasy będzie skonfigurowanie serwisu danych szczegółowych klienta, kóry ma dostać dostęp do serwera zasobu.
 *    Istnieje tu możliwość skonfigurowania danych klienta w pamięci (inMemory) lub w oparciu o dane pochodzące w tabeli bazy danych (jdbc).
 *    Drugim elementem podlegającym konfiguracji będzie strategia tego, jak token będzie przechowywany i udostępniany.
 *    Możemy tu skorzystać z opcji in memory token, jdbc token lub jwt token (JSON Web Token)
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//    static final String CLIENT_ID = "adamex";
//    static final String CLIENT_SECRET = "123456";
//    static final String GRANT_TYPE_PASSWORD = "password";
//    static final String AUTHORIZATION_CODE = "authorization_code";
//    static final String REFRESH_TOKEN = "refresh_token";
//    static final String IMPLICIT = "implicit";
//    static final String SCOPE_READ = "read";
//    static final String SCOPE_WRITE = "write";
//    static final String TRUST = "trust";
//    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
//    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

        configurer.jdbc(dataSource);


//         configurer.inMemory()
//                .withClient(CLIENT_ID)
//                .secret(CLIENT_SECRET)
//                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT )
//                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
//                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
//                refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
    }

}