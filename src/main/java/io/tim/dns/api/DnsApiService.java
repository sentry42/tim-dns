package io.tim.dns.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import io.tim.dns.model.DnsRecord;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public abstract class DnsApiService {
	public abstract Response dns(DnsRecord record, SecurityContext securityContext, Request request,
			AsyncResponse asyncResponse, HttpHeaders httpHeaders, UriInfo uriInfo,
			HttpServletRequest httpServletRequest) throws Exception;

	public abstract Response dns(String hostname, SecurityContext securityContext, Request request,
			AsyncResponse asyncResponse, HttpHeaders httpHeaders, UriInfo uriInfo,
			HttpServletRequest httpServletRequest) throws Exception;
}
