package tic_tac_toe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;

    private static final String CURRENT_WIN_VALUE = "Установленная длинна: ";
    private static final String CURRENT_FIELD_VALUE = "Текущий размер поля: ";

    JRadioButton human;
    JRadioButton ai;
    ButtonGroup buttonGroup;
    JButton btnStart;
    public JLabel choiceHA;
    JPanel mainPanel;
    JLabel fieldSize;
    JLabel currentFieldSize;
    JSlider sliderFieldSize;
    JLabel winSize;
    JLabel currentWinSize;
    JSlider sliderWinSize;

    final int MIN_SIZE = 3;

    SettingsWindow(GameWindow gameWindow){
        human = new JRadioButton("Человек против человека");
        ai = new JRadioButton("Человек против компьютера");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(human);
        buttonGroup.add(ai);
        btnStart = new JButton("Start new game");
        mainPanel = new JPanel(new GridLayout(9, 1));

        choiceHA = new JLabel("Выберите режим игры");
        fieldSize = new JLabel("Выберите размер поля");
        currentFieldSize = new JLabel(CURRENT_FIELD_VALUE + MIN_SIZE);
        sliderFieldSize = new JSlider(MIN_SIZE, 10,MIN_SIZE);

        winSize = new JLabel("Выберите длину для победы");
        currentWinSize = new JLabel(CURRENT_WIN_VALUE + MIN_SIZE);
        sliderWinSize = new JSlider(MIN_SIZE, 10, MIN_SIZE);

        mainPanel.add(choiceHA);
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(0, 3, 3, 3);
                setVisible(false);
            }
        });


        sliderFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentFieldSize.setText(CURRENT_FIELD_VALUE + sliderFieldSize.getValue());
                sliderWinSize.setMaximum(sliderFieldSize.getValue());
            }
        });

        sliderWinSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentWinSize.setText(CURRENT_WIN_VALUE + sliderWinSize.getValue());
            }
        });

        mainPanel.add(ai);
        mainPanel.add(human);
        ai.setSelected(true);
        mainPanel.add(fieldSize);
        mainPanel.add(currentFieldSize);
        mainPanel.add(sliderFieldSize);

        mainPanel.add(winSize);
        mainPanel.add(currentWinSize);
        mainPanel.add(sliderWinSize);

        add(mainPanel);
        add(btnStart, BorderLayout.SOUTH);
    }
}
