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

package see.functions.reactive;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import see.evaluation.Context;
import see.functions.ContextCurriedFunction;
import see.functions.Settable;
import see.functions.VarArgFunction;
import see.reactive.Signal;
import see.reactive.SignalFactory;

import javax.annotation.Nonnull;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;

public class Bind implements ContextCurriedFunction<Object, Signal<?>> {
    @Override
    public VarArgFunction<Object, Signal<?>> apply(@Nonnull final Context context) {
        return new VarArgFunction<Object, Signal<?>>() {
            @Override
            public Signal<?> apply(@Nonnull List<Object> input) {
                Preconditions.checkArgument(input.size() == 2, "Bind takes two arguments");

                final Settable<Object> target = (Settable<Object>) input.get(0);

                final Signal<?> signal = (Signal<?>) input.get(1);

                context.getServices().getInstance(SignalFactory.class).bind(of(signal), new Supplier<Object>() {
                    @Override
                    public Object get() {
                        Object now = signal.now();
                        target.set(now);
                        return now;
                    }
                });

                return signal;

            }
        };
    }

    @Override
    public String toString() {
        return "bind";
    }
}
