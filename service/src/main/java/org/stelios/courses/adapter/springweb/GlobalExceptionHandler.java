package org.stelios.courses.adapter.springweb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.stelios.courses.adapter.repositories.events.EventAlreadyExistsException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleExceptions(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler({EventAlreadyExistsException.class})
    public ResponseEntity<String> handleEventAlreadyExistsException(EventAlreadyExistsException ex) {
        log.debug("Handling exception: [{}]", ex.getMessage());

        return ResponseEntity.ok("Event with this id already exists");
    }
}
