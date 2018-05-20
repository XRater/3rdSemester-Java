import java.util.*;

class Utils {

    @SafeVarargs
    static <T> Set<T> set(final T... elements) {
        return new TreeSet<>(Arrays.asList(elements));
    }

    @SuppressWarnings("unchecked")
    static Map<String, Cause> makeFailedMap(final Object... args) {
        final Map<String, Cause> map = new TreeMap<>();
        for (int i = 0; i < args.length; i += 3) {
            map.put((String) args[i],
                    new Cause((Class<? extends Throwable>)args[i + 1],
                            (Class<? extends Throwable>) args[i + 2]));
        }
        return map;
    }

    static Map<String, String> makeSkippedMap(final String... args) {
        final Map<String, String> map = new TreeMap<>();
        for (int i = 0; i < args.length; i += 2) {
            map.put(args[i], args[i + 1]);
        }
        return map;
    }

}
