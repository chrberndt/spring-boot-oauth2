package com.chberndt.spring.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@RestController
public class OAuth2Controller {

	@GetMapping("/api/test")
	public Map<String, Object> getTest(Authentication authentication) {

		System.out.println("getTest()");

		printAuthorities(authentication);

		return getClaimsAsMap(authentication);

	}

//	@GetMapping("/api/test")
//	public TypeValue[] getTest(Authentication authentication) {
//
//		System.out.println("getTest()");
//
//		printAuthorities(authentication);
//
//		return getClaims(authentication);
//
//	}

	private Map<String, Object> getClaimsAsMap(Authentication authentication) {
		Jwt jwt = (Jwt)authentication.getPrincipal();
		return jwt.getClaims();
	}

	private TypeValue[] getClaimsAsArray(Authentication authentication) {

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
		for (Object authority : authorities) {
			System.out.println(authority);
		}
	}

}
