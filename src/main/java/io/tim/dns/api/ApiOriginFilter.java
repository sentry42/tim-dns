package io.tim.dns.api;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class ApiOriginFilter implements javax.servlet.Filter {
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
         throws IOException, ServletException {
      HttpServletResponse res = (HttpServletResponse) response;
      res.addHeader("Access-Control-Allow-Origin", "*");
      res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
      res.addHeader("Access-Control-Allow-Headers", "Content-Type");
      chain.doFilter(request, response);
   }

   public void destroy() {
   }

   public void init(FilterConfig filterConfig) throws ServletException {
   }
}