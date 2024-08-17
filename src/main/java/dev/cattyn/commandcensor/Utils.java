package dev.cattyn.commandcensor;

import dev.cattyn.commandcensor.config.CommandCensorConfig;
import dev.cattyn.commandcensor.mixin.DuckTextFieldWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.Locale;

public final class Utils {
    private Utils() {
        throw new AssertionError();
    }

    public static boolean isCommand(String input) {
        String s = input.toLowerCase(Locale.ROOT);
        for (String command : CommandCensorConfig.commands) {
            if (s.startsWith("/" + command.toLowerCase(Locale.ROOT) + " ")) {
                return true;
            }
        }
        return false;
    }

    public static void setText(TextFieldWidget widget, String input) {
        ((DuckTextFieldWidget) widget).commandCensor$setText(input);
    }

    public static int getCursor(TextFieldWidget widget) {
        return ((DuckTextFieldWidget) widget).commandCensor$getSelectionEnd();
    }

    public static void setCursor(TextFieldWidget widget, int cursor) {
        ((DuckTextFieldWidget) widget).commandCensor$setSelectionEnd(cursor);
        ((DuckTextFieldWidget) widget).commandCensor$setSelectionStart(cursor);
    }
}
