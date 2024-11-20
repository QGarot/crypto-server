package org.cryptoserver.packets.authentication;

import org.cryptoserver.packets.Event;
import org.cryptoserver.server.Connection;
import org.cryptoserver.users.UserManager;
import org.json.JSONObject;

public class RegisterEvent extends Event {

    @Override
    public void handle(Connection connection, JSONObject packet) {
        String username = packet.getString("username");
        String password = packet.getString("password");
        String confirmPassword = packet.getString("confirmPassword");

        if (password.equals(confirmPassword)) {
            if (!UserManager.getInstance().getUserDetailsDao().isRegistered(username)) {
                UserManager.getInstance().getUserDetailsDao().insert(username, "", password);
            }
        }
    }
}
