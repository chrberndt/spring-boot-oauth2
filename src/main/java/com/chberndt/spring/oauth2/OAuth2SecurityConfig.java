package com.chberndt.spring.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfig {

//	@Bean
//	public JwtAuthenticationConverter jwtAuthenticationConverter() {
//		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//		grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
//		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//
//		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//		return jwtAuthenticationConverter;
//	}

}
