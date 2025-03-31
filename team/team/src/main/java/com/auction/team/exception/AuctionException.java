package com.auction.team.exception;

public class AuctionException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8333948294006936811L;
	private final int statusCode;

    public AuctionException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
