package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
// our customize Json structure
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  // Jackson meaning modify your Json output, if anything is not null don't show
public class ResponseWrapper {

    private boolean success;
    private String message;
    private Integer code;
    private Object data;

    public ResponseWrapper(String message, Object data,HttpStatus httpStatus) {
        this.success = true;
        this.message = message;
        this.code = httpStatus.value();
        this.data = data;
    }

    public ResponseWrapper(String message,HttpStatus httpStatus) {
        this.message = message;
        this.code = httpStatus.value();
        this.success = true;
    }

}