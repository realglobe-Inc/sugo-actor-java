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

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * モジュールのテスト
 */
public class ModuleTest {

    private static class TestClass {

        @SuppressWarnings("unused")
        public void notModuleMethod() {}

        @ModuleMethod
        public void noReturn() {}

        @ModuleMethod
        public String echo(final String s) {
            return s;
        }

    }

    private static final String VERSION = "1.2.3";
    private static final String DESCRIPTION = "test module";

    private Module module;

    /**
     * 準備
     */
    @Before
    public void before() {
        this.module = new Module(VERSION, DESCRIPTION, new TestClass());
    }

    /**
     * 名前を返せるかどうか
     */
    @Test
    public void testGetName() {
        Assert.assertFalse(this.module.getName().isEmpty());
    }

    /**
     * バージョンを返せるかどうか
     */
    @Test
    public void testGetVersion() {
        Assert.assertEquals(VERSION, this.module.getVersion());
    }

    /**
     * 説明を返せるかどうか
     */
    @Test
    public void testGetDescription() {
        Assert.assertEquals(DESCRIPTION, this.module.getDescription());
    }

    /**
     * 関数を返せるかどうか
     */
    @Test
    public void testGetMethod() {
        final Set<String> methodNames = new HashSet<>();
        for (final Method method : this.module.getMethods()) {
            methodNames.add(method.getName());
        }
        Assert.assertFalse(methodNames.contains("notModuleMethod"));
        Assert.assertTrue(methodNames.contains("noReturn"));
    }

    /**
     * 無い関数の返り値が null か
     */
    @Test
    public void testGetNullReturnType() {
        Assert.assertNull(this.module.getReturnType("notModuleMethod"));
    }

    /**
     * 関数の返り値を返せるか
     */
    @Test
    public void testGetReturnType() {
        Assert.assertEquals(Void.TYPE, this.module.getReturnType("noReturn"));
    }

    /**
     * 関数を呼び出せるか
     * @throws Exception エラー
     */
    @Test
    public void testInvoke() throws Exception {
        this.module.invoke("noReturn", null);
    }

    /**
     * 返り値を返せるか
     * @throws Exception エラー
     */
    @Test
    public void testReturnValue() throws Exception {
        Assert.assertEquals("abcde", this.module.invoke("echo", new Object[] { "abcde" }));
    }

}
