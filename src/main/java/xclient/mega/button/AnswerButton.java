package xclient.mega.button;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AnswerButton extends ImageButton {
    public List<Component> text;
    private int click = 0;

    public AnswerButton(int p_169011_, int p_169012_, int p_169013_, int p_169014_, int p_169015_, int p_169016_, int p_169017_, ResourceLocation p_169018_, Component component) {
        super(p_169011_, p_169012_, p_169013_, p_169014_, p_169015_, p_169016_, p_169017_, p_169018_, (s) -> {
        });
        text = Collections.singletonList(component);
    }

    public AnswerButton(int p_169011_, int p_169012_, int p_169013_, int p_169014_, int p_169015_, int p_169016_, int p_169017_, ResourceLocation p_169018_, List<Component> component) {
        super(p_169011_, p_169012_, p_169013_, p_169014_, p_169015_, p_169016_, p_169017_, p_169018_, (s) -> {
        });
        text = component;
    }

    @Override
    public void render(@NotNull PoseStack p_93657_, int p_93658_, int p_93659_, float p_93660_) {
        super.render(p_93657_, p_93658_, p_93659_, p_93660_);
        if (click > 0) {
            renderToolTip(p_93657_, p_93658_, p_93659_);
            isHovered = true;
        }

    }

    @Override
    public void onClick(double p_93634_, double p_93635_) {
        click = click > 0 ? 0 : click+1;
    }

    @Override
    public void renderToolTip(@NotNull PoseStack p_93653_, int p_93654_, int p_93655_) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) {
            Screen screen = mc.screen;
            screen.renderTooltip(new PoseStack(), text, Optional.empty(), x + width, y + height);
        }
    }
}
