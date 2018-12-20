package io.tim.dns.api;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class ApiException extends Exception {
   private int code;

   public ApiException(int code, String msg) {
      super(msg);
      this.code = code;
   }
}
