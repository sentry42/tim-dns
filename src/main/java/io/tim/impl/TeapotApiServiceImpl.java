package io.tim.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import io.tim.dns.api.NotFoundException;
import io.tim.dns.api.TeapotApiService;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class TeapotApiServiceImpl extends TeapotApiService {
   @Override
   public Response teapot(SecurityContext securityContext) throws NotFoundException {
      // do some magic!
      return Response.status(Status.fromStatusCode(200)).entity("Making tea.").build();
   }
}
