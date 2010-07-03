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
public class RepeatActionThread implements Runnable {

    public static final int CREATED = 0;
    public static final int RUNNING = 1;
    public static final int PAUSED = 2;
    public static final int STOPPED = 3;
    protected int actionCount;
    protected int actionMarker;
    protected RepeatedAction[] actions;
    protected int state;
    protected Thread internalThread;
    /**
     * Holds the period of the loop thread.
     */
    protected int loopPeriodMilli;
    protected long loopPeriodNano;
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
     * Target loop time for this loop
     */
    protected long loopPeriodTargetNano = 0;
    /**
     * Holds the nano time that the Thread will be requested to sleep in
     * this iteration of the game loop.
     */
    protected long loopSleepTimeNano = 0;
    /**
     * Holds the lower bound for sleep time.
     */
    protected long minimumLoopSleepTimeMilli = 0;
    /**
     * Holds the default period for all loops.
     */
    public static final int DEFAULT_LOOP_PERIOD_MILLIS;
    /**
     * Holds the lower bounds for loop sleep times.
     */
    public static final int DEFAULT_MINIMUM_SLEEP_TIME_MILLIS;

    static {
        DEFAULT_LOOP_PERIOD_MILLIS = 100;
        DEFAULT_MINIMUM_SLEEP_TIME_MILLIS = 1;
    }

    /**
     * Constructor for Loop Thread.
     * @param loopPeriod The period that the loop thread will cycle at.
     */
    public RepeatActionThread(int timedActionCount) {
        this.actionCount = timedActionCount;
        this.actionMarker = 0;
        this.actions = new RepeatedAction[timedActionCount];
        this.state = CREATED;
        this.internalThread = null;
        this.loopPeriodMilli = DEFAULT_LOOP_PERIOD_MILLIS;
        this.loopPeriodNano = Timer.milliSecondsToNanoSeconds(DEFAULT_LOOP_PERIOD_MILLIS);
    }

    public int getCurrentState() {
        return this.state;
    }

    /**
     * Mutate method for loop period.
     * @param loopPeriod The period that the LoopThread will run at in milliseconds.
     * @return The loop period.
     */
    public int setLoopPeriod(int loopPeriod) {
        loopPeriodNano = Timer.milliSecondsToNanoSeconds(loopPeriod);
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

    public void registerAction(RepeatedAction a) {
        for (int i = 0; i < actionMarker; i++) {
            if (a == actions[i]) {
                return;
            }
        }
        if (actionMarker < actionCount) {
            actions[actionMarker] = a;
            actionMarker++;
        } else {
            throw new RuntimeException("To Many Actions Registered. Make the LoopThread Larger.");
        }
    }

    public void unregisterAction(RepeatedTimedAction a) {
        for (int i = 0; i < actionCount; i++) {
            if (a == actions[i]) {
                // Shuffle the rest of the actions along.
                for (; i < actionCount - 1; i++) {
                    actions[i] = actions[i + 1];
                }
                actions[actionCount--] = null;
                return;
            }
        }
    }

    public int getActionCount() {
        return actionCount;
    }

    public RepeatedAction getAction(int i) {
        return actions[i];
    }

    public RepeatedAction[] getActions() {
        return actions;
    }

    public RepeatedAction[] clearActions() {
        for (int i = 0; i < actionCount; i++) {
            actions[i] = null;
        }
        actionCount = 0;
        return actions;
    }

    public synchronized void start() {
        if (state == PAUSED) {
            internalThread.interrupt();
        } else {
            state = RUNNING;
            internalThread = new Thread(this);
            internalThread.start();
        }
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

    public void run() {
        StopWatch actionWatch = new StopWatch();
        StopWatch loopWatch = new StopWatch();
        StopWatch sleepWatch = new StopWatch();
        loopStartTimeMilli = Timer.getInstance().getMilliSecondDateTime();
        state = RUNNING;
        while (state != STOPPED) {
            while (state == PAUSED) {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                /**
                * Normal Interupt. WIll sleep if still paused
                * otherwise will fall out.
                */
                }
            }
            loopWatch.start();
            actionWatch.start();
            runActions();
            actionWatch.stop();
            loopPeriodTargetNano = loopPeriodNano + loopErrorAdjustNano;
            loopSleepTimeNano = loopPeriodTargetNano - actionWatch.getDeltaNanoTime();
            sleepWatch.start();
            int sleepTimeMilli = (int) Timer.nanoSecondsToMilliSeconds(loopSleepTimeNano);
            if (sleepTimeMilli < 0) {
                sleepTimeMilli = 1;
            }
            try {
                Thread.sleep(sleepTimeMilli);
            } catch (InterruptedException e) {
            /**
            * Normal Interupt.
            */
            }
            sleepWatch.stop();
            loopWatch.stop();
            loopErrorAdjustNano = loopPeriodTargetNano - loopWatch.getDeltaNanoTime();
        }
    }

    /**
     * Executes the Repeated Actions currently registered. Any errors reported
     * must but stored in the Repeated Action.
     */
    void runActions() {
        for (int i = 0; i < actionMarker; i++) {
            RepeatedAction action = actions[i];
            try {
                action.execute();
            } catch (ActionException ae) {
                action.actionException = ae;
            }
        }
    }
}
