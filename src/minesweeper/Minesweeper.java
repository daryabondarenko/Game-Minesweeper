package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Minesweeper {

    private static int n, m; //кол-во полей в ширину и длину
    private static int numMines; //кол-во мин
    private static JFrame jfr;
    private static GamePanel gf; //панель с полями
    private static JButton b; //кнопка Проверить (проверяет, правильно ли помечены поля флагами)

    public static void main(String[] args) {
        n = 10;
        m = 10;
        numMines = 10;

        Field.width = 30;
        Field.height = 30;

        jfr = new JFrame("Сапер");
        jfr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Игра");

        JMenuItem jmi1 = new JMenuItem("Новая игра");
        jmi1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        jm.add(jmi1);

        JMenuItem jmi2 = new JMenuItem("Настройки");
        jmi2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SettingsDialog();
            }
        });
        jm.add(jmi2);

        jmb.add(jm);
        jfr.setJMenuBar(jmb);

        newGame();

        jfr.setVisible(true);
        jfr.setLocationRelativeTo(null);
    }

    //изменить настройки
    public static void setSettings(int n1, int m1, int numMines1) {
        n = n1;
        m = m1;
        numMines = numMines1;
        newGame();
    }

    //новая игра
    public static void newGame() {
        if (gf != null) {
            jfr.remove(gf);
        }
        if (b != null) {
            jfr.remove(b);
        }

        gf = new GamePanel(n, m, numMines);

        jfr.setResizable(true);
        jfr.setSize(n * Field.width + 5, m * Field.height + 110);
        jfr.setResizable(false);
        jfr.setLayout(new BorderLayout());

        jfr.add(gf);

        b = new JButton("Проверить");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gf.check();
            }
        });
        jfr.add(b, BorderLayout.SOUTH);
    }
}
