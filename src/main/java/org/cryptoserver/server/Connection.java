package org.cryptoserver.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {

    // A socket is an endpoint for communication between two machines.
    private Socket socket;

    private final BufferedReader reader;
    private final PrintWriter writer;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(this.getSocket().getInputStream()));
        this.writer = new PrintWriter(this.getSocket().getOutputStream(), true);
        this.listenMessages();
    }

    /**
     * Messages are incoming data
     */
    public void listenMessages() {
        String message;

        // Try to read new data coming from client (String)
        try {
            message = this.getReader().readLine();

            // Try to parse the message to a JSONArray
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(message);
            } catch (JSONException e) {
                System.out.println("JSON Exception: can not parse the received message");
                System.out.println(e.getMessage());
            }

            // if the JSONObject is not null, then it is ready to be processed
            if (jsonObject != null) {
                this.onEvent(jsonObject);
            }
        } catch (IOException e) {
            System.out.println("Can not read the received message");
        }
    }

    public BufferedReader getReader() {
        return this.reader;
    }

    public PrintWriter getWriter() {
        return this.writer;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * An event is an incoming data parsed into a JSONObject
     * @param jsonObject: collected data
     */
    public void onEvent(JSONObject jsonObject) {
        System.out.println(jsonObject.toString());
    }
}
