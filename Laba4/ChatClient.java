package Laba4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int TCP_PORT = 1503;
    private static final int UDP_PORT = 1502;
    private static final String MULTICAST_ADDRESS = "233.0.0.1";
    private static DatagramSocket udpSocket;
    private static MulticastSocket multicastSocket;
    private static InetAddress group;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JTextField textField = new JTextField();
        frame.add(textField, BorderLayout.SOUTH);

        // Устанавливаем обработчик для отправки сообщений
        textField.addActionListener(e -> {
            String message = textField.getText();
            sendMessageToServer(message);
            textField.setText("");
        });

        // Подключаемся к серверу через TCP
        new Thread(() -> {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, TCP_PORT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String serverMessage;
                while ((serverMessage = reader.readLine()) != null) {
                    textArea.append("Server: " + serverMessage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Получаем сообщения через UDP
        new Thread(() -> {
            try {
                udpSocket = new DatagramSocket();
                multicastSocket = new MulticastSocket(UDP_PORT);
                group = InetAddress.getByName(MULTICAST_ADDRESS);
                multicastSocket.joinGroup(group);

                while (true) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    multicastSocket.receive(packet);
                    String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                    textArea.append("Received: " + receivedMessage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        frame.setVisible(true);
    }

    // Отправка сообщений серверу через TCP
    private static void sendMessageToServer(String message) {
        try (Socket socket = new Socket(SERVER_ADDRESS, TCP_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
