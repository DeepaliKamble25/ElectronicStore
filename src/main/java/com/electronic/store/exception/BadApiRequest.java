package com.electronic.store.exception;

import com.electronic.store.playload.ApiConstant;

public class BadApiRequest extends RuntimeException {

    public BadApiRequest(String message) {
        super(message);
    }
    public BadApiRequest() {
        super(ApiConstant.Bad_ApiRequest_Message);
    }
}
