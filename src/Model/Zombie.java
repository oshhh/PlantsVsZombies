package Model;

import java.util.Random;

public class Zombie extends Character {
    public static int ATTACK_POWER = 10;
    public static int DEFENCE_POWER = 10;

    private ZombieTool zombieTool;

    public Zombie(Position position) {
        super(position);
        relativeSize = 1.5;
        int type = new Random().nextInt(8);
        zombieTool = null;
        imageName = "Zombie.gif";
        switch (type) {
            case 0:
                zombieTool = new Flag();
                imageName = "ZombieFlag.gif";
                relativeSize = 2;
                break;
            case 1:
                zombieTool = new Cone();
                imageName = "ZombieCone.gif";
                relativeSize = 2;
                break;
            case 2:
                zombieTool = new Bucket();
                imageName = "ZombieBucket.gif";
                relativeSize = 2;
                break;
            case 3:
                zombieTool = new FlyingCap();
                imageName = "ZombieFlyingCap.gif";
                relativeSize = 2;
                break;
        }
    }
}
