package xclient.mega.button;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import xclient.mega.Main;
import xclient.mega.utils.Render2DUtil;

import java.awt.*;

public class ModuleButton extends Button {
    public ModuleButton(int p_93721_, int p_93722_, int p_93723_, int p_93724_, Component p_93725_, OnPress p_93726_) {
        super(p_93721_, p_93722_, p_93723_, p_93724_, p_93725_, p_93726_);
    }

    public ModuleButton(int p_93728_, int p_93729_, int p_93730_, int p_93731_, Component p_93732_, OnPress p_93733_, OnTooltip p_93734_) {
        super(p_93728_, p_93729_, p_93730_, p_93731_, p_93732_, p_93733_, p_93734_);
    }

    @Override
    public void render(PoseStack stack, int x, int y, float pt) {
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
