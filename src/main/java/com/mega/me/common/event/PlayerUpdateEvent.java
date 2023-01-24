package com.mega.me.common.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class PlayerUpdateEvent extends Event {
    protected final Player player;

    public PlayerUpdateEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Cancelable
    public static class Pre extends PlayerUpdateEvent {

        public Pre(Player player) {
            super(player);
        }
    }

    @Cancelable
    public static class Post extends PlayerUpdateEvent {

        public Post(Player player) {
            super(player);
        }
    }
}
