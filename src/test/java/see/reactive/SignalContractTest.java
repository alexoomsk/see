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

package see.reactive;

import com.google.common.base.Supplier;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.collect.ImmutableSet.of;
import static java.lang.Integer.valueOf;
import static junit.framework.Assert.assertEquals;

public abstract class SignalContractTest {

    SignalFactory signalFactory;

    @Before
    public void setUp() throws Exception {
        signalFactory = getSignalFactory();
    }

    protected abstract SignalFactory getSignalFactory();

    @Test
    public void testInteraction() throws Exception {
        final VariableSignal<String> a = signalFactory.var("asd");
        Signal<Integer> b = signalFactory.bind(of(a), new Supplier<Integer>() {
            @Override
            public Integer get() {
                return a.now().length();
            }
        });

        assertEquals(valueOf(3), b.now());
        a.set("omg");
        assertEquals(valueOf(3), b.now());
        a.set("zxcv");
        assertEquals(valueOf(4), b.now());
    }

    @Test
    public void testMultipleDeps() throws Exception {
        final VariableSignal<Integer> a = signalFactory.var(1);
        final VariableSignal<Integer> b = signalFactory.var(2);

        Signal<Integer> sum = signalFactory.bind(of(a, b), new Supplier<Integer>() {
            @Override
            public Integer get() {
                return a.now() + b.now();
            }
        });

        assertEquals(valueOf(3), sum.now());

        a.set(7);
        assertEquals(valueOf(9), sum.now());
        
        b.set(35);
        assertEquals(valueOf(42), sum.now());
    }

    @Test
    public void testStatefulSignal() throws Exception {
        final VariableSignal<String> a = signalFactory.var("crno");
        final AtomicInteger lengthCounter = new AtomicInteger(0);

        final Signal<Integer> length = signalFactory.bind(of(a), new Supplier<Integer>() {
            @Override
            public Integer get() {
                lengthCounter.incrementAndGet();
                return a.now().length();
            }
        });

        assertEquals(1, lengthCounter.get());

        final AtomicInteger plusCounter = new AtomicInteger(0);
        final Signal<Integer> plusOne = signalFactory.bind(of(length), new Supplier<Integer>() {
            @Override
            public Integer get() {
                plusCounter.incrementAndGet();
                return length.now() + 1;
            }
        });

        assertEquals(1, lengthCounter.get());
        assertEquals(1, plusCounter.get());

        a.set("bka");
        assertEquals(2, lengthCounter.get());
        assertEquals(2, plusCounter.get());

        assertEquals(valueOf(4), plusOne.now());
        assertEquals(2, lengthCounter.get());
        assertEquals(2, plusCounter.get());
    }
}