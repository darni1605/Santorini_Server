package ch.uzh.ifi.seal.soprafs19.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class HttpException extends RuntimeException{
    public HttpException(String exception) {
        super(exception);
    }
}