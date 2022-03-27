package com.example.springsecurity.Security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2505978202798966833L;

	@Override
	public void commence(HttpServletRequest arg0, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.resetBuffer();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
//		response.setHeader("Content-Type", "application/json");
		response.getOutputStream()
				.print("{\"responseCode\":{\"code\":" + HttpServletResponse.SC_UNAUTHORIZED + ",\r\n"
						+ "	        \"errorMessage\": \"Unauthorized Access!\",\r\n"
						+ "	        \"message\": \"Unauthorized Access\"\n}\n}");
		response.flushBuffer();

//		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().write(json);
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}
