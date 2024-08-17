package dev.cattyn.commandcensor.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.cattyn.commandcensor.CommandCensor;
import dev.cattyn.commandcensor.Utils;
import dev.cattyn.commandcensor.config.CommandCensorConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class MixinChatScreen {
    @Shadow
    protected TextFieldWidget chatField;

    @Unique
    private String commandCensor$prevInput;
    @Unique
    private int commandCensor$prevCursor = -1;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.BEFORE))
    private void renderHookPre(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!CommandCensorConfig.enabled) return;

        String f = chatField.getText();
        if (!Utils.isCommand(f)) {
            return;
        }

        int i = f.indexOf(" ") + 1;
        if (i > f.length()) {
            return;
        }

        commandCensor$prevInput = f;
        int length = f.length() - i;

        if (CommandCensorConfig.hideSuggestion) {
            chatField.setSuggestion("");
        }

        if (CommandCensorConfig.hideLength) {
            length = 3;
            commandCensor$prevCursor = Utils.getCursor(chatField);
            Utils.setCursor(chatField, i + 3);
        }

        f = f.substring(0, i) + "*".repeat(length);
        Utils.setText(chatField, f);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.AFTER))
    private void renderHookPost(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!CommandCensorConfig.enabled
                || commandCensor$prevInput == null) return;

        if (CommandCensorConfig.hideLength && commandCensor$prevCursor != -1) {
            Utils.setCursor(chatField, commandCensor$prevCursor);
            commandCensor$prevCursor = -1;
        }
        Utils.setText(chatField, commandCensor$prevInput);
        commandCensor$prevInput = null;
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatInputSuggestor;render(Lnet/minecraft/client/gui/DrawContext;II)V"))
    private boolean render(ChatInputSuggestor instance, DrawContext context, int mouseX, int mouseY) {
        if (!CommandCensorConfig.enabled || !CommandCensorConfig.hideSuggestion) return true;
        String f = chatField.getText();
        return !Utils.isCommand(f);
    }

}
