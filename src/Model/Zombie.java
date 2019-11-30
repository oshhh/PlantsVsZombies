package Model;

import java.io.Serializable;
import java.util.Random;

public class Zombie extends Character implements Serializable {
    private int ATTACK_POWER = 10;
    private int DEFENCE_POWER = 10;

    private ZombieTool zombieTool;
    private volatile boolean attacking;
    private volatile boolean moving;
    private String attackImageName;
    private String deadImageName;

    public Zombie(Position position) {
        super(position);
        relativeSize = 1.5;
        int type = new Random().nextInt(8);
        zombieTool = null;
        imageName = "Zombie.gif";
        deadImageName = imageName;
        attackImageName = "ZombieAttack.gif";
        moving = true;
        /*
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
        */
    }

    public boolean isAttacking() {
        return attacking;
    }

    public synchronized void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int attack() {
        if(zombieTool == null) {
            return this.ATTACK_POWER;
        } else {
            return this.ATTACK_POWER + zombieTool.ATTACK_POWER;
        }
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
        return deadImageName;
    }

    public void move(){
        position.setX(position.getX() - 1);
    }

    @Override
    public boolean equals(Object obj){
        if(!obj.getClass().equals(getClass())) {
            return false;
        }
        Zombie zombie = (Zombie) obj;
        return  (
                ATTACK_POWER == zombie.ATTACK_POWER &
                DEFENCE_POWER == zombie.DEFENCE_POWER &
                attacking == zombie.attacking &
                moving == zombie.moving &
                attackImageName.equals(zombie.attackImageName) & super.equals(obj)
        );

    }
    public String getAttackingImageName(){
        return attackImageName;
    }
}
