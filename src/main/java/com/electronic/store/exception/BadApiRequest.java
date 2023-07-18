package com.electronic.store.exception;

import com.electronic.store.playload.ApiConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadApiRequest extends RuntimeException {

    public static Logger logger= LoggerFactory.getLogger(BadApiRequest.class);
    /**
     * @author Deepali Kamble
     * @apiNote Bad api request
     * @param message
     */
    public BadApiRequest(String message) {

        super(message);
    }
    public BadApiRequest() {


        super(ApiConstant.Bad_ApiRequest_Message);
        logger.info("Initiating BadApiRequest: {}");
    }
}
