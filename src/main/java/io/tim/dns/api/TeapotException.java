package io.tim.dns.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@SuppressWarnings("serial")
@Provider
public class TeapotException extends Exception implements ExceptionMapper<TeapotException> {

	public TeapotException() {
		super();
	}

	public TeapotException(String message) {
		super(message);
	}

	public TeapotException(String actualSignature, String expectedSignature, String payload) {
		super(String.format("The actual signature was '%s' but it should have been '%s' for payload %s",
				actualSignature, expectedSignature, payload));
	}

	@Override
	public Response toResponse(TeapotException exception) {
		System.out.println("Returning TeapotException");
		return Response.status(400).build();
	}

}
