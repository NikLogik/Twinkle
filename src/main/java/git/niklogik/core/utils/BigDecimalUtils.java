package git.niklogik.core.utils;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class BigDecimalUtils {

    public static BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value).setScale(3, HALF_UP);
    }

    public static boolean lessThan(BigDecimal left, BigDecimal compareWith) {
        return left.compareTo(compareWith) < 0;
    }

    public static boolean greaterThan(BigDecimal left, BigDecimal compareWith) {
        return left.compareTo(compareWith) > 0;
    }

    public static BigDecimal divide(BigDecimal left, BigDecimal right) {
        return left.divide(right, 3, HALF_UP);
    }
}
