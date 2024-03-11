package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ServerWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_POSX = 550;
    private static final int WINDOW_POSY = 250;

    private boolean workServer = false;

    private JTextArea logs;
    private JButton btnStart;
    private JButton btnStop;
    JPanel panBtn;

    ClientGUI clientGUI;

    ServerWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Server");
        setResizable(false);

        clientGUI = new ClientGUI(this);

        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        panBtn = new JPanel(new GridLayout(1,2));
        panBtn.add(btnStart);
        panBtn.add(btnStop);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (workServer){
                    System.out.println("Server is already running");
                }else {
                    System.out.println("Server start");
                    workServer = true;
                    setLogs(readFile());
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!workServer){
                    System.out.println("Server was stopped");
                }else {
                    System.out.println("Server stop");
                    workServer = false;
                    logs.setText("");
                }

            }
        });



        logs = new JTextArea();
        logs.setEnabled(false);
        add(logs, BorderLayout.CENTER);
        add(panBtn, BorderLayout.SOUTH);


        setVisible(true);
    }

    void setLogs(String msg){
        String text = logs.getText();
        logs.setText(text + msg + "\n");
    }

    String readFile(){
        try (FileReader fileReader = new FileReader("history.txt")) {
            Scanner scanner = new Scanner(fileReader);
            StringBuilder text = new StringBuilder();
            while (scanner.hasNextLine()){
                text.append(scanner.nextLine() + "\n");
            }
            return text.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean isWorkServer(){
        return workServer;
    }

}