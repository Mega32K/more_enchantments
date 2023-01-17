package xclient.mega.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.Main;
import xclient.mega.MegaUtil;
import xclient.mega.event.GameUpdateEvent;

import java.io.IOException;
import java.io.InputStream;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public abstract Window getWindow();

    @Shadow public Window window;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        getWindow().setTitle("");
        InputStream inputstream = Main.class.getResourceAsStream("/assets/x_client/textures/icon.png");
        if (inputstream != null) {
            this.window.setIcon(inputstream, inputstream);
        }
    }


    @Inject(method = "run", at = @At("HEAD"))
    private void run(CallbackInfo ci) {
        getWindow().setTitle("");
        MinecraftForge.EVENT_BUS.post(new GameUpdateEvent());
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void close(CallbackInfo ci) {
        MegaUtil.writeXCLIENT();
    }
}
