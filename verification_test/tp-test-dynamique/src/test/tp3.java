package TP3;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*; 	

@RunWith(MockitoJUnitRunner.class)
public class test1 {

	GregorianCalendar doublure = mock(GregorianCalendar.class);
	
	@Test
	public void test11() {
		Timer T = null;
		try {
			T = new Timer(1, -1, 1);
	    } catch (TimerException var3) {
	        System.out.println(var3);
	    } 
		T.setActive(true,doublure);
		assertFalse(T.isRinging());
	}

	@Test
	public void test12() {
		Timer T = null;
		try {
			T = new Timer(1, 42, 1);
	    } catch (TimerException var3) {
	        System.out.println(var3);
	    } 
		T.setActive(true,doublure);
		assertFalse(T.isRinging());
	}


	@Test
	public void test13() {
		Timer T = null;
		try {
			T = new Timer(1,12,-1);
	    } catch (TimerException var3) {
	        System.out.println(var3);
	    } 
		T.setActive(true,doublure);
		assertFalse(T.isRinging());
	}


	@Test
	public void test14() {
		Timer T = null;
		try {
			T = new Timer(1,12,144);
	    } catch (TimerException var3) {
	        System.out.println(var3);
	    } 
		T.setActive(true,doublure);
		assertFalse(T.isRinging());
	}


	
	
}