package io.tim.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.tim.dns.api.ApiResponseMessage;
import io.tim.dns.api.DnsApiService;
import io.tim.dns.api.NotFoundException;
import io.tim.dns.model.DnsRecord;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class DnsApiServiceImpl extends DnsApiService {
   @Override
   public Response dns(DnsRecord body, SecurityContext securityContext) throws NotFoundException {
      // do some magic!
      return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
   }
}
