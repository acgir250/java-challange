package jp.co.axa.apidemo.errors.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * This class is the model class of the apierror to be given in the response to the user.
 */
@Value
public class ApiError {

    @JsonProperty("status")
    private HttpStatus status;
    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Instant timestamp;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("trace")
    private String debugMessage;

}
