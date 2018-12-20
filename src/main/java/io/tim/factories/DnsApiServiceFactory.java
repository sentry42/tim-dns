package io.tim.factories;

import io.tim.dns.api.DnsApiService;
import io.tim.impl.DnsApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class DnsApiServiceFactory {
   private final static DnsApiService service = new DnsApiServiceImpl();

   public static DnsApiService getDefaultApi() {
      return service;
   }
}
