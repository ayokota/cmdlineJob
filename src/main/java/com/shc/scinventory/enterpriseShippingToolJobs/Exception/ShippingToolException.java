package com.shc.scinventory.enterpriseShippingToolJobs.Exception;

import com.shc.scinventory.enterpriseShippingToolJobs.Enums.ShippingToolStatusCode;

public class ShippingToolException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected ShippingToolStatusCode statusCode;

    public ShippingToolStatusCode getStatusCode() {
        return statusCode;
    }

    public ShippingToolException(String errorMessage) {
        super(errorMessage);
        statusCode = ShippingToolStatusCode.UNKNOWN_ERROR;
    }

    public ShippingToolException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.statusCode = ShippingToolStatusCode.GENERAL_ERROR;
    }

    public ShippingToolException(String errorMessage, ShippingToolStatusCode statusCode) {
        super(errorMessage);
        this.statusCode = statusCode;
    }

    public ShippingToolException(String errorMessage, ShippingToolStatusCode statusCode, Throwable cause) {
        super(errorMessage, cause);
        this.statusCode = statusCode;
    }
}
