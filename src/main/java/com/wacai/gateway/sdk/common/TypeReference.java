package com.wacai.gateway.sdk.common;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This generic abstract class is used for obtaining full generics type information by sub-classing;
 * it must be converted to implementation (implemented by <code>JavaType</code>
 * from "databind" bundle) to be used. Class is based on ideas from <a
 * href="http://gafter.blogspot.com/2006/12/super-type-tokens.html" >http://gafter.blogspot.com/2006/12/super-type-tokens.html</a>,
 * Additional idea (from a suggestion made in comments of the article) is to require bogus
 * implementation of <code>Comparable</code> (any such generic interface would do, as long as it
 * forces a method with generic type to be implemented). to ensure that a Type argument is indeed
 * given. <p> Usage is by sub-classing: here is one way to instantiate reference to generic type
 * <code>List&lt;Integer&gt;</code>:
 * <pre>
 *  TypeReference ref = new TypeReference&lt;List&lt;Integer&gt;&gt;() { };
 * </pre>
 * which can be passed to methods that accept TypeReference, or resolved using
 * <code>TypeFactory</code> to obtain.
 */

public abstract class TypeReference<T> {

    private final Type type;

    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();

        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
