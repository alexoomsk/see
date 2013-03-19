/*
 * Copyright 2013 Vasily Shiyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package see.exceptions;

/**
 * Thrown on access to undefined variable
 */
public class NoSuchVariableException extends RuntimeException {
    private final String variable;

    public NoSuchVariableException(String variable) {
        super("Cannot resolve variable " + variable);
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }
}
