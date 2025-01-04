package upsSP.Server;

import upsSP.GUI.GameWindow;
import upsSP.GUI.Informator;
import upsSP.GUI.Window;
import upsSP.Nastroje.Constants;
import upsSP.Nastroje.GameState;
import upsSP.Nastroje.States;
import upsSP.Nastroje.Constants;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private IListenerInQueue lisenerInQueue;

    private IListenerInGame listenerInGame;

    private IListenerAfterTurn listenerAfterTurn;

    private IListenerInJudgement listenerInJudgement;
    private IListenerAfterLogin listenerAfterLogin;
    private long time;
    boolean connected = false;
    int numberOfPings = 0;
    int numberOfPongs = 0;
    Lock lock = new ReentrantLock();
    boolean gameStopped = false;

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
            stopLisening();
            stopPinging();
            sleep(1000); //sleep na dozpracovavani zprav co jsou mozny ze jsou jeste v prubehu;
            if (socket != null) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            resetConnectionParametres();
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
        setIsConnected(true);
        liseningMessegesFromServer();
        sendingPingToServer();
    }

    private void liseningMessegesFromServer() {
        isLisening = true;
        System.out.println("Lisening...");
        lisenThread = new Thread(() -> {
            while (getIsLisening()) {
                //try {
                String message;
                try {
                    message = acceptMessage();
                    /*} catch (IOException cs) {
                        if (getIsLisening()) {
                            cs.printStackTrace();
                        } else {
                            System.out.println("Poslochani bylo zastaveno");
                        }
                        break;
                    }*/
                    if (message == null) {
                        continue;
                    }
                    System.out.println("Prijata zprava: " + message);
                    setTime(System.currentTimeMillis());
                    if (message.startsWith("Mess:pong:")) {
                        if (isConnected()) {
                            setNumberOfPongs(getNumberOfPongs() + 1);
                        } else {
                            setIsConnected(true);
                            setNumberOfPongs(getNumberOfPongs() + 1);
                            //setConfiguration(port, adress);
                            Informator.getInstance(null).repairGame();
                        }
                    }
                    if (message.startsWith("Mess:opponentConnectionProblems:")) {
                        Informator.getInstance(null).informAboutOpponentsFuckedConnection(1);
                    } else if (message.startsWith("Mess:opponentConnectionGood:")) {
                        Informator.getInstance(null).repairGame();

                    } else if (message.startsWith("Mess:opponentConnectionFall:")) {
                        GameState.getInstance().setScores(0, 0, 0);
                        Informator.getInstance(null).informAboutOpponentsFuckedConnection(-1);
                    }
                    if (message.startsWith("Mess:invalidMessage:")) {
                        System.out.println("Nevlaidni zprva poslana, odpojuji");
                        GameState.getInstance().setScores(0,0, 0);
                        Informator.getInstance(null).informAboutInvalidMessage();
                    }
                    if (message.startsWith("Mess:logout:")) {
                        closeConnection();
                        clientId = -1;
                        Informator.getInstance(null).informToShow();
                        GameState.getInstance().setScores(0,0, 0);
                        GameState.getInstance().setState(States.LOGIN);
                    }
                    if (listenerAfterLogin != null && message != null) {
                        listenerAfterLogin.onMessage(message);
                    }
                    if (lisenerInQueue != null && message != null) {
                        lisenerInQueue.onMessage(message);
                    }
                    if (listenerAfterTurn != null && message != null) {
                        //System.out.print("Prijata zprava: " + message + "\n");
                        listenerAfterTurn.onMessage(message);
                    }
                } catch (IOException cs) {
                    if (getIsLisening()) {
                        cs.printStackTrace();
                    } else {
                        System.out.println("Poslochani bylo zastaveno");
                    }
                    break;
                }
                    /*if (message.equals("Mess:břong")) {
                        pong();
                    }*/
                //} //catch (IOException e) {
                    //e.printStackTrace();
                //}
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
                    System.out.println("Pocet pingu: " + getNumberOfPings() + " Pocet pongu: " + getNumberOfPongs());
                    //long timeNow = System.currentTimeMillis();
                    //if (Math.abs(getTime() - timeNow) > 50000 && !connected) {
                    if (Math.abs(getNumberOfPongs() - getNumberOfPings()) > Constants.NUMBER_OF_PINGS && !isConnected()) {
                        GameState.getInstance().setScores(0, 0, 0);
                        closeConnection();
                        Informator.getInstance(null).informAboutTimeout();
                        break;
                    }
                    //if (Math.abs(getTime() - timeNow) > 5000 && isConnected() == true) { //pokud jeden ping ne tak spatny
                    if (Math.abs(getNumberOfPongs() - getNumberOfPings()) >= 1 && isConnected() == true) { //pokud jeden ping ne tak spatny
                        System.out.println("Problem s spojenim");
                        setIsConnected(false);
                        Informator.getInstance(null).informAboutFuckedConnection();
                    }
                    setNumberOfPings(getNumberOfPings() + 1);
                    sendMessage("Mess:ping:" + clientId +":");
                    sleep(Constants.TIME_FOR_ONE_PING);
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
        setIsLisening(false);
        //sleep();
    }
    private void  stopPinging() {
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
        this.lisenerInQueue = listenerInQueue;
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

    // Getter a setter pro time
    public long getTime() {
        lock.lock();
        try {
            return time;
        } finally {
            lock.unlock();
        }
    }

    public void setTime(long time) {
        lock.lock();
        try {
            this.time = time;
        } finally {
            lock.unlock();
        }
    }

    // Getter a setter pro connected
    public boolean isConnected() {
        lock.lock();
        try {
            return connected;
        } finally {
            lock.unlock();
        }
    }

    public void setIsConnected(boolean connected) {
        lock.lock();
        try {
            this.connected = connected;
        } finally {
            lock.unlock();
        }
    }

    // Getter pro numberOfPings
    public int getNumberOfPings() {
        lock.lock();
        try {
            return numberOfPings;
        } finally {
            lock.unlock();
        }
    }

    // Inkrementace numberOfPings o parametr
    public void setNumberOfPings(int numberOfPings) {
        lock.lock();
        try {
            this.numberOfPings = numberOfPings;
        } finally {
            lock.unlock();
        }
    }

    // Getter pro numberOfPongs
    public int getNumberOfPongs() {
        lock.lock();
        try {
            return numberOfPongs;
        } finally {
            lock.unlock();
        }
    }

    // Inkrementace numberOfPongs o parametr
    public void setNumberOfPongs(int numberOfPongs) {
        lock.lock();
        try {
            this.numberOfPongs = numberOfPongs;
        } finally {
            lock.unlock();
        }
    }

    public boolean getIsLisening() {
        lock.lock();
        try {
            return this.isLisening;
        } finally {
            lock.unlock();
        }
    }

    public void setIsLisening(boolean isLisening) {
        lock.lock();
        try {
            this.isLisening = isLisening;
        } finally {
            lock.unlock();
        }
    }

    public void resetConnectionParametres() {
        setNumberOfPings(0);
        setNumberOfPongs(0);
        setIsConnected(false);
    }

}
