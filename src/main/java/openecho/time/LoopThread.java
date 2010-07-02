/**
 * Copyright (C) 2010 openecho
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package openecho.time;

/**
 *
 * @author openecho
 * @version 1.0.0
 */
public class LoopThread implements Runnable {

    public static final int CREATED = 0;
    public static final int RUNNING = 0;
    public static final int PAUSED = 1;
    public static final int STOPPED = 2;

    protected int actionCount;
    protected int actionMarker;
    protected RepeatedTimedAction[] actions;
    protected int state;
    protected Thread internalThread;
    /**
     * Holds the period of the loop thread.
     */
    protected int loopPeriodMilli;
    /**
     * Holds the start time of the Thread.
     */
    protected long loopStartTimeMilli;
    /**
     * Holds the nano time required to adjust the next sleep by due to
     * the previous loops time errors.
     *
     * This is due to the time error in Java's Thread.sleep() method and the
     * differences in times the actions can take.
     */
    protected long loopErrorAdjustNano = 0;
    /**
     * Holds the total adjustment required to the current loops sleep time
     * based on time to execute actions.
     */
    protected long loopSleepTotalAdjustNano = 0;
    /**
     * Holds the nano time that the Thread will be requested to sleep in
     * this itteration of the game loop.
     */
    protected long loopSleepTimeNano = 0;
    public static final int DEFAULT_LOOP_PERIOD_MILLIS;
    public static final int MINIMUM_SLEEP_TIME_MILLIS;

    static {
        DEFAULT_LOOP_PERIOD_MILLIS = 100;
        MINIMUM_SLEEP_TIME_MILLIS = 5;
    }

    /**
     * Constructor for Loop Thread.
     *
     * @param loopPeriod The period that the loop thread will cycle at.
     */
    public LoopThread(int timedActionCount) {
        this.actionCount = timedActionCount;
        this.actionMarker = 0;
        this.actions = new RepeatedTimedAction[timedActionCount];
        this.state = CREATED;
        this.internalThread = null;
        this.loopPeriodMilli = DEFAULT_LOOP_PERIOD_MILLIS;

    }

    public int getCurrentState() {
        return this.state;
    }

    /**
     * Mutate method for loop period.
     *
     * @param loopPeriod The period that the LoopThread will run at in milliseconds.
     * @return The loop period.
     */
    public int setLoopPeriod(int loopPeriod) {
        return loopPeriodMilli = loopPeriod;
    }

    /**
     * Access Method for loop period.
     *
     * @return The loop period.
     */
    public int getLoopPeriod() {
        return loopPeriodMilli;
    }

    public void registerAction(RepeatedTimedAction a) {
        for(int i=0;i<actionMarker;i++) {
            if(a==actions[i]) {
                return;
            }
        }
        if(actionMarker < actionCount) {
            actions[actionMarker]=a;
            actionMarker++;
        } else {
            throw new RuntimeException("To Many Actions Registered. Make the LoopThread Larger.");
        }
    }

    public void unregisterAction(RepeatedTimedAction a) {
        for(int i=0;i<actionCount;i++) {
            if(a==actions[i]) {
                
            }
        }
    }

    public int getActionCount() {
        return actionCount;
    }

    public RepeatedTimedAction getAction(int i) {
        return actions[i];
    }

    public RepeatedTimedAction[] getActions() {
        return actions;
    }

    public RepeatedTimedAction[] clearActions() {
        for(int i=0;i<actionCount;i++) {
            actions[i]=null;
        }
        actionCount = 0;
        return actions;
    }

    public synchronized void start() {
        state = RUNNING;
        if (state == PAUSED) {
            internalThread.interrupt();
        } else {
            internalThread = new Thread(this);
            internalThread.start();
        }
    }

    public void run() {

    }

    public synchronized void pause() {
        state = PAUSED;
        internalThread.interrupt();
    }

    public synchronized void stop() {
        state = STOPPED;
        internalThread.interrupt();
        internalThread = null;
    }

    void runActions() {
        for (int i = 0; i < actionMarker; i++) {
            RepeatedTimedAction action = actions[i];
            try {
                action.execute();
            } catch (ActionException ae) {
                // Add exception to action.
                ae.printStackTrace();
            }
        }
    }
}