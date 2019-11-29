package Model;

import java.io.Serializable;

public class GridPosition implements Serializable {
    private int row;
    private int column;

    public GridPosition(int row,int column){
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(getClass())) {
            return false;
        }

        GridPosition gridPosition = (GridPosition) obj;
        return (
                row == gridPosition.row &
                column == gridPosition.column
        );

    }
}
