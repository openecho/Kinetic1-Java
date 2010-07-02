/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.time;

import junit.framework.TestCase;

/**
 *
 * @author jmarsden
 */
public class SystemTimerTest extends TestCase {
    
    public SystemTimerTest(String testName) {
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
     * Test of getNanoSecondTime method, of class SystemTimer.
     */
    public void testGetNanoSecondTime() {
        System.out.println("getNanoSecondTime");
        SystemTimer instance = new SystemTimer();
        for(int i=0;i<100;i++) {
            long time1 = instance.getNanoSecondTime();
            long time2 = instance.getNanoSecondTime();
            assertTrue(time1 < time2);
        }
    }
}
