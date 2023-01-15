package xclient.mega.mod.bigmodule;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xclient.mega.XScreen;
import xclient.mega.event.RenderEvent;
import xclient.mega.utils.RainbowFont;
import xclient.mega.utils.Render2DUtil;
import xclient.mega.Main;
import xclient.mega.utils.Vec2d;

import java.awt.*;

@Mod.EventBusSubscriber
public class KeyDisplayBM extends BigModuleBase{
    @Override
    public void render(PoseStack stack, int x, int y) {
        if (isPressing) {
            Main._x_ = XScreen.mouseX;
            Main._y_ = XScreen.mouseY;
        }
        if (Main.key_display) {
            Minecraft mc = Minecraft.getInstance();
            int background = new Color(255, 255, 255, 50).getRGB();
            int color = new Color(0, 0, 0, Main.base_timehelper.integer_time).getRGB();
            int color2 = new Color(Main.timeHelper.integer_time / 2, Main.base_timehelper.integer_time / 3, 130, Main.base_timehelper.integer_time).getRGB();
            Options options = mc.options;
            Render2DUtil.drawRectWithString(stack,  x+ 1 + 20,  y, 20, 20, options.keyUp.isDown() ? color : background, " W", RainbowFont.INS);
            Render2DUtil.drawRectWithString(stack,  x+ 20 + 1,  y + 20 + 1, 20, 20, options.keyDown.isDown() ? color : background, " S", RainbowFont.INS);
            Render2DUtil.drawRectWithString(stack,  x,  y + 20 + 1, 20, 20, options.keyLeft.isDown() ? color : background, " A", RainbowFont.INS);
            Render2DUtil.drawRectWithString(stack,  x+ 40 + 2,  y + 20 + 1, 20, 20, options.keyRight.isDown() ? color : background, " D", RainbowFont.INS);
            Render2DUtil.drawRectWithString(stack,  x,  y + 40 + 2 + 2, 61, 15, options.keyJump.isDown() ? color : background, "  [ _ ] ", RainbowFont.INS);
            Render2DUtil.drawRectWithString(stack,  x,  y + 40 + 2 + 2 + 1 + 15, 30, 15, mc.options.keySprint.isDown() ? color : background, "Ctrl", RainbowFont.INS);
            Render2DUtil.drawRectWithString(stack,  x + 30 + 1,  y + 40 + 2 + 2 + 1 + 15, 33, 15, options.keyShift.isDown() ? color : background, "Shift", RainbowFont.INS);
            Render2DUtil.drawRectWithString(stack,  x+ 1 + 62,  y + 30, 20, 35, color2, "Fps", RainbowFont.INS);
            Gui.drawCenteredString(stack, RainbowFont.INS, " " +Minecraft.fps, x + 4 + 62, y + 35 + 30 / 3 + 3, 0);
        }
    }

    public boolean isInRange(int mx, int my, Vec2d start, Vec2d end) {
        int sx = (int) start.x;
        int sy = (int) start.y;
        int ex = (int) end.x;
        int ey = (int) end.y;
        return mx >= sx && my >= sy && mx < ex && my < ey;

    }

    @SubscribeEvent
    public static void renderHud(RenderEvent event) {
        PoseStack stack = new PoseStack();
        stack.scale(Main.key_scale, Main.key_scale, Main.key_scale);
        Main.KEY_DISPLAY_BM.render(stack, Main._x_, Main._y_);
    }
}
