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
