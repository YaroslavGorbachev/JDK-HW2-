package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;

public class ClientGUI extends JFrame {
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_POSX = 835;
    private static final int WINDOW_POSY = 250;

    private JTextField ip;
    private JTextField ipPort;
    private JTextField nickName;
    private JPasswordField pasword;
    private JTextField msg;

    private boolean isSingIn = false;

    private JLabel nothing;

    private JButton btnLogin;
    private JTextArea logs;
    private JButton btnSend;

    private JTextArea mainText;


    ClientGUI(ServerWindow serverWindow){
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Client");

        createTopPanel(serverWindow);

        createMainText();

        createButtPanel(serverWindow);



        setVisible(true);

    }

    void createTopPanel(ServerWindow serverWindow){
        ip = new JTextField();
        ip.setText("127.0.0.1");
        ipPort = new JTextField();
        ipPort.setText("8080");
        nickName = new JTextField();
        nickName.setText("My Name");
        pasword = new JPasswordField();
        btnLogin = new JButton("Login");

        nothing = new JLabel();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isSingIn = true;
                mainText.setText(serverWindow.readFile());
            }
        });

        JPanel panTop = new JPanel(new GridLayout(2,3));
        panTop.add(ip);
        panTop.add(ipPort);
        panTop.add(nothing);
        panTop.add(nickName);
        panTop.add(pasword);
        panTop.add(btnLogin);

        add(panTop, BorderLayout.NORTH);
    }

    private void createMainText(){
        mainText = new JTextArea();
        mainText.setEnabled(false);

        add(mainText, BorderLayout.CENTER);
    }

    private void createButtPanel(ServerWindow serverWindow){
        msg = new JTextField();
        btnSend = new JButton("send");
        JPanel panelButt = new JPanel(new BorderLayout());
        panelButt.add(msg);
        panelButt.add(btnSend, BorderLayout.EAST);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverWindow.isWorkServer() && isSingIn){
                    String text = msg.getText();
                    msg.setText("");
                    mainText.setText(mainText.getText() + text + "\n");
                    sendMsgToLog(serverWindow, text);
                }
            }
        });

        msg.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (serverWindow.isWorkServer() && isSingIn){
                        String text = msg.getText();
                        msg.setText("");
                        mainText.setText(mainText.getText() + text + "\n");
                        sendMsgToLog(serverWindow, text);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        add(panelButt, BorderLayout.SOUTH);
    }

    private void sendMsgToLog(ServerWindow serverWindow, String msg){
        serverWindow.setLogs(msg);
        saveToFile(msg);
        ;   }

    private void saveToFile(String msg){
        try(FileWriter fileWriter = new FileWriter("history.txt", true);) {
            fileWriter.write(msg + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}