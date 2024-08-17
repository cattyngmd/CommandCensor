package dev.cattyn.commandcensor;

import dev.cattyn.commandcensor.config.CommandCensorConfig;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

public final class CommandCensor implements ModInitializer {
    public static final String MOD_ID = "commandcensor";

    @Override
    public void onInitialize() {
        MidnightConfig.init(MOD_ID, CommandCensorConfig.class);
    }
}
