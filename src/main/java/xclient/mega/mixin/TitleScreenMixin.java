package xclient.mega.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xclient.mega.utils.Textures;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        addRenderableWidget(new Button(this.width - 20, this.height - 20, 20, 20, new TextComponent("Q"), (b) -> {
            if (Textures.background < 3)
                Textures.background++;
            else if (Textures.background == 3)
                Textures.background = 1;
            System.out.println(Textures.background);
        }));
    }

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    public void render(PoseStack p_96739_, int p_96740_, int p_96741_, float p_96742_, CallbackInfo ci) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, new ResourceLocation("x_client:textures/background" + Textures.background + ".png"));
        minecraft = Minecraft.getInstance();
        blit(p_96739_, -p_96740_, (int) (-p_96741_ / 1.5F), 0, 0,  width * 2, height * 2, width * 2, height * 2);
        for(Widget widget : this.renderables) {
            widget.render(p_96739_, p_96740_, p_96741_, p_96742_);
        }

        ci.cancel();
    }
}
