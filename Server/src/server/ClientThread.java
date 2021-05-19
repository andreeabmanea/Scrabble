package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket = null;
    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
            try {
                while (true) {
                // Get the request from the input stream: client → server
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();
                // Send the response to the oputput stream: server → client
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                String reply;
                reply = "Your command: " + request + " has been received!";
                out.println(reply);

                }
            } catch (IOException e) {
                System.err.println("Communication error... " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) { System.err.println (e); }
        }
    }

}
