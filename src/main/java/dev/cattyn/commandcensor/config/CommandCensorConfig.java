package dev.cattyn.commandcensor.config;

import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

public final class CommandCensorConfig extends MidnightConfig {
    @Entry
    public static boolean enabled = true;

    @Entry
    public static boolean hideSuggestion = true;

    @Entry
    public static boolean hideLength = false;

    @Entry
    public static List<String> commands = Lists.newArrayList("login", "l");

}