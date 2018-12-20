package io.tim.dns.api;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import io.tim.dns.model.DnsRecord;
import io.tim.factories.DefaultApiServiceFactory;

@Path("/")
@io.swagger.annotations.Api(description = "the  API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class DefaultApi {
   private final DefaultApiService delegate;

   public DefaultApi(@Context ServletConfig servletContext) {
      DefaultApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("DefaultApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (DefaultApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }

      if (delegate == null) {
         delegate = DefaultApiServiceFactory.getDefaultApi();
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
      System.out.println("DNS operation");
      return delegate.dns(body, securityContext);
   }
}
