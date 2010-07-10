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
 * Base Repeated Action with minimal overhead.
 * @author openecho
 * @version 1.0.0
 */
public abstract class RepeatedAction {

    /**
     * The number of times this action has been called.
     */
    protected long tick;

    /**
     * Holds the last Action Exception caught when this
     * Repeated Action was run. Any calling class should set this
     * in the event of an error.
     */
    public ActionException actionException;

    public RepeatedAction() {
        tick = 0;
        actionException = null;
    }
    /**
     * Get the name of the RepeatedAction
     * @return String name.
     */
    public String getName() {
        return getClass().getName();
    }

    /**
     * Execute the Repeated Action.
     * @throws ActionException Errors that may occur during the Action execution.
     */
    void execute() throws ActionException {
        // Run Action
        handleExecute(tick++);
    }

    /**
     * Execution Handler. Sub Classes must implement this.
     * @param tick The current tick.
     * @throws ActionException Error that occurs during the execution.
     */
    protected abstract void handleExecute(long tick) throws ActionException;
}
