package xclient.mega.mixin;

import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(FontManager.class)
public interface FM {
    @Accessor("fontSets")
    Map<ResourceLocation, FontSet> fontSets();

    @Accessor("renames")
    Map<ResourceLocation, ResourceLocation> renames();

    @Accessor("missingFontSet")
    FontSet fs();
}
