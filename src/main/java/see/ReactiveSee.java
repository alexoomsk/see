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

package see;

import com.google.common.collect.Maps;
import see.functions.reactive.ReactiveFunction;
import see.reactive.impl.ReactiveFactory;
import see.tree.Node;

import java.util.Map;

/**
 * Public API for working with reactive extensions(signals, bindings, etc.).
 * Holds {@link See} and {@link ReactiveFactory} instances.
 * Evaluation methods taking context work in new context.
 */
@SuppressWarnings("UnusedDeclaration")
public class ReactiveSee {
    private final See see;
    private final ReactiveFactory reactiveFactory;

    /**
     * Create new instance, custom See instance and reactive factory.
     * @param see see instance
     * @param factory reactive factory
     */
    public ReactiveSee(See see, ReactiveFactory factory) {
        this.see = see;
        this.reactiveFactory = factory;
    }

    /**
     * Create new instance, See configured with supplied configuration.
     * @param see see instance
     */
    public ReactiveSee(See see) {
        this(see, new ReactiveFactory());
    }

    /**
     * Create new instance, default See configuration, external ReactiveFactory.
     * @param reactiveFactory reactive factory
     */
    public ReactiveSee(ReactiveFactory reactiveFactory) {
        this(new See(), reactiveFactory);
    }

    /**
     * Create new instance with default See configuration.
     */
    public ReactiveSee() {
        this(new See(), new ReactiveFactory());
    }

    /**
     * Get inner reactive factory.
     * @return reactive factory
     */
    public ReactiveFactory getReactiveFactory() {
        return reactiveFactory;
    }

    /**
     * Parse a single expression
     *
     * @param expression text to parse
     * @return parsed tree
     */
    public Node<Object> parseExpression(String expression) {
        return see.parseExpression(expression);
    }

    /**
     * Parse semicolon-separated list of expressions
     *
     * @param expression text to parse
     * @return parsed tree
     */
    public Node<Object> parseExpressionList(String expression) {
        return see.parseExpressionList(expression);
    }

    /**
     * Evaluate tree with supplied variables.
     * Evaluation context will contain a reference to ReactiveFactory under special key.
     *
     * @param tree tree to evaluate
     * @param context variable->value mapping
     * @param <T> return type
     * @return evaluated value
     */
    public <T> T evaluate(Node<T> tree, final Map<String, Object> context) {
        return see.evaluate(tree, getReactiveContext(context));
    }

    /**
     * Evaluate tree with empty context.
     *
     * @param tree tree to evaluate
     * @param <T> return type
     * @return evaluated value
     */
    public <T> T evaluate(Node<T> tree) {
        return evaluate(tree, Maps.<String, Object>newHashMap());
    }

    /**
     * Parse and evaluate simple expression.
     * Equivalent to evaluate(parseExpression(expression), context).
     * @param expression expression to evaluate
     * @param context variable->value mapping
     * @return evaluated value
     */
    public Object eval(String expression, Map<String, Object> context) {
        return evaluate(parseExpression(expression), context);
    }

    /**
     * Parse and evaluate simple expression with empty context.
     * Equivalent to evaluate(parseExpression(expression), context).
     * @param expression expression to evaluate
     * @return evaluated value
     */
    public Object eval(String expression) {
        return eval(expression, Maps.<String, Object>newHashMap());
    }

    /**
     * Create new context, which contains ReactiveFactory.
     * @param context initial context
     * @return context with reactive factory
     */
    private Map<String, Object> getReactiveContext(Map<String, Object> context) {
        Map<String, Object> reactiveContext = Maps.newHashMap(context);
        reactiveContext.put(ReactiveFunction.REACTIVE_KEY, reactiveFactory);
        return reactiveContext;
    }
}
