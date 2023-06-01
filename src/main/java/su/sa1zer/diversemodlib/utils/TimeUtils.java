package su.sa1zer.diversemodlib.utils;

import joptsimple.internal.Strings;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TimeUtils {

    public static String getDurationString(long duration, String[] arr) {
        long diffSeconds = duration % 60;
        long diffMinutes = duration / 60 % 60;
        long diffHours = duration / (60 * 60) % 24;
        long diffMonths = duration / (60 * 60 * 24) % 30;
        long diffYears = duration / (60 * 60 * 24 * 30) % 12;

        List<String> message = new ArrayList<>();

        if(diffYears > 0) {
            message.add(String.valueOf(diffYears));
            message.add(arr[4]);
        }
        if(diffMonths > 0){
            message.add(String.valueOf(diffMonths));
            message.add(arr[3]);
        }
        if(diffHours > 0) {
            message.add(String.valueOf(diffHours));
            message.add(arr[2]);
        }
        if(diffMinutes > 0) {
            message.add(String.valueOf(diffMinutes));
            message.add(arr[1]);
        }
        if(diffSeconds > 0){
            message.add(String.valueOf(diffSeconds));
            message.add(arr[0]);
        }

        return String.join(" ", message);
    }

    public static String declOfNum(long n, String[] arr) {
        n = n % 100;

        long n1 = n % 10;
        if (n > 10 && n < 20) return arr[2];
        if (n1 > 1 && n1 < 5) return arr[1];
        if (n1 == 1) return arr[0];

        return n + " " +arr[2];
    }
}
