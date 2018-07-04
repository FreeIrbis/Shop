package com.shop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShopException extends RuntimeException {

    public ShopException(String msg) {
        super(msg);
    }

    public ShopException(Exception exception) {
        super(exception);
    }

    public ShopException(String format, Object... args) {
        super(String.format(format, args));
    }

}
