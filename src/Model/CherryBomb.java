package Model;

import Model.Plant;
import Model.Position;

import java.util.*;
import java.io.*;

public class CherryBomb extends Plant {
    public CherryBomb(Position position) {
        super(position);
        imageName = "CherryBomb.gif";
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }
        CherryBomb cherryBomb = (CherryBomb) obj;
        return (
                imageName.equals(cherryBomb.imageName) &
                super.equals(obj)
        );
    }
}
