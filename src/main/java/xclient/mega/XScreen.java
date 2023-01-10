package xclient.mega;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import xclient.mega.mod.Module;
import xclient.mega.mod.ModuleManager;
import xclient.mega.utils.RainbowFont;

import java.util.List;

public class XScreen extends Screen {
    protected XScreen() {
        super(new TextComponent(""));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static boolean isInRange(Module<?> module, int x, int y) {
        if (x >= module.x && y >= module.y && x < module.x + module.width && y < module.y + module.height) {
            System.out.println(module.getName() + " is in range");
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        for (List<Module<?>> list : ModuleManager.all)
            for (Module<?> module : list) {
                if (isInRange(module, (int) x, (int) y)) {
                    if (p_94697_ == 0) {
                        module.left();
                    }
                    if (p_94697_ == 1) {
                        module.right();
                    }
                }
            }
        Main.setModules();
        return super.mouseClicked(x, y, p_94697_);
    }

    @Override
    public void render(PoseStack stack, int mx, int my, float pt) {
        int x=0,y=0;
        for (Module<?> module : ModuleManager.modules) {
            module.render(stack, x, y);
            y += 11;
        }
        y+=15;
        if (Main.killaura_displayInfo) {
            for (Module<?> module : ModuleManager.configuration_father_modules) {
                module.render(stack, x, y);
                y += 11;
            }
        }
        super.render(stack, mx, my, pt);
    }

    @Override
    public Component getTitle() {
        return new TextComponent("The X Screen").withStyle(ChatFormatting.BLUE);
    }

    @Override
    protected void init() {
        minecraft = Minecraft.getInstance();
        super.init();
    }
}
