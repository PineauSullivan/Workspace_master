/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nantes.timer;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author sunye
 */
public class TimerStructuralTest {

    public TimerStructuralTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of selectRing method, of class Timer.
     */
    @Test
    public void testSelectRing() throws Exception {
        System.out.println("selectRing");
        int ring = 0;
        Timer instance = null;
        instance.selectRing(ring);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMin method, of class Timer.
     */
    @Test
    public void testAddMin() throws Exception {
        System.out.println("addMin");
        int addedmin = 0;
        Timer instance = null;
        instance.addMin(addedmin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActive method, of class Timer.
     */
    @Test
    public void testSetActive() {
        System.out.println("setActive");
        boolean active = false;
        Timer instance = null;
        instance.setActive(active);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Timer.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object object = null;
        Timer instance = null;
        boolean expResult = false;
        boolean result = instance.equals(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Timer.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Timer instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Timer.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Timer.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
