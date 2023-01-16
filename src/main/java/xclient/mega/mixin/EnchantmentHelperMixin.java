package xclient.mega.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xclient.mega.MegaUtil;

import javax.annotation.Nullable;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Shadow
    public static int getEnchantmentLevel(CompoundTag p_182439_) {
        return 0;
    }

    @Shadow
    @Nullable
    public static ResourceLocation getEnchantmentId(Enchantment p_182433_) {
        return null;
    }

    @Shadow
    @Nullable
    public static ResourceLocation getEnchantmentId(CompoundTag p_182447_) {
        return null;
    }

    /**
     * @author mega
     * @reason banEffect
     */
    @Overwrite
    public static int getItemEnchantmentLevel(Enchantment p_44844_, ItemStack p_44845_) {
        if (MegaUtil.get(p_44844_))
            return 0;
        if (!p_44845_.isEmpty()) {
            ResourceLocation resourcelocation = getEnchantmentId(p_44844_);
            ListTag listtag = p_44845_.getEnchantmentTags();

            for (int i = 0; i < listtag.size(); ++i) {
                CompoundTag compoundtag = listtag.getCompound(i);
                ResourceLocation resourcelocation1 = getEnchantmentId(compoundtag);
                if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
                    return getEnchantmentLevel(compoundtag);
                }
            }

        }
        return 0;
    }
}
