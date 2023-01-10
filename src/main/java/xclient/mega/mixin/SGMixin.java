package xclient.mega.mixin;

import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.utils.Textures;

@Mixin(SpectatorGui.class)
public class SGMixin {
    @Shadow
    @Final
    @Mutable
    private static ResourceLocation WIDGETS_LOCATION;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void clinit(CallbackInfo ci) {
        WIDGETS_LOCATION = Textures.WIDGETS_LOCATION;
    }
}
