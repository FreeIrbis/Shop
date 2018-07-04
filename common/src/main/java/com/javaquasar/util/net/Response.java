package com.javaquasar.util.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Java Quasar on 06.09.17.
 */
public class Response {
    private int code;
    private byte[] body;

    public Response(int code) {
        this.code = code;
    }

    public Response(int code, byte[] body) {
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getStringBody() throws UnsupportedEncodingException {
        return new String(body, StandardCharsets.UTF_8.name());
    }

    public String getStringBody(String charsets) throws UnsupportedEncodingException {
        return new String(body, charsets);
    }
}
