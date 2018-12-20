package io.tim.dns.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public abstract class TeapotApiService {
   public abstract Response teapot(SecurityContext securityContext) throws NotFoundException;
}
