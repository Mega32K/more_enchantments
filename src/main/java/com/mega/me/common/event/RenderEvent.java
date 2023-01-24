package com.mega.me.common.event;

import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class RenderEvent extends Event {
    public float particleTicks;
    public long nanos;

    public RenderEvent(float p, long n) {
        particleTicks = p;
        nanos = n;
    }

    @Cancelable
    public static class Pre extends RenderEvent {

        public Pre(float p, long n) {
            super(p, n);
        }
    }

    @Cancelable
    public static class Post extends RenderEvent {

        public Post(float p, long n) {
            super(p, n);
        }
    }
}
