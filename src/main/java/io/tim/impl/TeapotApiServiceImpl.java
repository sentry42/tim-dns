package io.tim.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.tim.dns.api.NotFoundException;
import io.tim.dns.api.TeapotApiService;
import io.tim.dns.api.TeapotException;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class TeapotApiServiceImpl extends TeapotApiService {
   @Override
   public Response teapot(SecurityContext securityContext) throws NotFoundException, TeapotException {
//      // do some magic!
//      return Response.status(Status.fromStatusCode(200)).entity("Making tea.").build();
	   System.out.println("Gonna throw a teapot");
	   throw new TeapotException("TeapotException here");
   }
}
