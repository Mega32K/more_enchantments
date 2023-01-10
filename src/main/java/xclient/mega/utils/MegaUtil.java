package xclient.mega.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MegaUtil {

    public static List<Entity> getEntitiesToWatch(int level, Player player) {
        if (player == null)
            return null;
        List<Entity> entities = new ArrayList<>();
        for (int dis = 0;dis < level * 2;dis += 2) {
            AABB aabb = player.getBoundingBox().inflate(2, 2, 2);
            Vec3 vec = player.getLookAngle();
            vec = vec.normalize();
            aabb = aabb.move(vec.x * dis, vec.y * dis, vec.z * dis);
            List<Entity> list = player.level.getEntities(player, aabb);
            entities.addAll(list);
        }
        return entities;
    }

    public static Entity getEntityToWatch(int level, Player player) {
        if (player == null)
            return null;
        Entity entity = null;
        for (int dis = 0;dis < level * 2;dis += 2) {
            AABB aabb = player.getBoundingBox();
            Vec3 vec = player.getLookAngle();
            vec = vec.normalize();
            aabb = aabb.move(vec.x * dis, vec.y * dis, vec.z * dis);
            List<Entity> list = player.level.getEntities(player, aabb);
            float distance = level;
            for (Entity curEntity : list) {
                float curDist = curEntity.distanceTo((Entity)player);
                if (curDist < distance) {
                    entity = curEntity;
                    distance = curDist;
                }
            }
            if (entity != null)
                break;
        }
        return entity;
    }

    public static double circleX(double x, double r, double a) {
        return x + r * Math.cos(Math.toRadians(a));
    }

    public static double circleY(double y, double r, double a) {
        return y + r * Math.sin(Math.toRadians(a));
    }
}