package jp.realglobe.sugo.actor;

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
        final Actor actor = new Actor(hub, key, name, description);

        final String moduleName = "module";
        final String moduleVersion = "2.0.0";
        final String moduleDescription = "test module";
        final Object module = new TestClass();

        actor.addModule(moduleName, moduleVersion, moduleDescription, module);
        actor.connect();

        Thread.sleep(Long.MAX_VALUE);
    }

}
