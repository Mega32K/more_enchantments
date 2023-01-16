package xclient.mega.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xclient.mega.MegaUtil;

@Mixin(value = Enchantment.class, priority = Integer.MAX_VALUE)
public abstract class EnchantmentMixin extends net.minecraftforge.registries.ForgeRegistryEntry<Enchantment> {
    @Shadow
    public abstract String getDescriptionId();

    @Shadow
    public abstract boolean isCurse();

    @Shadow
    public abstract int getMaxLevel();

    /**
     * @author mega
     * @reason withBanned name
     */
    @Overwrite
    public Component getFullname(int p_44701_) {
        MutableComponent mutablecomponent = new TranslatableComponent(this.getDescriptionId());
        if (this.isCurse()) {
            mutablecomponent.withStyle(ChatFormatting.RED);
        } else {
            mutablecomponent.withStyle(ChatFormatting.GRAY);
        }

        if (p_44701_ != 1 || this.getMaxLevel() != 1) {
            mutablecomponent.append(" ").append(new TranslatableComponent("enchantment.level." + p_44701_));
        }
        if (MegaUtil.get(this))
            mutablecomponent.append(" " + I18n.get("enc_banning.disabled"));

        return mutablecomponent;
    }

    @Inject(method = "canApplyAtEnchantingTable", at = @At("HEAD"), cancellable = true, remap = false)
    public void canApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (MegaUtil.get(this))
            cir.setReturnValue(false);
    }
}
