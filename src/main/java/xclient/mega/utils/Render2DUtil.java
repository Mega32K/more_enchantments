package xclient.mega.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;

public class Render2DUtil {
    public static void drawRect(PoseStack stack, int x, int y, int width, int height, int color) {
        Gui.fill(stack, x, y, x + width, y + height, color);
    }

    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
