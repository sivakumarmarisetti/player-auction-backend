package com.auction.player.exception;


public class BusinessLogicException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7271281754536203688L;
	private final int statusCode;

    public BusinessLogicException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
