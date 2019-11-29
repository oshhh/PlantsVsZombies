package Model;

import java.io.Serializable;

public abstract class ZombieTool implements Serializable {
    public int ATTACK_POWER;
    public int DEFENCE_POWER;
    @Override
    public boolean equals(Object obj){
        if(!obj.getClass().equals(getClass())) {
            return false;
        }
        ZombieTool zombieTool = (ZombieTool) obj;
        return (ATTACK_POWER==zombieTool.ATTACK_POWER && DEFENCE_POWER==zombieTool.DEFENCE_POWER);
    }
}
