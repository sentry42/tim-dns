package io.tim.dns.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@SuppressWarnings("serial")
@Provider
public class InvalidSignatureException extends Exception implements ExceptionMapper<InvalidSignatureException> {

	public InvalidSignatureException() {
		super();
	}

	public InvalidSignatureException(String message) {
		super(message);
	}

	public InvalidSignatureException(String actualSignature, String expectedSignature, String payload) {
		super(String.format("The actual signature was '%s' but it should have been '%s' for payload %s",
				actualSignature, expectedSignature, payload));
	}

	@Override
	public Response toResponse(InvalidSignatureException exception) {
		return Response.status(Status.UNAUTHORIZED).build();
	}

}
