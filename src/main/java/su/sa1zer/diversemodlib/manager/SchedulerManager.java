package su.sa1zer.diversemodlib.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import su.sa1zer.diversemodlib.MainMod;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SchedulerManager {

    private static final SchedulerManager INSTANCE = new SchedulerManager();

    private SchedulerManager() {

    }

    public static SchedulerManager getINSTANCE() {
        return INSTANCE;
    }

    public void execute(Runnable runnable) {
        schedule(runnable, 0L);
    }

    public void schedule(Runnable runnable, long tickDelay) {
        TickExecutor.queue.add(new SimpleTask(runnable, tickDelay + 1));
    }

    public void schedule(Runnable runnable, long tickDelay, long periodDelay) {

    }

    @Mod.EventBusSubscriber(modid = MainMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class TickExecutor {

        private static final List<SimpleTask> queue = Collections.synchronizedList(new LinkedList<>());
        private static final List<SimpleTask> scheduledNow = new LinkedList<>();

        @SubscribeEvent
        public static void serverTick(TickEvent.ServerTickEvent event) {
            if(event.phase != TickEvent.Phase.START) return;

            synchronized(queue){
                int idx = queue.size();
                while(--idx >= 0){
                    SimpleTask task = queue.get(idx);
                    if(--task.ticks == 0){
                        queue.remove(idx);
                        scheduledNow.add(task);
                    }
                }
            }

            int idx = scheduledNow.size();
            while(--idx >= 0){
                scheduledNow.remove(idx).runnable.run();
            }
        }
    }

    @AllArgsConstructor
    @Getter
    private static class SimpleTask {
        public final Runnable runnable;
        public long ticks;
    }
}
