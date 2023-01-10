package xclient.mega.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.utils.HudUtils;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    public void render(PoseStack p_96739_, int p_96740_, int p_96741_, float p_96742_, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, new ResourceLocation("x_client:textures/background2.png"));
        blit(p_96739_, -p_96740_, 0, 0, 0,  this.width * 2, this.height, this.width * 2, this.height );
        for(Widget widget : this.renderables) {
            widget.render(p_96739_, p_96740_, p_96741_, p_96742_);
        }
        ci.cancel();
    }
}
