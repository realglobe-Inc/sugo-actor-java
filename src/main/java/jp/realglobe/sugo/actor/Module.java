/*----------------------------------------------------------------------
 * Copyright 2017 realglobe Inc.
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
 *----------------------------------------------------------------------*/

package jp.realglobe.sugo.actor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * モジュール
 */
final class Module {

    private static final Logger LOG = Logger.getLogger(Module.class.getName());

    private final String version;
    private final String description;
    private final Object instance;
    private final Map<String, Method> methods;

    Module(final String version, final String description, final Object instance) {
        this.version = version;
        this.description = description;
        this.instance = instance;

        // 関数実行時に java.lang.Class.getMethod() しようとすると、引数の型を指定する必要があるので先にマップしておく。
        // よって、オーバーロードは捨てる
        this.methods = new HashMap<>();
        for (final Method method : instance.getClass().getMethods()) {
            boolean use = false;
            for (final Annotation annotation : method.getAnnotations()) {
                if (annotation instanceof ModuleMethod) {
                    use = true;
                    break;
                }
            }
            if (!use) {
                continue;
            }
            if (this.methods.containsKey(method.getName())) {
                LOG.warning("Method overload is not supported: " + method.getName());
            }
            this.methods.put(method.getName(), method);
        }
    }

    String getName() {
        return this.instance.getClass().getName();
    }

    String getVersion() {
        return this.version;
    }

    String getDescription() {
        return this.description;
    }

    List<Method> getMethods() {
        return new ArrayList<>(this.methods.values());
    }

    /**
     * モジュール関数の返り値の型を返す
     * @param methodName 関数名
     * @return 返り値の型。そんな関数無い場合は null
     */
    Class<?> getReturnType(final String methodName) {
        final Method method = this.methods.get(methodName);
        if (method == null) {
            return null;
        }
        return method.getReturnType();
    }

    Object invoke(final String methodName, final Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.methods.get(methodName).invoke(this.instance, args);
    }

}
