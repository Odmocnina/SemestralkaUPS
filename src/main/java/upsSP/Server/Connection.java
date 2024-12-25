package upsSP.Server;

import java.io.*;
import java.net.*;

import static java.lang.Thread.sleep;

public class Connection {
    private int port;
    String adress;
    private static Connection instance;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isLisening = false; //posloucham
    private boolean isSending = false;  //posilam
    private Thread lisenThread, pingThread;

    public int clientId;
    private IListenerInQueue listenerÍnQueue;

    private IListenerInGame listenerInGame;

    private IListenerAfterTurn listenerAfterTurn;

    private IListenerInJudgement listenerInJudgement;
    private IListenerAfterLogin listenerAfterLogin;

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
        out.println(message); //tedka jsem to zmenil z println na print nebo z posilani s lomeno n a bez, tedka bez \n
        out.flush();
        System.out.println("Posilam: " + message);
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
            sleep(1000); //sleep na dozpracovavani zprav co jsou mozny ze jsou jeste v prubehu
            stopLisening();
            stopPinging();
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException | InterruptedException e) {
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
        //sendingPingToServer();
    }

    private void liseningMessegesFromServer() {
        isLisening = true;
        System.out.println("Lisening...");
        lisenThread = new Thread(() -> {
            while (isLisening) {
                try {
                    String message = acceptMessage();
                    System.out.println("Prijata zprava: " + message);
                    if (listenerAfterLogin != null && message != null) {
                        //System.out.print("Prijata zprava: " + message + "\n");
                        listenerAfterLogin.onMessage(message);
                    }
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
                    /*if (message.equals("Mess:břong")) {
                        pong();
                    }*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        lisenThread.start();
    }

    public void sendingPingToServer() {
        isSending = true;
        System.out.println("Sending ping...");
        pingThread = new Thread(() -> {
            while (isSending) {
                try {
                    sendMessage("Mess:ping:" + clientId +":");
                    sleep(5000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        pingThread.start();
    }

    private void pong() throws IOException {
        Connection.getInstance().sendMessage("Mess:pong:\n");
    }

    private void stopLisening() {
        isLisening = false;
        //sleep();
    }
    private void stopPinging() {
        isSending = false;
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

    public interface IListenerInJudgement {
        void onMessage(String message);
    }

    public interface IListenerAfterLogin {
        void onMessage(String message);
    }

//    private void pong() {
//
//    }

    public void addListnerInQueue(IListenerInQueue listenerInQueue) {
        this.listenerÍnQueue = listenerInQueue;
    }

    public void addListnerInGame(IListenerInGame listenerInGame) {
        this.listenerInGame = listenerInGame;
    }

    public void addListnerAfterTurn(IListenerAfterTurn listenerAfterTurn) {
        this.listenerAfterTurn = listenerAfterTurn;
    }

    public void addListnerAfterLogin(IListenerAfterLogin listenerAfterLogin) {
        this.listenerAfterLogin = listenerAfterLogin;
    }

    public void addListnerInJudgement(IListenerInJudgement listenerInJudgement) {
        this.listenerInJudgement = listenerInJudgement;
    }

}
