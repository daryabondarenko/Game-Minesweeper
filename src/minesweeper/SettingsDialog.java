package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsDialog extends JDialog {

    public SettingsDialog() {
        super();

        //создать форму
        setTitle("Настройки");
        setSize(200, 220);
        setResizable(false);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //добавить поле для ввода ширины
        JLabel jl1 = new JLabel("Ширина: ");
        add(jl1);
        JTextField jtf1 = new JTextField("10", 10);
        add(jtf1);

        //добавить поле для ввода длины
        JLabel jl2 = new JLabel("Длина: ");
        add(jl2);
        JTextField jtf2 = new JTextField("10", 10);
        add(jtf2);

        //добавить поле для ввода кол-ва мин
        JLabel jl3 = new JLabel("Количество мин: ");
        add(jl3);
        JTextField jtf3 = new JTextField("10", 10);
        add(jtf3);

        //добавить кнопку Применить
        JButton b = new JButton("Применить");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //изменить настройки игры
                changeSettings(jtf1.getText(), jtf2.getText(), jtf3.getText());
            }
        });
        add(b);

        setVisible(true);
    }

    private void changeSettings(String n1, String m1, String numMines1) {
        int n, m, numMines;

        try {
            n = Integer.parseInt(n1);
            m = Integer.parseInt(m1);
            numMines = Integer.parseInt(numMines1);

            if (n > 1 && n < 25 && m > 1 && m < 25 && n * m >= numMines && numMines > 0) {
                Minesweeper.setSettings(n, m, numMines);
            } else {
                JOptionPane.showMessageDialog(null, "Введите числа: ширина>1 \n ширина<25 \n длина>1 \n длина<25 \n ширина*длина>=кол-во мин \n кол-во мин>0", "Ошибка", 1);
            }
        } catch (NumberFormatException exc) {
            JOptionPane.showMessageDialog(null, "Введите числа: ширина>1 \n ширина<25 \n длина>1 \n длина<25 \n ширина*длина>=кол-во мин \n кол-во мин>0", "Ошибка", 1);
        }
        dispose();
    }
}
