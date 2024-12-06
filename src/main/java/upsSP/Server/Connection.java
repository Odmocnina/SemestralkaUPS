package upsSP.Server;

import java.io.*;
import java.net.*;

public class Connection {
    private int port;
    String adress;
    private static Connection instance;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isLisening = false; //posloucham
    private Thread lisenThread;

    public int clientId;
    private IListenerInQueue listenerÍnQueue;

    private IListenerInGame listenerInGame;

    private IListenerAfterTurn listenerAfterTurn;

    // Soukromý konstruktor
    private Connection() {
    }

    // Metoda pro získání instance Singletonu
    public static Connection getInstance() throws IOException {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    // Metoda pro odeslání zprávy serveru a přečtení odpovědi
    public String sendMessage(String message) throws IOException {
        out.println(message);
        out.flush();
        /*if (in != null) {
            String messageRecieved = in.readLine();
            if (messageRecieved != null) {
                return messageRecieved;
            }
        }
        System.out.println("Server didn't respond");*/
        return null;
    }

    public String acceptMessage() throws IOException {
        return in.readLine();
    }

    // Metoda pro uzavření spojení
    public void closeConnection() {
        try {
            stopLisening();
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPort(int port) {
        this.port = port;
    }

    private void setAdress(String adress) {
        this.adress = adress;
    }

    public void setConfiguration(int port, String adress) throws IOException {
        setPort(port);
        setAdress(adress);
        this.socket = new Socket(adress, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        liseningMessegesFromServer();
    }

    private void liseningMessegesFromServer() {
        isLisening = true;
        System.out.println("Lisening...");
        lisenThread = new Thread(() -> {
            while (isLisening) {
                try {
                    String message = acceptMessage();
                    System.out.println("Prijata zprava: " + message);
                    if (listenerÍnQueue != null && message != null) {
                        //System.out.print("Prijata zprava: " + message + "\n");
                        listenerÍnQueue.onMessage(message);
                    }
                    //if (listenerInGame != null && message != null) {
                        //System.out.print("Prijata zprava: " + message + "\n");
                        //listenerInGame.onMessage(message);
                    //}
                    if (listenerAfterTurn != null && message != null) {
                        //System.out.print("Prijata zprava: " + message + "\n");
                        listenerAfterTurn.onMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        lisenThread.start();
    }

    private void stopLisening() {
        isLisening = false;
        //sleep();
    }
    public interface IListenerInQueue {
        void onMessage(String message);
    }

    public interface IListenerInGame {
        void onMessage(String message);
    }

    public interface IListenerAfterTurn {
        void onMessage(String message);
    }

    public void addListnerInQueue(IListenerInQueue listenerInQueue) {
        this.listenerÍnQueue = listenerInQueue;
    }

    public void addListnerInGame(IListenerInGame listenerInGame) {
        this.listenerInGame = listenerInGame;
    }

    public void addListnerAfterTurn(IListenerAfterTurn listenerAfterTurn) {
        this.listenerAfterTurn = listenerAfterTurn;
    }

}
