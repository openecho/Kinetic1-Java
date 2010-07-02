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
 * Timed Action Exception. TimedAction's may throw this exception.
 *
 * @author openecho
 * @version 1.0.0
 */
public class TimedActionException extends Exception {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 695382805099000726L;

        /**
         * Default Constructor
         */
	public TimedActionException() {
		super();
	}
	/**
         * Constructor With Message
         * @param message message.
         */
	public TimedActionException(String message) {
		super(message);
	}
	/**
         * Constructor with stacktrace
         * @param throwable throwable stack
         */
	public TimedActionException(Throwable throwable) {
		super(throwable);
	}
	/**
         * Constructor with message and throwable
         * @param message message.
         * @param throwable throwable stack.
         */
	public TimedActionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
