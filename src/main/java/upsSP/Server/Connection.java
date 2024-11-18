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

    public int clientId;

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
        return in.readLine(); // čeká na odpověď od serveru
    }

    // Metoda pro uzavření spojení
    public void closeConnection() {
        try {
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
    }
}
