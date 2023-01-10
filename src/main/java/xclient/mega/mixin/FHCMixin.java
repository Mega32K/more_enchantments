package xclient.mega.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.protocol.game.*;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.Main;

@Mixin(value = ForgeHooksClient.class, remap = false)
public class FHCMixin {
    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    private static void drawScreen(Screen screen, PoseStack poseStack, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (screen instanceof DeathScreen && Main.respawn) {
            if (Minecraft.getInstance().player != null)
                Minecraft.getInstance().player.connection.send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
            Minecraft.getInstance().screen = null;
            screen = null;
            Minecraft.getInstance().getSoundManager().resume();
            Minecraft.getInstance().mouseHandler.grabMouse();
            if (Minecraft.getInstance().player != null)
                Minecraft.getInstance().player.connection.send(new ServerboundChatPacket("/back"));
            ci.cancel();
        }
    }
}
