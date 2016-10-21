package fr.nantes.timer;

public class Calendarstub extends Calendar{

	public final static int HOUR = 1;
	public final static int MINUTE = 2;

	public Calendarstub() {
		super();
		System.out.println("A Calendar is instantiated");
	}

	public int get(int field) {
		System.out.println("Calendar used");
		return 12;
	}
}
