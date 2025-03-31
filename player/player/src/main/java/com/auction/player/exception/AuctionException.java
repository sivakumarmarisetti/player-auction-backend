package com.auction.player.exception;

public class AuctionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8095988309471349523L;
	private final int statusCode;

    public AuctionException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
