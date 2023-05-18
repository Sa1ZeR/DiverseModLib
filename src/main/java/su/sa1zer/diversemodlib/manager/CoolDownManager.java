package su.sa1zer.diversemodlib.manager;

import java.util.HashMap;
import java.util.Map;

public class CoolDownManager<T> {

    private final Map<T, Long> coolDownMap = new HashMap<>();

    public void addToCooldown(T object, long seconds) {
        coolDownMap.put(object, System.currentTimeMillis() + seconds * 1000);
    }

    public boolean isCoolDown(T object) {
        Long aLong = coolDownMap.get(object);
        if(aLong == null) return false;

        return System.currentTimeMillis() < aLong;
    }

    public long getTime(T object) {
        long time = coolDownMap.getOrDefault(object, 0L);
        time = time - System.currentTimeMillis();

        if(time <= 0) return 0;
        return time / 1000;
    }
}
