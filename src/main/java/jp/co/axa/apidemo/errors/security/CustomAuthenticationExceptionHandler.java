package jp.co.axa.apidemo.errors.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.errors.model.ApiError;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 *  This is the custom authentication exception handler implemented from the authentication entry point
 *  It's main purpose is to handle the authentication exception generated from oAuth2AUthentication.
 */
@Component
public class CustomAuthenticationExceptionHandler implements AuthenticationEntryPoint {
    private final HttpMessageConverter<String> messageConverter;

    private final ObjectMapper objectMapper;

    public CustomAuthenticationExceptionHandler(HttpMessageConverter<String> messageConverter, ObjectMapper objectMapper) {
        this.messageConverter = messageConverter;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ServerHttpResponse outputMessage = new ServletServerHttpResponse(httpServletResponse);
        outputMessage.setStatusCode(UNAUTHORIZED);
        ApiError error = new ApiError(UNAUTHORIZED, Instant.now(), e.getMessage(), e.getMessage());

        messageConverter.write(objectMapper.writeValueAsString(error), MediaType.APPLICATION_JSON, outputMessage);

    }
}
