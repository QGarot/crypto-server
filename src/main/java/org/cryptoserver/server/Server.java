package org.cryptoserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends ServerSocket {

    private final List<Connection> connections = new ArrayList<>();

    public Server(int port) throws IOException {
        super(port);
        System.out.println("Server started on port " + port + "\n");
    }

    public List<Connection> getConnections() {
        return this.connections;
    }

    public void listenClientConnection() throws IOException {
        Socket clientSocket = this.accept();
        System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());

        // Create a connection object and add it to the list of all current connections
        Connection connection = new Connection(clientSocket);
        this.getConnections().add(connection);

        // Make the connection listen for incoming data
        connection.listenMessages();
    }
}
