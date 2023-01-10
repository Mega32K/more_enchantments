package xclient.mega.mixin;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ImageButton.class)
public interface IImageButton {
    @Accessor("resourceLocation")
    @Mutable
    void setLocation(ResourceLocation location);
}
