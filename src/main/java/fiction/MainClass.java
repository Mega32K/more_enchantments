package fiction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MainClass {
    public static void main(String[] args) {
        EntityLivingBase living = new EntityLivingBase();
        Class<?> clazz = living.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            int modifiers = m.getModifiers();
            if (!Modifier.isStatic(modifiers) && m.getReturnType().getName().equals(Float.class.getName())) {
                String lower = m.getName().toLowerCase();
                if (lower.contains("set") && lower.contains("health")) {
                    try {
                        m.invoke(living, 0F);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println(living.health);
    }
}
