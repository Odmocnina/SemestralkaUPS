package upsSP.Server;

import java.io.*;
import java.net.*;

public class Spojeni {
    private static final String SERVER_ADDRESS = "localhost"; // nebo IP adresa serveru
    private static final int PORT = 10000;

    private static Spojeni instance;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // Soukromý konstruktor
    private Spojeni() throws IOException {
        this.socket = new Socket(SERVER_ADDRESS, PORT);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Metoda pro získání instance Singletonu
    public static Spojeni getInstance() throws IOException {
        if (instance == null) {
            instance = new Spojeni();
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
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
