package de.florianmarsch.fussballmanager.db;

public class AccessLayerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1549404252315860222L;

	public AccessLayerException(String aMessage, Throwable aCause){
		super(aMessage, aCause);
	}
}
