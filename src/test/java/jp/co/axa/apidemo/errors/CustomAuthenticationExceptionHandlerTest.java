package jp.co.axa.apidemo.errors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.errors.security.CustomAuthenticationExceptionHandler;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)

public class CustomAuthenticationExceptionHandlerTest {


    @Mock
    HttpMessageConverter<String> messageConverter;

    @Mock
    ObjectMapper objectMapper;

    @Spy
    @InjectMocks
    CustomAuthenticationExceptionHandler customAuthenticationExceptionHandler;

    @Captor
    ArgumentCaptor<HttpServletResponse> serverResponse;


    @Test
    @SneakyThrows
    public void should_commence() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);

        //when
        customAuthenticationExceptionHandler.commence(request, response, exception);

        //then
        verify(customAuthenticationExceptionHandler).commence(eq(request), serverResponse.capture(), eq(exception));
        HttpServletResponse result = serverResponse.getValue();
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(0);
    }

}
