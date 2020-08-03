package pizza.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 *    Klasa konfigurująca serwer autoryzacyjny, rozszerzająca klasę AuthorizationServerConfigurerAdapter.
 *    Nadpisując jej metody, możemy skonfigurować trzy elementy:
 *
 *    - ClientDetailsServiceConfigurer (konfigurator serwisu danych klienta)
 *      Podstawowym zadaniem tej klasy będzie skonfigurowanie serwisu danych szczegółowych klienta OAuth2 (ClientDetailsService).
 *      Serwis ClientDetailsService dostarcza informacji szczegółowych (ClientDetails) nt. klientów zapisanych w pamięci (inMemory) albo utrwalonych w bazie danych (jdbc),
 *      którzy mają dostęp do serwera zasobu.
 *      Przy pomocy ClientDetailsServiceConfigurer istnieje możliwość skonfigurowania źródła danych klienta w oparciu o dane inicjowane w pamięci (inMemory) lub w oparciu
 *      o dane pochodzące z bazy danych (jdbc).
 *
 *    - AuthorizationServerEndpointsConfigurer (konfigurator punktów końcowych serwera autoryzacji)
 *      AuthorizationServerEndpointsConfigurer konfiguruje właściwości punktów końcowych serwera autoryzacji. Definiuje autoryzację i punkty końcowe tokenu oraz serwisy tokenu.
 *      Jedna z właściwości obiektu AuthorizationServerEndpointsConfigurer, AuthorizationServerTokenServices, generuje access tokeny specyficzne dla klienta.
 *
 *      Konfiguratorowi przekazujemy skonfigurowany TokenStore. W ramach konfiguracji TokenStore decydujemy się na wybór strategii tego, jak token będzie przechowywany i udostępniany.
 *      Możemy tu skorzystać z opcji in memory token, jdbc token lub jwt token (JSON Web Token)
 *      TokenStore utrwala tokeny OAuth2. Pozwala na odczytanie access tokenu (OAuth2AccessToken) i refresh tokenu (OAuth2RefreshToken), pobranie z access tokenu tokenu autentykacji OAuth2
 *      (token autentykacji OAuth2 {OAuth2Authentication}  może zawierać dwa rodzaje uwierzytelnienia: jedno dla klienta (obiekt tokenu żądania OAuth2Request) i jedno dla użytkownika (obiekt tokenu Authentication)-
 *      jesli przyznana autoryzacja wymaga uwierzytelnienia użytkownika).
 *      JwtTokenStore jest implementacją TokenStore, ktora nie utrwala danych tokena jwt, a jedynie odczytuje dane z tokena.
 *
 *      Konfiguratorowi przekazujemy skonfigurowany AuthenticationManager.
 *      AuthenticationManager przetwarza żądania uwierzytelnienia klienta. Bean AuthenticationManager został skonfigurowany w klasie SecurityConfig.
 *      Zadaniem AuthenticationManagera jest próba uwierzytelnienia klienta w oparciu o obiekt Authentication.
 *      Obiekt Authentication reprezentuje token żądania uwierzytelnienia lub token uwierzytelnionego podmiotu zabezpieczeń (principala) po przetworzeniu żądania przez AuthenticationManager.
 *      Po uwierzytelnieniu żądania, token Authentication będzie zwykle przechowywany w lokalnym wątku  SecurityContext  zarządzanym przez SecurityContextHolder.
 *
 *      Konfiguratorowi przekazujemy JwtAccessTokenConverter.
 *      JwtAccessTokenConverter jest 'pomocnikiem', który tłumaczy między wartościami tokenów zakodowanymi za pomocą JWT a informacjami uwierzytelniającymi OAuth (w obu kierunkach:
 *          1) informacje uwierzytelniające OAuth zawarte w inicjalnym żądaniu uwierzytelniajacym => JwtAccessTokenConverter => dane w PAYLOAD JWT Tokena
 *          2) dane w JWT Tokena dołączanego do żądań do zasobów REST API -> JwtAccessTokenConverter -> informacje uwierzytelniające OAuth)
 *
 *    - AuthorizationServerSecurityConfigurer (konfigurator zabezpieczeń serwera autoryzacji)
 *      W naszym przypadku nie nadpisujemy metody dającej mozliwość skonfigurowanie tego obiektu.
 *      Odwołując się do AuthorizationServerSecurityConfigurer możemy skonfigurować niestandardowe filtry uwierzytelniania dla TokenEndpoint (punktu końcowego dla żądań POST o access token)
 *

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
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
        .accessTokenConverter(accessTokenConverter());
    }


    @Bean
    public  TokenStore tokenStore() {

       // return new InMemoryTokenStore();

        JwtTokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
        return tokenStore;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter jwtATConverter = new JwtAccessTokenConverter();
        jwtATConverter.setSigningKey("as466gf");

        return jwtATConverter;
    }
}
