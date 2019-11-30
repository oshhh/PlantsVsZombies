package Model;

import Model.Plant;
import Model.Position;
import Model.SunToken;

import java.util.*;
import java.io.*;

public class SunFlower extends Plant {
    private boolean sunFlowerFlag;
    public SunFlower(Position position) {
        super(position);
        this.health = 70;
        imageName = "SunFlower.gif";
    }
    public void setSunFlowerFlag(boolean sunFlowerFlag) {
        this.sunFlowerFlag = sunFlowerFlag;
    }

    public boolean getSunFlowerFlag(){
        return  sunFlowerFlag;
    }
}
