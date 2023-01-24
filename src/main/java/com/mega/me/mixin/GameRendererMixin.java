package com.mega.me.mixin;

import com.mega.me.common.enc.base.EncBase;
import com.mega.me.common.enc.base.IRenderEnc;
import com.mega.me.common.event.RenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(float p_109094_, long p_109095_, boolean p_109096_, CallbackInfo ci) {
        EncBase.OBJs.forEach(encBase -> {
            if (encBase instanceof IRenderEnc iRenderEnc)
                iRenderEnc.renderTick(true);
        });
        if (MinecraftForge.EVENT_BUS.post(new RenderEvent.Pre(p_109094_, p_109095_)))
            ci.cancel();
    }

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    public void render_return(float p_109094_, long p_109095_, boolean p_109096_, CallbackInfo ci) {
        EncBase.OBJs.forEach(encBase -> {
            if (encBase instanceof IRenderEnc iRenderEnc)
                iRenderEnc.renderTick(false);
        });
        if (MinecraftForge.EVENT_BUS.post(new RenderEvent.Post(p_109094_, p_109095_)))
            ci.cancel();
    }
}
