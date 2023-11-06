package com.example.dronedeliveryapp.util.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"message", "error"})
public class ResponseError implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("error")
    private String error;

    @JsonProperty("errors")
    private Map<String, Object> errors;

    public ResponseError(ResponseErrorBuilder responseErrorBuilder) {
        Assert.notNull(responseErrorBuilder, "Response error builder cannot be null");

        this.message = responseErrorBuilder.message;

        Assert.notNull(responseErrorBuilder.error, "Response error cannot be null");
        this.error = responseErrorBuilder.error;

        this.errors = responseErrorBuilder.errors;
    }

    public static ResponseErrorBuilder builder() {
        return new ResponseErrorBuilder();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResponseError that)) return false;
        return Objects.equals(message, that.message) && Objects.equals(error, that.error) && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, error, errors);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ResponseError.class.getSimpleName() + "[", "]")
                .add("message='" + message + "'")
                .add("error='" + error + "'")
                .add("errors=" + errors)
                .toString();
    }

    public static class ResponseErrorBuilder {
        private String message;
        private String error;
        private Map<String, Object> errors = null;

        public ResponseErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseErrorBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ResponseErrorBuilder errors(Map<String, Object> errors) {
            this.errors = errors;
            return this;
        }

        public ResponseError build() {
            return new ResponseError(this);
        }
    }
}
