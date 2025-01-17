package com.chberndt.spring.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OAuth2Controller {

	@GetMapping("/api/test")
	public TypeValue[] getClaimsAsArray(Authentication authentication) {

		printAuthorities(authentication);

		return doGetClaimsAsArray(authentication);

	}

	@GetMapping("/api/test/map")
	public Map<String, Object> getClaimsAsMap(Authentication authentication) {

		printAuthorities(authentication);

		return doGetClaimsAsMap(authentication);

	}

	private Map<String, Object> doGetClaimsAsMap(Authentication authentication) {
		Jwt jwt = (Jwt) authentication.getPrincipal();
		return jwt.getClaims();
	}

	private TypeValue[] doGetClaimsAsArray(Authentication authentication) {

		try {
			Jwt jwt = (Jwt) authentication.getPrincipal();
			Map<String, Object> claims = jwt.getClaims();
			Set<String> keySet = claims.keySet();
			String[] keys = keySet.toArray(new String[0]);

			TypeValue[] typeValues = new TypeValue[keys.length];

			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				typeValues[i] = new TypeValue(key, claims.get(key));
				// System.out.println(key + ": " + claims.get(key));
			}

			return typeValues;

		}
		catch (ClassCastException cce) {
			throw new UnsupportedOperationException("You must authenticate with JWT.");
		}

	}

	private void printAuthorities(Authentication authentication) {

		Collection<?> authorities = authentication.getAuthorities();
		System.out.println();
		for (Object authority : authorities) {
			System.out.println(authority);
		}
	}

}
