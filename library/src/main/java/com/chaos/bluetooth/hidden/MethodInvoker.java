/*
 * Copyright 2017 Chaos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chaos.bluetooth.hidden;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class MethodInvoker {

    private static final String TAG = "MethodInvoker";

    private Class argTypes[];
    private Object instance;
    private Method method;
    private String methodName;

    public MethodInvoker(Object instance, String methodName) {
        this(instance, methodName, (Class[]) null);
    }

    public MethodInvoker(Object instance, String methodName, Class argType) {
        this(instance, methodName, new Class[]{argType});
    }

    public MethodInvoker(Object instance, String methodName, Class argTypes[]) {
        this.instance = instance;
        this.methodName = methodName;
        this.argTypes = argTypes;

        try {
            findMethod();
        } catch (NoSuchMethodException e) {
            Log.e(TAG, String.format("Can't find the method: %s in instance: %s", methodName, instance), e);
        }
    }

    @SuppressWarnings("unchecked")
    private boolean findMethod() throws NoSuchMethodException {
        if (instance == null) {
            Log.e(TAG, String.format("findMethod() cannot find method named: %s on a null instance.", methodName));
            return false;
        }

        if (instance instanceof Class) {
            method = ((Class) instance).getMethod(methodName, argTypes);
        } else {
            method = instance.getClass().getMethod(methodName, argTypes);
        }

        return method != null;
    }

    private void ensureValidMethod() throws NoSuchMethodException {
        if (!isMethodValid()) {
            Log.e(TAG, "invoke() called without a valid method!");
            throw new NoSuchMethodException(String.format("Didn't bind to method: %s on instance: %s", methodName, instance));
        }
    }

    private void handleInvocationError(Exception exception, Object args[]) {
        String log = String.format("Caught exception when invoking method: %s on instance=%s args=[\n", methodName, instance);
        if (args != null) {
            for (Object arg : args) {
                log += (arg == null ? "null" : arg.toString()) + "\n";
            }
        }
        Log.e(TAG, log + "]\n", exception);
    }

    public final Object invoke(Object... args) {
        try {
            ensureValidMethod();
            return method.invoke(instance, args);
        } catch (IllegalArgumentException e) {
            handleInvocationError(e, args);
        } catch (IllegalAccessException e) {
            handleInvocationError(e, args);
        } catch (InvocationTargetException e) {
            handleInvocationError(e, args);
        } catch (NoSuchMethodException e) {
            handleInvocationError(e, args);
        }
        return null;
    }

    public final boolean isMethodValid() {
        return method != null;
    }

}