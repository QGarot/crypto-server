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
    }

    /**
     * Handles messages which are incoming data
     */
    public void listenMessages() {
        String message;
        try {
            while ((message = this.getReader().readLine()) != null) {
                this.onEvent(this.getParsedMessage(message));
            }
        } catch (IOException e) {
            System.out.println("Can not read the received message");
        } finally {
            this.closeConnection();
        }
    }

    /**
     * Parses the string message into a JSONObject
     * @param message : incoming message, which is not null
     * @return
     */
    public JSONObject getParsedMessage(String message) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
        } catch (JSONException e) {
            System.out.println("JSON Exception: can not parse the message");
        }
        return jsonObject;
    }

    /**
     * Close the reader, writer and the socket
     */
    public void closeConnection() {
        try {
            this.getReader().close();
            this.getWriter().close();
            this.getSocket().close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.out.println("Can not close the connection.");
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
     * Handles an event which is an incoming data parsed into a JSONObject
     * @param jsonObject: collected data
     */
    public void onEvent(JSONObject jsonObject) {
        if (jsonObject != null) {

            System.out.println(this.getSocket().getRemoteSocketAddress().toString() + " sends: ");
            System.out.println(jsonObject);
            System.out.println();
        } else {
            System.out.println("The incoming event is null.");
        }
    }
}
