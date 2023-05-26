package org.stelios.courses.adapter.repositories.events;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventAlreadyExistsException extends Exception {
    private static final String MESSAGE = "Trying to save existing event";

    public EventAlreadyExistsException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
