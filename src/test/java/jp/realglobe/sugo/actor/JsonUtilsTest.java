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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * JsonUtils のテスト
 */
public class JsonUtilsTest {

    /**
     * JSONObject の変換テスト
     */
    @Test
    public void testConvertJSONObjectToObject() {
        final boolean b = true;
        final double n = 123.45;
        final String s = "abcde";
        final Object[] a = new Object[] { null, 1, "a", false };
        final Map<String, Object> o = new HashMap<>();
        o.put("b", "c");
        o.put("c", 2);
        o.put("e", true);
        // JSONObject が null を保存しないのでテストしない
        // o.put("f", null);

        final Map<String, Object> data = new HashMap<>();
        data.put("null", null);
        data.put("boolean", b);
        data.put("number", n);
        data.put("string", s);
        data.put("array", a);
        data.put("object", o);
        final JSONObject json = new JSONObject(data);

        final Map<String, Object> obj = JsonUtils.convertToObject(json);
        Assert.assertNull(obj.get("null"));
        Assert.assertEquals(b, obj.get("boolean"));
        Assert.assertEquals(n, obj.get("number"));
        Assert.assertEquals(s, obj.get("string"));
        Assert.assertArrayEquals(a, (Object[]) obj.get("array"));
        Assert.assertEquals(o, obj.get("object"));
    }

    /**
     * JSONArray の変換テスト
     */
    @Test
    public void testConvertJSONArrayToObject() {
        final boolean b = true;
        final double n = 123.45;
        final String s = "abcde";
        final Object[] a = new Object[] { null, 1, "a", false };
        final Map<String, Object> o = new HashMap<>();
        o.put("b", "c");
        o.put("c", 2);
        o.put("e", true);
        // JSONObject が null を保存しないのでテストしない
        // o.put("f", null);

        final Object[] data = new Object[] { null, b, n, s, a, o };
        final JSONArray json = new JSONArray(data);

        final Object[] obj = JsonUtils.convertToObject(json);
        Assert.assertArrayEquals(data, obj);
    }

}
