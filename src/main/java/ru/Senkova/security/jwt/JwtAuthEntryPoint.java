package ru.Senkova.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
//        if (request.getAttribute("currentUser") == null || request.getAttribute("currentUser").equals("")) {
//                    LOGGER.error("Unauthorized error. Message - {}", e.getMessage());
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
//        } // check exceptions
    }
}
