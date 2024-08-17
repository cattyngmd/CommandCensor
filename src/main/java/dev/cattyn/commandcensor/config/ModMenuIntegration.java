package dev.cattyn.commandcensor.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.cattyn.commandcensor.CommandCensor;
import eu.midnightdust.lib.config.MidnightConfig;

public final class ModMenuIntegration implements ModMenuApi {
    @Override public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, CommandCensor.MOD_ID);
    }
}
