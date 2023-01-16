package xclient.mega.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xclient.mega.Main;
import xclient.mega.utils.Render2DUtil;

import java.awt.*;

@Mixin(Button.class)
public abstract class ButtonMixin extends AbstractWidget {
    public ButtonMixin(int p_93629_, int p_93630_, int p_93631_, int p_93632_, Component p_93633_) {
        super(p_93629_, p_93630_, p_93631_, p_93632_, p_93633_);
    }

    /**
     * @author mega
     * @reason pretty
     */
    @Overwrite
    public void renderButton(PoseStack stack, int x, int y, float pt) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        int base = new Color(30, 30, 30, 150).getRGB();
        int pressing = new Color(0, 0, 0, Main.base_timehelper.integer_time).getRGB();
        Render2DUtil.drawRect(stack, this.x, this.y, this.width, this.height, Render2DUtil.isHovered(x, y, this.x, this.y, this.width, this.height) ? pressing : base);
        this.renderBg(stack, minecraft, x, y);
        int j = getFGColor();
        drawCenteredString(stack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
        if (this.isHoveredOrFocused()) {
            this.renderToolTip(stack, x, y);
        }
    }
}
