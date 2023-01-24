package com.mega.me.common.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class PlayerReleaseUsingEvent extends PlayerEvent {
    public ItemStack stack;
    public PlayerReleaseUsingEvent(Player player, ItemStack stack) {
        super(player);
        this.stack = stack;
    }

    @Cancelable
    public static class Pre extends PlayerReleaseUsingEvent {

        public Pre(Player player, ItemStack stack) {
            super(player, stack);
        }
    }

    @Cancelable
    public static class Post extends PlayerReleaseUsingEvent {

        public Post(Player player, ItemStack stack) {
            super(player, stack);
        }
    }
}
