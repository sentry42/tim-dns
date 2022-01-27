package io.tim.dns.api;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.util.Base64;

import io.tim.TimDns;
import io.tim.TimDnsApp;

public class JwsTools {
	
	private static final String PRIVATE_KID = TimDns.APP_NAME;

	/**
	 * Signs a payload with the private key
	 * 
	 * @param payload The payload to be signed.
	 * @return
	 * @throws KeyLengthException
	 * @throws JOSEException
	 */
	public static String signPrivatePayload(String payloadStr) throws KeyLengthException, JOSEException {
		// Create an HMAC-protected JWS object with some payload
		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256, null, null, null, null, null, null, null, null, null,
				PRIVATE_KID, false, null, null);
		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(PRIVATE_KID+payloadStr));

		// Apply the HMAC to the JWS object
		jwsObject.sign(new MACSigner(TimDnsApp.getInstance().getPrivateKey()));

		// Output in URL-safe format
		return jwsObject.serialize();
	}

	/**
	 * Signs a payload with some public key
	 * 
	 * @param kid     The ID of the key required.
	 * @param payload The payload to be signed.
	 * @return
	 * @throws KeyLengthException
	 * @throws JOSEException
	 */
	public static String signPublicPayload(String kid, String payloadStr) throws KeyLengthException, JOSEException {
		// Create an HMAC-protected JWS object with some payload
		JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256, null, null, null, null, null, null, null, null, null,
				kid, false, null, null);
		JWSObject jwsObject = new JWSObject(jwsHeader, new Payload(kid+payloadStr));

		// Apply the HMAC to the JWS object
		jwsObject.sign(new MACSigner(TimDnsApp.getInstance().getPublicKey(kid)));

		// Output in URL-safe format
		return jwsObject.serialize();
	}

	public static void verifySignature(String kid, String payloadStr, String signature)
			throws KeyLengthException, JOSEException, InvalidSignatureException {
		if (!signPublicPayload(kid, payloadStr).equals(signature)) {
			throw new InvalidSignatureException("InvalidSignatureException message");
		}
	}

	public static void verifyToken(String payload, HttpHeaders httpHeaders)
			throws InvalidSignatureException, KeyLengthException, JOSEException {
		String authHeader = null;
		List<String> authHeaders = httpHeaders.getRequestHeader(HttpHeaders.AUTHORIZATION);
		if (authHeaders != null && !authHeaders.isEmpty()) {
			authHeader = authHeaders.get(0);
		} else {
			throw new InvalidSignatureException("No Authorization header.");
		}
		if (authHeader == null || authHeader.isEmpty()) {
			throw new InvalidSignatureException("Authorzation header not set.");
		}
		String tokenReceived = new Base64(authHeader.substring("Bearer ".length())).decodeToString();
		int colonIndex = tokenReceived.indexOf(':');
		String kid = tokenReceived.substring(0, colonIndex);
		JwsTools.verifySignature(kid, payload, tokenReceived.substring(colonIndex + 1));
	}
}
