import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static <T> Set<T> set(final T... elements) {
        return new TreeSet<>(Arrays.asList(elements));
    }

    public static Set<String> colour(final Set<String> passedTests, String colour) {
        return passedTests.stream().map(s -> colour(s, colour))
                .collect(Collectors.toSet());
    }

    public static String colour(final String s, String colour) {
        return colour + s + Colours.ANSI_RESET;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Cause> makeFailedMap(final Object... args) {
        final Map<String, Cause> map = new TreeMap<>();
        for (int i = 0; i < args.length; i += 3) {
            map.put((String) args[i],
                    new Cause((Class<? extends Throwable>)args[i + 1],
                            (Class<? extends Throwable>) args[i + 2]));
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> makeSkippedMap(final String... args) {
        final Map<String, String> map = new TreeMap<>();
        for (int i = 0; i < args.length; i += 2) {
            map.put(args[i], args[i + 1]);
        }
        return map;
    }

}
