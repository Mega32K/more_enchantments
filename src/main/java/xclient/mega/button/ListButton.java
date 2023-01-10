package xclient.mega.button;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ListButton extends Button {
    public ListButton(int p_93721_, int p_93722_, int p_93723_, int p_93724_, Component p_93725_, OnPress p_93726_) {
        this(p_93721_, p_93722_, p_93723_, p_93724_, p_93725_, p_93726_, NO_TOOLTIP);
    }

    public ListButton(int p_93728_, int p_93729_, int p_93730_, int p_93731_, Component p_93732_, OnPress p_93733_, OnTooltip p_93734_) {
        super(p_93728_, p_93729_, p_93730_, p_93731_, p_93732_, p_93733_, p_93734_);
    }

}
