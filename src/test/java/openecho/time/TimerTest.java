/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.time;

import java.util.Date;
import junit.framework.TestCase;

/**
 *
 * @author jmarsden
 */
public class TimerTest extends TestCase {
    
    public TimerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getInstance method, of class Timer.
     */
    public void testGetInstance() {
        System.out.println("getInstance");
        Timer expResult = Timer.getInstance();
        Timer result = Timer.getInstance();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRefrenceData method, of class Timer.
     */
    public void testSetRefrenceData() {
        System.out.println("setRefrenceData");
        Timer instance = new TimerImpl();
        instance.setRefrenceData();
        assertTrue(instance.getDateError() > 0);
        assertNotNull(instance.getRefrenceDate());
        assertTrue(instance.getRefrenceNanoTime() >= 0);
    }

    /**
     * Test of getNanoSecondTime method, of class Timer.
     */
    public void testGetNanoSecondTime() {
        System.out.println("getNanoSecondTime");
        Timer instance = new TimerImpl();
        long result = instance.getNanoSecondTime();
        assertTrue(result > 0);
    }


    public class TimerImpl extends Timer {
        public long getNanoSecondTime() {
            return System.nanoTime();
        }
    }
}
