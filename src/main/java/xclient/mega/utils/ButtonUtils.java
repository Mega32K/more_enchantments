package xclient.mega.utils;

import xclient.mega.mixin.IAbstractWidget;
import xclient.mega.mixin.IButton;
import xclient.mega.mixin.IImageButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ButtonUtils {
    public static List<Object> loaded_buttons = new ArrayList<>();
    public AbstractWidget button;

    public ButtonUtils(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "ButtonUtils:" +
                "button=" + button;
    }

    //if this button is instance ImageButton
    public void setButtonImage(ResourceLocation location) {
        if (button instanceof ImageButton imageButton) {
            IImageButton iImageButton = (IImageButton) imageButton;
            iImageButton.setLocation(location);
        } else System.out.println(this + "isn't instance ImageButton");
    }

    public void setButtonMessage(Component message) {
        IAbstractWidget iButton = (IAbstractWidget) button;
        iButton.setMessage(message);
    }

    public void setButtonOnPress(Button.OnPress press) {
        if (button instanceof Button button_) {
            IButton iButton = (IButton) button_;
            iButton.setOnPress(press);
        } else System.out.println(this + "isn't instance Button");
    }
}
