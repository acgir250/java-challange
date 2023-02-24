package jp.co.axa.apidemo.errors;

import jp.co.axa.apidemo.errors.security.CustomAccessDeniedHandler;
import lombok.SneakyThrows;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ExtendWith(SpringExtension.class)
public class CustomAccessDeniedHandlerTest {

    @InjectMocks
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    AccessDeniedException exception;

    @Test
    @SneakyThrows
    public void should_handle() {
        //given
        //when

        //then
        BDDAssertions.assertThatThrownBy(() -> customAccessDeniedHandler.handle(request, response, exception)).isInstanceOf(AccessDeniedException.class);
    }

}
