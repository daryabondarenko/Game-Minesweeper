package minesweeper;

import java.awt.*;

public class Field {

    public static int width, height;

    protected boolean opened; //открыто поле или нет
    protected boolean marked; //помечено или нет
    protected int minesNear; //кол-во мин рядом с полем
    protected Field[] neighbors; //соседи данного поля

    public Field() {
        marked = false;
        opened = false;
        minesNear = 0;
    }

    //рисует поле
    public void drawField(Graphics g, int x, int y) {
        //нарисовать прямоугольник
        g.setColor(Color.GRAY);
        g.fill3DRect(x * width, y * height, width, height, !opened);

        //если поле открыто и рядом с ним есть мины, вывести число мин
        if (opened && minesNear > 0) {
            g.setColor(Color.WHITE);
            g.drawString("" + minesNear, x * width + 10, y * height + 20);
        }

        //если поле помечено, нарисовать красный флаг
        if (marked && !opened) {
            g.setColor(Color.RED);
            g.fillRect(x * width + width / 4, y * height + height / 4, width / 2, height / 2);
        }
    }

    //добавить соседей
    public void setNeighbors(Field... f) {
        neighbors = f;

        //посчитать количество мин рядом с полем
        for (int i = 0; i < f.length; i++) {
            if (neighbors[i].hasMine()) {
                minesNear++;
            }
        }
    }

    protected boolean hasMine() {
        return false;
    }

    public boolean open() {
        if (!opened) {
            opened = true;
            marked = false;

            //если мин рядом нет, открыть соседние поля
            if (minesNear == 0) {
                for (int i = 0; i < neighbors.length; i++) {
                    if (!neighbors[i].opened) {
                        neighbors[i].open();
                    }
                }
            }

        }
        return true; //true указывает на то, что игра дальше продолжаться может, не была найдена мина
    }

    public boolean isMarked() {
        return marked;
    }

    public void mark() {
        marked = !marked;
    }

    public boolean check() {
        return !marked; //проверяется, не помечено ли поле
    }
}
