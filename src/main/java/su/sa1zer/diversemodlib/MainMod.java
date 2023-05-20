package su.sa1zer.diversemodlib;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.sa1zer.diversemodlib.config.LangResource;

@Mod(MainMod.MOD_ID)
public class MainMod {

    public static final String MOD_ID = "diversemodlib";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static LangResource langResource;

    public MainMod() {
        langResource = new LangResource("ru_ru", this.getClass(), MOD_ID);
    }
}
