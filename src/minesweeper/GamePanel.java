package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class GamePanel extends JPanel {

    private Field fields[][];
    private boolean gameOver;
    private int numMines, flags;

    public GamePanel(int n, int m, int numMines1) {
        gameOver = false;
        numMines = numMines1;
        flags = 0;
        fields = new Field[n][m];

        //создаем заминированные поля
        int k;
        for (int i = 0; i < numMines; i++) {
            k = (int) (Math.random() * (n * m - i));

            while (fields[k % n][k / n] != null) {
                k++;
            }

            fields[k % n][k / n] = new MineField();
        }

        //создаем остальные поля
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (fields[i][j] == null) {
                    fields[i][j] = new Field();
                }
            }
        }

        //добавляем соседей каждого поля
        //для угловых полей
        fields[0][0].setNeighbors(fields[1][0], fields[0][1], fields[1][1]);
        fields[0][m - 1].setNeighbors(fields[1][m - 1], fields[0][m - 2], fields[1][m - 2]);
        fields[n - 1][0].setNeighbors(fields[n - 2][0], fields[n - 1][1], fields[n - 2][1]);
        fields[n - 1][m - 1].setNeighbors(fields[n - 2][m - 1], fields[n - 1][m - 2], fields[n - 2][m - 2]);

        //для полей у верхней и нижней границы
        for (int i = 1; i < m - 1; i++) {
            fields[0][i].setNeighbors(fields[1][i], fields[0][i + 1], fields[0][i - 1], fields[1][i + 1], fields[1][i - 1]);
            fields[n - 1][i].setNeighbors(fields[n - 2][i], fields[n - 1][i + 1], fields[n - 1][i - 1], fields[n - 2][i + 1], fields[n - 2][i - 1]);
        }

        //для полей у правой и левой границы
        for (int i = 1; i < n - 1; i++) {
            fields[i][0].setNeighbors(fields[i + 1][0], fields[i - 1][0], fields[i][1], fields[i + 1][1], fields[i - 1][1]);
            fields[i][m - 1].setNeighbors(fields[i + 1][m - 1], fields[i - 1][m - 1], fields[i][m - 2], fields[i + 1][m - 2], fields[i - 1][m - 2]);
        }

        //для остальных полей
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                fields[i][j].setNeighbors(fields[i + 1][j], fields[i - 1][j], fields[i][j + 1], fields[i][j - 1], fields[i + 1][j + 1], fields[i - 1][j + 1], fields[i + 1][j - 1], fields[i - 1][j - 1]);
            }
        }

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!gameOver && (e.getX() < n * Field.width && e.getX() > 0 && e.getY() < m * Field.height && e.getY() > 0)) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        //обработка нажатия левой кнопкой мыши
                        //if (e.getX() < n * Field.width && e.getX() > 0 && e.getY() < m * Field.height && e.getY() > 0) {
                        if (!fields[e.getX() / Field.width][e.getY() / Field.height].open()) {
                            check(); //если была найдена мина
                        }
                        //}

                    }
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        //обработка нажатия правой кнопкой мыши

                        if (!fields[e.getX() / Field.width][e.getY() / Field.height].isMarked() && flags < numMines) {
                            //пометить поле, если не помечено и кол-во флагов меньше кол-ва мин
                            fields[e.getX() / Field.width][e.getY() / Field.height].mark();
                            flags++;
                        } else if (fields[e.getX() / Field.width][e.getY() / Field.height].isMarked()) {
                            //убрать пометку, если уже помечено
                            fields[e.getX() / Field.width][e.getY() / Field.height].mark();
                            flags--;
                        }

                    }
                    repaint();
                }
            }
        }
        );
    }

    public void paint(Graphics g) {
        super.paint(g);

        //нарисовать каждое поле
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fields[i][j].drawField(g, i, j);
            }
        }

        //добавить надпись с кол-вом отмеченных полей
        g.setColor(Color.DARK_GRAY);
        g.drawString("" + flags + "/" + numMines, 0, fields[0].length * Field.height + 15);
    }

    //проверяет каждое поле в конце игры и выдает сообщение о победе или проигрыше
    public void check() {
        if (!gameOver) {
            boolean f = true;

            //проверить каждое поле
            for (int i = 0; i < fields.length; i++) {
                for (int j = 0; j < fields[i].length; j++) {
                    if (!fields[i][j].check()) {
                        f = false;
                    }
                }
            }

            repaint();

            if (!f) {
                JOptionPane.showMessageDialog(null, "Игра окончена. Вы проиграли.", "Сапер", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Игра окончена. Вы пробедили!", "Сапер", 1);
            }
            gameOver = true;
        }
    }
}
