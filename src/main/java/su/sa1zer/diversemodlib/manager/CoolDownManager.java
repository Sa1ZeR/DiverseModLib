package su.sa1zer.diversemodlib.manager;

import java.util.HashMap;
import java.util.Map;

public class CoolDownManager<T> {

    private final Map<T, Long> coolDownMap = new HashMap<>();

    public void addToCooldown(T object, long time) {
        coolDownMap.put(object, System.currentTimeMillis() + time);
    }

    public boolean isCoolDown(T object) {
        Long aLong = coolDownMap.get(object);
        if(aLong == null) return false;

        return System.currentTimeMillis() < aLong;
    }

    public long getTime(T object) {
        return coolDownMap.getOrDefault(object, 0L);
    }
}
