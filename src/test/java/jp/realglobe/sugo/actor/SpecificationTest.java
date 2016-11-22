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
