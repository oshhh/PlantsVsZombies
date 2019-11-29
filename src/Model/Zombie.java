package Model;

import java.util.Random;

public class Zombie extends Character {
    private int ATTACK_POWER = 10;
    private int DEFENCE_POWER = 10;

    private ZombieTool zombieTool;
    private boolean attacking;
    private boolean moving;

    public Zombie(Position position) {
        super(position);
        relativeSize = 1.5;
        int type = new Random().nextInt(8);
        zombieTool = null;
        imageName = "Zombie.gif";
        moving = true;
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

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int attack() {
        return this.ATTACK_POWER + zombieTool.ATTACK_POWER;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }

    public String getDeadImageName(){
        return "DeadZombie.gif";
    }
    public void move(){
        position.setX(position.getX()-4);
    }
    public String getAttackingImageName(){
        return "Zombie.gif";
    }
}
