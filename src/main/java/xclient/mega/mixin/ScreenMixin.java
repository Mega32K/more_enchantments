package xclient.mega.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xclient.mega.Main;
import xclient.mega.utils.Render2DUtil;

import java.awt.*;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow
    public int width;

    @Shadow
    public int height;

    /**
     * @author mega
     * @reason backgrounf
     */
    @Overwrite
    public void renderDirtBackground(int p_96627_) {
        Render2DUtil.drawRect(new PoseStack(), -1, -1, width + 1, height + 1, new Color(30, 30, 30, Main.base_timehelper.integer_time).getRGB());
    }
}
