/*
 * Copyright 2011 Vasily Shiyan
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

package see.evaluation;

import java.util.Map;

public interface Context {

    /**
     * Get value associated with given key
     * @param key name of variable or constant
     * @return variable value, null if absent
     */
    Object get(String key);

    /**
     * Set variable to given value
     * @param key variable
     * @param value new value
     */
    void put(String key, Object value);

    /**
     * Get immutable view of all variables in context
     * @return immutable view
     */
    Map<String, ?> asMap();

    /**
     * Get service a object (property resolver, etc.) by it's class
     *
     * @param serviceClass service class
     * @return service instance, null if not present
     */
    <T> T getService(Class<T> serviceClass);
}