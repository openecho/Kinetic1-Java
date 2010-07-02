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
 * Stop watch functionality. Can be used to accurately time executions down
 * down to nano time.
 *
 * @author openecho
 * @version 1.0.0
 */
public class StopWatch {

    /**
     * Timer instance for all time lookups.
     */
    public Timer timer;
    /**
     * Nano time that this timer was started.
     */
    public long startNanoTime;
    /**
     * Nano time that this timer was stopped.
     */
    public long stopNanoTime;
    /**
     * Nano time for the cumulative delta times for all start and stops since
     * construction.
     */
    public long cumulativeNanoTime;

    /**
     * Default Constructor. If no Timer instance is provided then this
     * constructor may cause one to be created (which may result in a gc).
     */
    public StopWatch() {
        this(null);
    }

    /**
     * Constructor that allows an instance of timer to be specified.
     * @param instance Timer instance to use for time lookups.
     */
    public StopWatch(Timer instance) {
        if (instance == null) {
            timer = Timer.getInstance();
        } else {
            timer = instance;
        }
        startNanoTime = -1;
        stopNanoTime = -1;
        cumulativeNanoTime = -1;
    }

    /**
     * Gets the nano time that this timer was last started.
     * @return long nano time start.
     */
    public long getStartNanoTime() {
        return this.startNanoTime;
    }

    /**
     * Gets the nano time that this timer was last stopped.
     * @return long nano time start.
     */
    public long getStopNanoTime() {
        return this.stopNanoTime;
    }

    /**
     * Gets the nano time that this timer was last run for (delta).
     * @return long nano time delta.
     */
    public long getDeltaNanoTime() {
        return this.stopNanoTime - this.startNanoTime;
    }

    /**
     * Get the current cumulative nano time.
     * @return long cumulative nano time.
     */
    public long getCumulativeNanoTime() {
        return cumulativeNanoTime;
    }

    /**
     * Get the current cumulative nano time.
     * @return long cumulative nano time.
     */
    public long time() {
        return cumulativeNanoTime;
    }

    /**
     * Start the timer.
     * @return long zero.
     */
    public synchronized long start() {
        startNanoTime = timer.getNanoSecondTime();
        return 0L;
    }

    /**
     * Stop the timer.
     * @return long nano second delta that this Timer was stopped.
     */
    public synchronized long stop() {
        long deltaNanoTime = (stopNanoTime = timer.getNanoSecondTime()) - startNanoTime;
        cumulativeNanoTime += deltaNanoTime;
        return deltaNanoTime;
    }

    /**
     * Reset the timer.
     * @return long zero.
     */
    public synchronized long reset() {
        return cumulativeNanoTime = startNanoTime = stopNanoTime = 0L;
    }
}
