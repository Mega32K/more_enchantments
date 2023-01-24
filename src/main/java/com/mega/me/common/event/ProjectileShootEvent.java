package com.mega.me.common.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ProjectileShootEvent extends Event {
    protected Projectile p;
    protected Entity e;
    protected float xr;
    protected float yr;
    protected float a;
    protected float ss;
    protected float sl;
    public ProjectileShootEvent(Projectile projectile, Entity owner, float xrot, float yrot, float a, float SpeedScale, float SpeedLevel) {
        p = projectile;
        e = owner;
        xr = xrot;
        yr = yrot;
        this.a = a;
        this.ss = SpeedScale;
        this.sl = SpeedLevel;
    }

    public Projectile getProjectile() {
        return p;
    }

    public Entity getOwner() {
        return e;
    }

    public float getA() {
        return a;
    }

    public float getSpeedLevel() {
        return sl;
    }

    public float getSpeedScale() {
        return ss;
    }

    public float getXrot() {
        return xr;
    }

    public float getYrot() {
        return yr;
    }
}
