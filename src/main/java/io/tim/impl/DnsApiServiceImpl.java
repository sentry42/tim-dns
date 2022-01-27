package io.tim.impl;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.util.Base64;

import io.tim.TimDns;
import io.tim.dns.api.CustomHeaders;
import io.tim.dns.api.DnsApiService;
import io.tim.dns.api.InvalidSignatureException;
import io.tim.dns.api.JsonUtil;
import io.tim.dns.api.JwsTools;
import io.tim.dns.api.NotFoundException;
import io.tim.dns.model.DnsRecord;
import io.tim.dns.model.Error;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-12-16T15:33:42.028Z")
public class DnsApiServiceImpl extends DnsApiService {

	private ConcurrentHashMap<String, String> records = new ConcurrentHashMap<String, String>();

	@Override
	public Response dns(DnsRecord record, SecurityContext securityContext, Request request, AsyncResponse asyncResponse,
			HttpHeaders httpHeaders, UriInfo uriInfo, HttpServletRequest httpServletRequest)
			throws NotFoundException, KeyLengthException, InvalidSignatureException, JOSEException, IOException {
		JwsTools.verifyToken(JsonUtil.serialize(record), httpHeaders);
		records.put(record.getHostname(), record.getIp());
		try {
			String token = JwsTools.signPrivatePayload(null);
			return Response.ok()
					.header(CustomHeaders.X_TOKEN, Base64.encode((TimDns.APP_NAME + ':' + token).getBytes())).build();
		} catch (JOSEException e) {
			TimDns.logger.error("Exception while constructing token.", e);
			throw e;
		}
	}

	@Override
	public Response dns(String hostname, SecurityContext securityContext, Request request, AsyncResponse asyncResponse,
			HttpHeaders httpHeaders, UriInfo uriInfo, HttpServletRequest httpServletRequest) throws Exception {
		JwsTools.verifyToken(hostname, httpHeaders);

		String ip = records.get(hostname);
		Status status;
		Object entity;
		if (ip == null) {
			entity = new Error().code(Error.ErrorCode.NO_RECORD)
					.message("No entry found for hostname '" + hostname + "'.");
			status = Status.NOT_FOUND;
		} else {
			entity = new DnsRecord().hostname(hostname).ip(ip);
			status = Status.OK;
		}
		try {
			String token = JwsTools.signPrivatePayload(JsonUtil.serialize(entity));
			return Response.status(status)
					.header(CustomHeaders.X_TOKEN, Base64.encode((TimDns.APP_NAME + ':' + token).getBytes()))
					.entity(entity).build();
		} catch (JOSEException | IOException e) {
			TimDns.logger.error("Exception while constructing token.", e);
			throw e;
		}
	}
}
