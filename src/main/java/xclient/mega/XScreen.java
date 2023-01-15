package xclient.mega;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import xclient.mega.mod.Module;
import xclient.mega.mod.ModuleManager;


public class XScreen extends Screen implements IScreenClick{

    protected XScreen() {
        super(new TextComponent(""));
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static boolean isInRange(Module<?> module, int x, int y) {
        return x >= module.x && y >= module.y && x < module.x + module.width && y < module.y + module.height;
    }

    public void click(double x, double y, int code) {
        for (Module<?> module : Module.every) {
            if (XScreen.isInRange(module, (int) x, (int) y)) {
                if (code == 0) {
                    module.left();
                }
                if (code == 1) {
                    module.right();
                }
            }
        }
        Main.setModules();
    }

    @Override
    public boolean mouseClicked(double x, double y, int p_94697_) {
        click(x, y, p_94697_);
        return super.mouseClicked(x, y, p_94697_);
    }

    @Override
    public void render(PoseStack stack, int mx, int my, float pt) {
        int x=0,y=0;
        for (Module<?> module : ModuleManager.modules) {
            if (module.getFather() == null)
                module.render(stack, x, y, isInRange(module, mx, my));
            y += 11;
            if (y >= 110) {
                x += 130;
                y = 0;
            }
        }
        y+=15;
        if (Main.killaura_displayInfo) {
            for (Module<?> module : ModuleManager.modules) {
                if (module.getFather() == Main.SUPER_KILL_AURA) {
                    module.render(stack, x, y, isInRange(module, mx, my));
                    y += 11;
                }
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
        Main.setModules();
        super.init();
    }
}
