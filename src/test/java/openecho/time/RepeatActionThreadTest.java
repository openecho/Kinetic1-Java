/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.time;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author jmarsden
 */
public class RepeatActionThreadTest extends TestCase {
    
    public RepeatActionThreadTest(String testName) {
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
     * Test of getCurrentState method, of class RepeatActionThread.
     */
    public void testRunInstance() {
        System.out.println("getCurrentState");
        RepeatActionThread instance = new RepeatActionThread(1);
        instance.registerAction(new TestRepeatableAction());
        instance.start();
        for(int i=0;i<10;i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RepeatActionThreadTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        instance.stop();
    }

}
