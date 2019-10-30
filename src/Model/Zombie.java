package Model;

import java.util.Random;

public class Zombie extends Character {
    public static int ATTACK_POWER = 10;
    public static int DEFENCE_POWER = 10;

    ZombieTool zombieTool;

    Zombie(Position position) {
        super(position);
        int type = new Random().nextInt(4);
        switch (type) {
            case 0:
                zombieTool = null;
                break;
            case 1:
                zombieTool = new Hat();
                break;
            case 2:
                zombieTool = new Cone();
                break;
            case 3:
                zombieTool = new Bucket();
                break;
            case 4:
                zombieTool = new Javelin();
        }
    }
}
