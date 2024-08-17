package dev.cattyn.commandcensor.mixin;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextFieldWidget.class)
public interface DuckTextFieldWidget {
    @Accessor("text")
    void commandCensor$setText(String text);

    @Accessor("selectionEnd")
    int commandCensor$getSelectionEnd();

    @Accessor("selectionStart")
    void commandCensor$setSelectionStart(int selectionStart);

    @Accessor("selectionEnd")
    void commandCensor$setSelectionEnd(int selectionEnd);
}
