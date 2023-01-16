package xclient.mega.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xclient.mega.Config;
import xclient.mega.MegaUtil;

import java.util.List;

@Mixin(Inventory.class)
public abstract class InventoryMixin {
    @Shadow
    @Final
    public Player player;
    @Shadow
    @Final
    public NonNullList<ItemStack> armor;
    @Shadow
    public int selected;
    @Shadow
    @Final
    private List<NonNullList<ItemStack>> compartments;

    /**
     * @author mega
     * @reason banItem
     */
    @Overwrite
    public void tick() {
        for (NonNullList<ItemStack> nonnulllist : this.compartments) {
            for (int i = 0; i < nonnulllist.size(); ++i) {
                if (!nonnulllist.get(i).isEmpty()) {
                    if (!MegaUtil.get(nonnulllist.get(i).getItem())) {
                        nonnulllist.get(i).inventoryTick(this.player.level, this.player, i, this.selected == i);
                    } else {
                        if (Config.ITEM_BANNED_EMPTY.get())
                            nonnulllist.set(i, ItemStack.EMPTY);
                        player.drop(nonnulllist.get(i), true, false);
                        nonnulllist.set(i, ItemStack.EMPTY);
                    }
                }
            }
        }
        armor.forEach(e -> {
            if (!MegaUtil.get(e.getItem()))
                e.onArmorTick(player.level, player);
        });
    }
}
