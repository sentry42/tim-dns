package io.tim.dns.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ThrowableExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable throwable) {
		System.out.println("Handling unmapped exception");
		System.out.println(throwable);
		for (StackTraceElement ste : throwable.getStackTrace()) {
			System.out.println(ste);
		}
		return Response.status(500).build();
	}

}
