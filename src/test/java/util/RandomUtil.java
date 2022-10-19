package util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {
    public static String getRandomEmail(final int length) {
        return RandomStringUtils.random(length, true, false)
                + "@"
                + RandomStringUtils.random(5, true, false)
                + "."
                + RandomStringUtils.random(2, true, false) ;
    }

    public static String getRandomString(final int minLength, final int maxLength) {
        return getRandomString(getRandomIndex(minLength, maxLength));
    }

    public static String getRandomStringOrNull(final int length) {
        return ThreadLocalRandom.current().nextBoolean() ? getRandomString(length) : null;
    }

    public static String getRandomString(final int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public static int getRandomIndex(final int min, final int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static <T extends Enum<T>> T getRandomEnum(final Class<T> enumClass) {
        final EnumSet<T> allEnums = EnumSet.allOf(enumClass);
        return allEnums.stream().toList().get(getRandomIndex(0,
                allEnums.size() - 1));
    }

    public static ZonedDateTime getRandomDateTime() {
        var now = ZonedDateTime.now();
        final int minutes = getRandomIndex(0, 525600); //525600 - 1 year in minutes
        return ThreadLocalRandom.current().nextBoolean() ? now.
                minusMinutes(minutes) : now.plusMinutes(minutes);
    }

    public static boolean getRandomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
