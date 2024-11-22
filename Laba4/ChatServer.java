package Laba4;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int UDP_PORT = 1502;
    private static final String MULTICAST_ADDRESS = "233.0.0.1";
    private static final int TCP_PORT = 1503;
    private static final long MESSAGE_INTERVAL = 10000;
    private static final List<String> messageQueue = new ArrayList<>();
    private static final List<PrintWriter> clientWriters = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try {
            // Создаем сервер для обработки TCP подключений
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            System.out.println("Server started...");

            // Многопоточный сервер для обработки TCP соединений
            ExecutorService threadPool = Executors.newCachedThreadPool();
            threadPool.execute(new UDPBroadcaster());

            // Принимаем подключения от клиентов
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Обработка сообщений и рассылка через UDP
    static class UDPBroadcaster implements Runnable {
        @Override
        public void run() {
            try {
                MulticastSocket socket = new MulticastSocket();
                InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
                socket.joinGroup(group);

                while (true) {
                    // Если есть новые сообщения, рассылаем их
                    if (!messageQueue.isEmpty()) {
                        String messagesToSend = String.join("\n", messageQueue);
                        DatagramPacket packet = new DatagramPacket(messagesToSend.getBytes(), messagesToSend.length(), group, UDP_PORT);
                        socket.send(packet);
                        messageQueue.clear(); // Очистить очередь сообщений
                    }
                    // Спим 10 секунд
                    Thread.sleep(MESSAGE_INTERVAL);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Обработка TCP сообщений от клиентов
    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Устанавливаем потоки для общения с клиентом
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                String message;
                // Считываем сообщения от клиента
                while ((message = in.readLine()) != null) {
                    synchronized (messageQueue) {
                        messageQueue.add(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        synchronized (clientWriters) {
                            clientWriters.remove(out);
                        }
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
