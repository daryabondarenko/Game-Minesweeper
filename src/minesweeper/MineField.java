package minesweeper;

import java.awt.*;

import java.awt.Color;
import static minesweeper.Field.height;
import static minesweeper.Field.width;

public class MineField extends Field {

    public MineField() {
        super();
    }

    public void drawField(Graphics g, int x, int y) {
        //рисуем обычное поле
        super.drawField(g, x, y);

        if (opened) {
            //если открыто и помечено, рисуем зеленую мину
            if (marked) {
                g.setColor(Color.GREEN);
                g.fillOval(x * width + width / 4, y * height + height / 4, width / 2, height / 2);
            } //если открыто и не помечено, рисуем черную мину
            else {
                g.setColor(Color.BLACK);
                g.fillOval(x * width + width / 4, y * height + height / 4, width / 2, height / 2);
            }
        }
    }

    //не имеет значения, есть ли у поля с миной соседи или нет
    public void setNeighbors(Field... f) {
        neighbors = null;
        minesNear = 0;
    }

    protected boolean hasMine() {
        return true;
    }

    public boolean open() {
        opened = true;
        return false; //false указывает на то, что игра дальше продолжаться не может, была найдена мина
    }

    public boolean check() {
        opened = true; //открывается поле
        return marked; //проверяется, помечено ли поле
    }
}
