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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON 周りの便利関数
 */
final class JsonUtils {

    private JsonUtils() {}

    /**
     * JSONObject を一般的なオブジェクトに変換する
     * @param jsonObject 変換前
     * @return 変換後
     */
    static Map<String, Object> convertToObject(final JSONObject jsonObject) {
        final Map<String, Object> map = new HashMap<>();
        try {
            for (final String key : jsonObject.keySet()) {
                final Object obj = jsonObject.get(key);
                if (obj == JSONObject.NULL) {
                    map.put(key, null);
                } else if (obj instanceof JSONObject) {
                    map.put(key, convertToObject((JSONObject) obj));
                } else if (obj instanceof JSONArray) {
                    map.put(key, convertToObject((JSONArray) obj));
                } else {
                    map.put(key, obj);
                }
            }
        } catch (final JSONException e) {
            // 無い key の指定。ここには来ないはず
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * JSONArray を一般的な配列に変換する
     * @param jsonArray 変換前
     * @return 変換後
     */
    static Object[] convertToObject(final JSONArray jsonArray) {
        final Object[] array = new Object[jsonArray.length()];
        try {
            for (int i = 0; i < array.length; i++) {
                final Object obj = jsonArray.get(i);
                if (obj == JSONObject.NULL) {
                    array[i] = null;
                } else if (obj instanceof JSONObject) {
                    array[i] = convertToObject((JSONObject) obj);
                } else if (obj instanceof JSONArray) {
                    array[i] = convertToObject((JSONArray) obj);
                } else {
                    array[i] = obj;
                }
            }
        } catch (final JSONException e) {
            // 範囲外指定。ここには来ないはず
            throw new RuntimeException(e);
        }
        return array;
    }

}
