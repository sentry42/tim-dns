package io.tim.impl;

import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import io.tim.dns.api.DnsApiService;
import io.tim.dns.api.NotFoundException;
import io.tim.dns.model.DnsRecord;
import io.tim.dns.model.Error;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class DnsApiServiceImpl extends DnsApiService {

   private ConcurrentHashMap<String, String> records = new ConcurrentHashMap<String, String>();

   @Override
   public Response dns(DnsRecord record, SecurityContext securityContext) throws NotFoundException {
      records.put(record.getHostname(), record.getIp());
      return Response.ok().build();
   }

   @Override
   public Response dns(String hostname, SecurityContext securityContext) throws NotFoundException {
      String ip = records.get(hostname);
      if (ip == null) {
         return Response.status(Status.NOT_FOUND.getStatusCode())
               .entity(
                     new Error().code(Error.ErrorCode.NO_RECORD)
                           .message("No entry found for hostname '" + hostname + "'."))
               .build();
      }
      return Response.ok().entity(new DnsRecord().hostname(hostname).ip(ip)).build();
   }
}
