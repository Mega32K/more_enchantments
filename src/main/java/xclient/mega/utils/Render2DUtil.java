package xclient.mega.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;

public class Render2DUtil {
    public static void drawRect(PoseStack stack, int x, int y, int width, int height, int color) {
        Gui.fill(stack, x, y, x + width, y + height, color);
    }

    public static void drawRectWithString(PoseStack stack, int x, int y, int width, int height, int color, String s, Font font) {
        Gui.fill(stack, x, y, x + width, y + height, color);
        Gui.drawString(stack, font, s, x, y + height / 3, 0xFFFFFFFF);

    }

    public static boolean isHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static void drawImage(PoseStack stack, ResourceLocation resourceLocation, int x, int y, int width, int height) {
        RenderSystem.setShaderTexture(0, resourceLocation);
        Gui.blit(stack, x, y, 0, 0, width, height, width, height);
    }

    public static void drawImage(PoseStack stack, String name, int x, int y, int width, int height) {
        RenderSystem.setShaderTexture(0, new ResourceLocation("xclient:textures" + name));
        Gui.blit(stack, x, y, 0, 0, width, height, width, height);
    }
}
