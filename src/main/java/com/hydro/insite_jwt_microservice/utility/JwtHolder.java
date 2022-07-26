package com.hydro.insite_jwt_microservice.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hydro.insite_common_microservice.util.HydroLogger;
import com.hydro.insite_user_microservice.client.domain.enums.WebRole;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * JwtHolder class to get common information from token
 * 
 * @author Sam Butler
 * @since August 8, 2020
 */
@Service
public class JwtHolder {

	private JwtParser jwtParser;

	@Autowired
	private HydroLogger LOGGER;

	public JwtHolder() {
		jwtParser = Jwts.parser().setSigningKey(JwtEnvironment.getSigningKey());
	}

	/**
	 * Get the current user Id.
	 * 
	 * @return int of the userId from the current token
	 */
	public int getUserId() {
		return getUserId(getToken());
	}

	/**
	 * Get the current email.
	 * 
	 * @return String of the email from the current token
	 */
	public String getEmail() {
		return getEmail(getToken());
	}

	/**
	 * Get the current webRole.
	 * 
	 * @return String of the webRole from the current token
	 */
	public WebRole getWebRole() {
		return getWebRole(getToken());
	}

	/**
	 * Gets the reset password status.
	 * 
	 * @return int of the userId from the current token
	 */
	public boolean getResetPassword() {
		return getResetPassword(getToken());
	}

	/**
	 * Get the userId from the passed in token
	 * 
	 * @param token - String of the token to decode
	 * @return int of the userId from the current token
	 */
	public int getUserId(String token) {
		return Integer.parseInt(parse("userId", token).toString());
	}

	/**
	 * Get the email from the passed in token
	 * 
	 * @param token - String of the token to decode
	 * @return String of the email from the current token
	 */
	public String getEmail(String token) {
		return parse("email", token).toString();
	}

	/**
	 * Get the webRole from the passed in token
	 * 
	 * @param token - String of the token to decode
	 * @return String of the webRole from the current token
	 */
	public WebRole getWebRole(String token) {
		return WebRole.valueOf(parse("webRole", token).toString());
	}

	/**
	 * Gets the reset password status from the token.
	 * 
	 * @param token - String of the token to decode
	 * @return int of the userId from the current token
	 */
	public boolean getResetPassword(String token) {
		return Boolean.parseBoolean(parse("passwordReset", token).toString());
	}

	/**
	 * Get the current token passed in with the request
	 * 
	 * @return String of the token from the request headers
	 */
	public String getToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		return request.getHeader("Authorization") == null ? null : request.getHeader("Authorization").split(" ")[1];
	}

	/**
	 * Parse the claims from the given token and for the given key value pair.
	 * 
	 * @param key   The key to find.
	 * @param token The token to parse.
	 * @return {@link Object} of the found key.
	 */
	public Object parse(String key, String token) {
		try {
			return jwtParser.parseClaimsJws(token).getBody().get(key);
		}
		catch(Exception e) {
			LOGGER.warn("Could not parse key '{}' from token.", key);
			return null;
		}
	}
}
