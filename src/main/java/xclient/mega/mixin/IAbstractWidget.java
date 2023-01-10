package xclient.mega.mixin;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractWidget.class)
public interface IAbstractWidget {
    @Accessor("width")
    void setWidth(int width);

    @Accessor("height")
    void setHeight(int height);

    @Accessor("message")
    void setMessage(Component message);

    @Invoker("setFocused")
    void setFocus(boolean focus);
}
