package io.tim.dns.api;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import io.tim.dns.model.DnsRecord;
import io.tim.factories.DnsApiServiceFactory;

@Path("/")
@io.swagger.annotations.Api(description = "the  API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class DnsApi {
   private final DnsApiService delegate;

   public DnsApi(@Context ServletConfig servletContext) {
      DnsApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("DnsApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (DnsApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }

      if (delegate == null) {
         delegate = DnsApiServiceFactory.getDefaultApi();
      }

      this.delegate = delegate;
   }

   @POST
   @Path("/dns")
   @Consumes({ "application/json" })
   @Produces({ "application/json" })
   @io.swagger.annotations.ApiOperation(value = "Update DNS record.", notes = "Update DNS record.", response = Void.class, tags = {})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = Void.class) })
   public Response dns(
         @ApiParam(value = "A DNS Record", required = true) DnsRecord body,
         @Context SecurityContext securityContext)
         throws NotFoundException {
      return delegate.dns(body, securityContext);
   }

   @GET
   @Path("/dns")
   @io.swagger.annotations.ApiOperation(value = "Default.", notes = "Default.", response = Void.class, tags = {})
   @io.swagger.annotations.ApiResponses(value = {
         @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = Void.class) })
   public Response defaultOp(@Context SecurityContext securityContext) throws NotFoundException {
      return Response.ok().build();
   }
}
