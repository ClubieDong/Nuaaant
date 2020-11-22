package com.clubie.nuaaant.utils;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class NuaaAntException extends RuntimeException {

    public NuaaAntException(String message) {
        super(message);
    }

}
