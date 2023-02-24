package jp.co.axa.apidemo.errors;

import jp.co.axa.apidemo.errors.exceptionhandler.ApiExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class ApiExceptionHandlerTest {

    @MockBean
    ServletWebRequest servletWebRequest;
    @Spy
    private ApiExceptionHandler apiExceptionHandler;
    @MockBean
    private BindingResult bindingResult;

    @Test
    public void should_return_responseEntity_when_entityNotFound() {

        //given
        EntityNotFoundException exception = Mockito.mock(javax.persistence.EntityNotFoundException.class);
        //when
        ResponseEntity<Object> result = apiExceptionHandler.handleEntityNotFound(exception);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void should_return_responseEntity_when_MethodNotSupported() {

        //given
        HttpRequestMethodNotSupportedException exception = Mockito.mock(HttpRequestMethodNotSupportedException.class);
        HttpHeaders headers = Mockito.mock(HttpHeaders.class);
        WebRequest request = Mockito.mock(WebRequest.class);

        //when
        ResponseEntity<Object> result = apiExceptionHandler.handleHttpRequestMethodNotSupported(exception, headers, HttpStatus.METHOD_NOT_ALLOWED, request);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void should_return_responseEntity_when_httpMediaTypeNotSupported() {

        //given
        HttpMediaTypeNotSupportedException exception = Mockito.mock(HttpMediaTypeNotSupportedException.class);
        HttpHeaders headers = Mockito.mock(HttpHeaders.class);
        WebRequest request = Mockito.mock(WebRequest.class);

        //when
        ResponseEntity<Object> result = apiExceptionHandler.handleHttpMediaTypeNotSupported(exception, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void should_return_responseEntity_when_missingServletRequestParameter() {

        //given
        MissingServletRequestParameterException exception = Mockito.mock(MissingServletRequestParameterException.class);
        HttpHeaders headers = Mockito.mock(HttpHeaders.class);
        WebRequest request = Mockito.mock(WebRequest.class);

        //when
        ResponseEntity<Object> result = apiExceptionHandler.handleMissingServletRequestParameter(exception, headers, HttpStatus.BAD_REQUEST, request);
        //then
        assertThat(result).isNotNull();
    }


    @Test
    public void should_return_responseEntity_when_httpMessageNotWritablee() {

        //given
        HttpMessageNotWritableException exception = Mockito.mock(HttpMessageNotWritableException.class);
        HttpHeaders headers = Mockito.mock(HttpHeaders.class);
        WebRequest request = Mockito.mock(WebRequest.class);

        //when
        ResponseEntity<Object> result = apiExceptionHandler.handleHttpMessageNotWritable(exception, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void should_return_responseEntity_when_methodArgumentNotValid() {

        //given
        MethodArgumentNotValidException exception = Mockito.mock(MethodArgumentNotValidException.class);
        HttpHeaders headers = Mockito.mock(HttpHeaders.class);
        WebRequest request = Mockito.mock(WebRequest.class);
        given(exception.getBindingResult()).willReturn(bindingResult);

        //when
        ResponseEntity<Object> result = apiExceptionHandler.handleMethodArgumentNotValid(exception, headers, HttpStatus.BAD_REQUEST, request);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void should_return_responseEntity_when_noHandlerFoundException() {

        //given
        NoHandlerFoundException exception = Mockito.mock(NoHandlerFoundException.class);
        HttpHeaders headers = Mockito.mock(HttpHeaders.class);
        WebRequest request = Mockito.mock(WebRequest.class);

        //when
        ResponseEntity<Object> result = apiExceptionHandler.handleNoHandlerFoundException(exception, headers, HttpStatus.BAD_REQUEST, request);
        //then
        assertThat(result).isNotNull();
    }
}
