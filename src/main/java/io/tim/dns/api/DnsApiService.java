package io.tim.dns.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.tim.dns.model.DnsRecord;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public abstract class DnsApiService {
   public abstract Response dns(DnsRecord body, SecurityContext securityContext) throws NotFoundException;
}
