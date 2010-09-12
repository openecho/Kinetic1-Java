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
package kinetic.time;

import java.util.Date;

/**
 * Timer Utility Class. Provides all the required time based lookup
 * functionality.
 *
 * Requires an implementation that provides a nano second timer that
 * provides reliable nano second time updates on each call. The basis for
 * the nano second timer is irrelevant.
 * 
 * @author openecho
 * @verion 1.0.0
 */
public abstract class Timer {

    /**
     * Nano Second to Milli Second Conversion factor
     */
    public static final int NANO_MILLI_FACTOR = 1000000;
    /**
     * The calculated date error. This is the minimum
     * time between two dates. This is important because
     * the JVM Date/Time tends to jump all over the place.
     */
    protected long dateError;
    /**
     * The reference date/time.
     */
    protected long refrenceDateTime;
    /**
     * The reference nano timer.
     */
    protected long refrenceNanoTime;
    /**
     * Singleton instance of timer
     */
    protected static Timer timerInstance;
    /**
     * Date instances for basis construction.
     */
    Date dateOne = null;
    Date dateTwo = null;

    /**
     * Default Constructor
     */
    Timer() {
        setRefrenceData();
    }

    /**
     * Retrieves the Timer Singleton Instance.
     *
     * @return Timer Instance.
     */
    public static Timer getInstance() {
        if (timerInstance == null) {
            timerInstance = getTimerInstance();
        }
        return timerInstance;
    }

    /**
     * Retrieves a/the concrete instance of Timer.
     *
     * @return Timer
     */
    private static Timer getTimerInstance() {
        return new SystemTimer();
    }

    /**
     * Sets all the required reference information for this timer
     * instance.
     *
     * After calling this method the Timer will have a valid date
     * error, reference date and reference nano time.
     */
    final void setRefrenceData() {
        long tmpRefrenceNanoTime = 0;

        do {
            dateOne = new Date();
            dateTwo = new Date();
            tmpRefrenceNanoTime = getNanoSecondTime();
        } while (dateOne.getTime() == dateTwo.getTime());

        dateError = dateTwo.getTime() - dateOne.getTime();
        refrenceDateTime = dateTwo.getTime();
        refrenceNanoTime = tmpRefrenceNanoTime;
    }

    /**
     * Find the Date milli second error between two consecutive constructions.
     * @return long error milli seconds time.
     */
    public long getDateError() {
        return this.dateError;
    }

    /**
     * Find the basis nano second time for the Timer.
     * @return long basis nano second time.
     */
    public long getRefrenceNanoTime() {
        return this.refrenceNanoTime;
    }

    /**
     * Find the basis Date instance for the Timer.
     * @return long basis date time.
     */
    public long getRefrenceDateTime() {
        return this.refrenceDateTime;
    }

    /**
     * Find the basis Date instance for the Timer. This constructs a date. Use
     * getRefrenceDateTime() and manage Date construction.
     * @return Date basis date.
     */
    public Date getRefrenceDate() {
        return new Date(refrenceDateTime);
    }

    /**
     * Retrieves a nano second reference time. This should be accurate but does
     * not require a stable basis.
     * @return long current nano second counter value
     */
    public abstract long getNanoSecondTime();

    /**
     * Retrieves the milli second reference time. This is based on the nano
     * second reference time.
     * @see getNanoSecondTime()
     * @return long current milli second counter value
     */
    public final long getMilliSecondTime() {
        return Timer.nanoSecondsToMilliSeconds(getNanoSecondTime());
    }

    /**
     * Retrieves the best attempt at a correct millisecond date/time long.
     *
     * This method will pull the number of nano seconds since the reference
     * date/time and the current date time from the JVM. If the values are
     * out in either direction by the maximum date error then we need to
     * re-fetch reference information.
     *
     * @return Date/Time long Current date time. In the worst case, this can
     * go backwards in time.
     */
    public final long getMilliSecondDateTime() {
        long currentNanoTime = getNanoSecondTime();
        long deltaTime = currentNanoTime - getRefrenceNanoTime();
        long currentMilliTime = getRefrenceDateTime() + nanoSecondsToMilliSeconds(deltaTime);
        long currentDateTime = new Date().getTime();
        /**
         * If the time on the machine changes this gets all out of wack. A
         * System may also be very bad at keeping time (worse than normal).
         * We must check this every loop.
         */
        if (Math.abs(currentDateTime - currentMilliTime) > dateError) {
            setRefrenceData();
            return currentDateTime;
        } else {
            return currentMilliTime;
        }
    }

    /**
     * Convert nano seconds to milli seconds.
     */
    public static long nanoSecondsToMilliSeconds(long nanoSeconds) {
        return nanoSeconds / NANO_MILLI_FACTOR;
    }

    /**
     * Convert milli seconds to nano seconds.
     */
    public static long milliSecondsToNanoSeconds(long milliSeconds) {
        return milliSeconds * NANO_MILLI_FACTOR;
    }

    /**
     * Convert nano seconds to a decimal milli second value that is human
     * readable.
     */
    public static double nanoSecondsToReadableMilliSeconds(long nanoSeconds) {
        return nanoSeconds / (double) NANO_MILLI_FACTOR;
    }
}
