package jp.realglobe.sugo.actor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仕様の通知周り
 */
final class Specification {

    private static final String KEY_NAME = "name";
    private static final String KEY_VERSION = "version";
    private static final String KEY_DESC = "desc";
    private static final String KEY_METHODS = "methods";
    private static final String KEY_TYPE = "type";
    private static final String KEY_RETURN = "return";
    private static final String KEY_PARAMS = "params";

    private static final String UNDEFINED_VERSION = "unknown";

    private Specification() {}

    /**
     * モジュールの仕様データをつくる
     * @param module モジュール
     * @return モジュールの仕様データ
     */
    static Map<String, Object> generateSpecification(final Module module) {
        final Map<String, Object> specification = new HashMap<>();

        specification.put(KEY_NAME, module.getName());

        if (module.getVersion() != null) {
            specification.put(KEY_VERSION, module.getVersion());
        } else {
            specification.put(KEY_VERSION, UNDEFINED_VERSION);
        }

        if (module.getDescription() != null) {
            specification.put(KEY_DESC, module.getDescription());
        }

        final Map<String, Object> methods = new HashMap<>();
        for (final Method method : module.getMethods()) {
            methods.put(method.getName(), Specification.generateMethodSpecification(method));
        }
        specification.put(KEY_METHODS, methods);

        return specification;
    }

    /**
     * 関数の仕様データをつくる
     * @param method 関数
     * @return 関数の仕様データ
     */
    private static Map<String, Object> generateMethodSpecification(final Method method) {
        final Map<String, Object> specification = new HashMap<>();

        final List<Map<String, Object>> parameters = new ArrayList<>();
        for (final Class<?> type : method.getParameterTypes()) {
            final Map<String, Object> parameter = new HashMap<>();
            parameter.put(KEY_TYPE, type.getName());
            parameters.add(parameter);
        }
        specification.put(KEY_PARAMS, parameters);

        final Class<?> returnType = method.getReturnType();
        if (returnType != Void.TYPE) {
            final Map<String, Object> returnParameter = new HashMap<>();
            returnParameter.put(KEY_TYPE, returnType.getName());
            specification.put(KEY_RETURN, returnParameter);
        }

        return specification;
    }

}
