package jp.realglobe.sugo.actor;

import java.util.HashMap;
import java.util.Map;

/**
 * Actor のテスト
 */
public class ActorTest {

    private static class TestClass {

        @SuppressWarnings("unused")
        public void notModuleMethod() {}

        @ModuleMethod
        public void noReturn() {}

        @ModuleMethod
        public boolean echoBool(final boolean b) {
            return b;
        }

        @ModuleMethod
        public double echoNumber(final double n) {
            return n;
        }

        @ModuleMethod
        public String echoString(final String s) {
            return s;
        }

        @ModuleMethod
        public Object[] echoArray(final Object[] a) {
            return a;
        }

        @ModuleMethod
        public Map<String, Object> echoObject(final Map<String, Object> o) {
            return o;
        }

        @ModuleMethod
        public String echoWithDelay(final String s, final double delay) {
            try {
                Thread.sleep((long) (delay * 1_000));
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
            return s;
        }

    }

    private static String getenv(final String key, final String defaultValue) {
        final String value = System.getenv(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * テスト実行
     * @param args 実行引数
     * @throws InterruptedException 終わり
     */
    public static void main(final String[] args) throws InterruptedException {
        final int port = Integer.parseInt(getenv("PORT", "8080"));
        final String hub = "http://localhost:" + port + "/";

        final String key = "actor0";
        final String name = "actor";
        final String description = "test actor";
        final Actor actor = new Actor(key, name, description);
        try {
            final String moduleName = "module";
            final String moduleVersion = "2.0.0";
            final String moduleDescription = "test module";
            final Object module = new TestClass();

            final Emitter emitter = actor.addModule(moduleName, moduleVersion, moduleDescription, module);
            actor.connect(hub);

            while (true) {
                Thread.sleep(1_000L);

                emitter.emit("bool", true);
                emitter.emit("number", 123.45);
                emitter.emit("string", "abcde");
                emitter.emit("array", new Object[] { null, false, 0, "" });
                final Map<String, Object> object = new HashMap<>();
                object.put("b", false);
                object.put("c", 0);
                object.put("d", "");
                emitter.emit("object", object);
            }
        } finally {
            actor.disconnect();
        }
    }

}
