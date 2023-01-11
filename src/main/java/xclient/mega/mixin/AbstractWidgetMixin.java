package xclient.mega.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.utils.ButtonUtils;
import xclient.mega.utils.Textures;

@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin {
    @Shadow
    @Final
    @Mutable
    public static ResourceLocation WIDGETS_LOCATION;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void clinit(CallbackInfo ci) {
        WIDGETS_LOCATION = Textures.WIDGETS_LOCATION;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(int p_93629_, int p_93630_, int p_93631_, int p_93632_, Component p_93633_, CallbackInfo ci) {
        ButtonUtils.loaded_buttons.add(this);
    }
}
