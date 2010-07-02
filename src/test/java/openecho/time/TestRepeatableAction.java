/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package openecho.time;

/**
 *
 * @author jmarsden
 */
public class TestRepeatableAction extends RepeatedAction {

    Timer timer = Timer.getInstance();
    StopWatch stopWatch = new StopWatch(timer);

    @Override
    public String getName() {
        return "TestRepeatableAction";

    }

    @Override
    protected void handleExecute(long tick) throws ActionException {
        stopWatch.stop();
        System.out.println(this.getClass().getName() + " - tick " + tick + ":\t" + Timer.nanoSecondsToReadableMilliSeconds(stopWatch.getDeltaNanoTime()));
        stopWatch.start();
    }
}
