package fr.nantes.timer;

public class TimerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msg;

	public TimerException(String msg) {
		this.msg = msg;
	}

	public String toString() {
		return msg + " : " + super.toString();
	}

}