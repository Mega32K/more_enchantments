package xclient.mega.utils;


import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;

public class RendererUtils {
    public static int WHITE = 0xFFFFFFFF;
    Minecraft mc = Minecraft.getInstance();

    public static double getRandom(double min, double max) {
        return Math.random() * (max - min) - (max - min) / 2;
    }

    public static double getRandom(double length) {
        return Math.random() * length - length / 2;
    }

    public void drawItemOnScreen(int x, int y, ItemStack itemStack) {
        drawItemOnScreen(x, y, itemStack, new Vector4f(1.0F, 1.0F, 1.0F, 1.0F), 1);
    }

    public void drawItemOnScreen(int x, int y, ItemStack itemStack, Vector4f color) {
        drawItemOnScreen(x, y, itemStack, color, 1);
    }

    public void drawItemOnScreen(int x, int y, ItemStack itemStack, Vector4f color, int scale) {
        drawItemOnScreen(mc.getItemRenderer(), x, y, itemStack, color, scale);
    }

    public void drawItemOnScreen(ItemRenderer renderer, int x, int y, ItemStack itemStack, Vector4f color, int scale) {
        renderGuiItem(itemStack, x, y, renderer, scale, color);
    }

    public void renderGuiItem(ItemStack p_115124_, int p_115125_, int p_115126_, ItemRenderer renderer, int scale, Vector4f color) {
        this.renderGuiItem(p_115124_, p_115125_, p_115126_, renderer.getModel(p_115124_, null, null, 0), renderer, scale, color);
    }

    public void renderGuiItem(ItemStack p_115128_, int p_115129_, int p_115130_, BakedModel p_115131_, ItemRenderer renderer, int scale, Vector4f color) {
        mc.textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(color.x(), color.y(), color.z(), color.w());
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double) p_115129_ * scale, (double) p_115130_ * scale, 100.0F * scale);
        posestack.translate(8.0D * scale, 8.0D * scale, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F * scale, 16.0F * scale, 16.0F * scale);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !p_115131_.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        renderer.render(p_115128_, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, p_115131_);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

}
