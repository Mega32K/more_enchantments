package xclient.mega.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.Main;
import xclient.mega.MegaUtil;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow public abstract Window getWindow();

    @Inject(method = "run", at = @At("RETURN"))
    private void run(CallbackInfo ci) {
        getWindow().setTitle("X-Client | " + Main.version);
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void close(CallbackInfo ci) {
        MegaUtil.writeXCLIENT();
    }
}
