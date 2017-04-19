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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Specification のテスト
 */
public class SpecificationTest {

    private static class TestClass {

        @ModuleMethod
        public String echo(final String s) {
            return s;
        }

    }

    /**
     * 仕様データをつくれるか
     */
    @Test
    public void testGenerateSpecification() {
        final String version = "1.2.3";
        final String description = "test module";
        final Map<String, Object> specification = Specification.generateSpecification(new Module(version, description, new TestClass()));
        Assert.assertEquals(version, specification.get("version"));
        Assert.assertEquals(description, specification.get("desc"));
        Assert.assertEquals(TestClass.class.getName(), specification.get("name"));
        @SuppressWarnings("unchecked")
        final Map<String, Object> methods = (Map<String, Object>) specification.get("methods");
        @SuppressWarnings("unchecked")
        final Map<String, Object> method = (Map<String, Object>) methods.get("echo");
        Assert.assertTrue(method.containsKey("params"));
        Assert.assertTrue(method.containsKey("return"));
    }

}
