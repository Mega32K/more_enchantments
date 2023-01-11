package fiction;

public class EntityLivingBase {
    public float health;
    public void setHealth(float health) {
        this.health = health;
        System.out.println("setHealth");
    }

    public void sethealth(float health) {
        this.health = health;
        System.out.println("sethealth");
    }
}
