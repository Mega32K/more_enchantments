package xclient.mega.button;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import xclient.mega.utils.Textures;

public class SolidColorButton extends Button {
    protected float R;
    protected float G;
    protected float B;
    private ResourceLocation texture = Textures.WIDGETS_LOCATION;

    public SolidColorButton(int p_93721_, int p_93722_, int p_93723_, int p_93724_, Component p_93725_, OnPress p_93726_) {
        super(p_93721_, p_93722_, p_93723_, p_93724_, p_93725_, p_93726_);
        R = G = B = 1.0F;
    }

    public SolidColorButton(int p_93721_, int p_93722_, int p_93723_, int p_93724_, Component p_93725_, OnPress p_93726_, float alpha) {
        this(p_93721_, p_93722_, p_93723_, p_93724_, p_93725_, p_93726_);
        this.alpha = alpha;
    }

    public SolidColorButton(int p_93721_, int p_93722_, int p_93723_, int p_93724_, Component p_93725_, OnPress p_93726_, float r, float g, float b, float alpha) {
        this(p_93721_, p_93722_, p_93723_, p_93724_, p_93725_, p_93726_, alpha);
        setColor(r, g, b);
    }

    public static SolidColorButton getBlack(int x, int y, int width, int height, Component component, OnPress onPress) {
        return new SolidColorButton(x, y, width, height, component, onPress, 0 ,0 ,0, 1);
    }

    public void setTexture(ResourceLocation location) {
        texture = location;
    }

    public void resetTexture() {
        texture = Textures.WIDGETS_LOCATION;
    }

    public void setColor(float r, float g, float b) {
        R = r;
        G = g;
        B = b;
    }

    public void renderButton(@NotNull PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(R / 255, G / 255, B / 255, this.alpha);
        int i = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(p_93676_, this.x, this.y, 0, i * 20, this.width / 2, this.height);
        this.blit(p_93676_, this.x + this.width / 2, this.y, 200 - this.width / 2, i * 20, this.width / 2, this.height);
        this.renderBg(p_93676_, minecraft, p_93677_, p_93678_);
        int j = getFGColor();
        drawCenteredString(p_93676_, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
        if (this.isHoveredOrFocused()) {
            this.renderToolTip(p_93676_, p_93677_, p_93678_);
        }
    }
}
