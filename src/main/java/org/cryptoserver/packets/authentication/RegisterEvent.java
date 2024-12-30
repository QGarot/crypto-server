package org.cryptoserver.packets.authentication;

import org.cryptoserver.packets.Event;
import org.cryptoserver.packets.headers.OutgoingHeaders;
import org.cryptoserver.server.Connection;
import org.cryptoserver.users.UserManager;
import org.json.JSONObject;

public class RegisterEvent extends Event {

    @Override
    public void handle(Connection connection, JSONObject packet) {
        String username = packet.getString("username");
        String password = packet.getString("password");
        String confirmPassword = packet.getString("confirmPassword");

        JSONObject response = new JSONObject();
        response.put("header", OutgoingHeaders.REGISTER_RESPONSE);

        System.out.println(username);

        if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if (password.equals(confirmPassword)) {
                if (!UserManager.getInstance().getUserDetailsDao().isRegistered(username)) {
                    // Register user in database
                    UserManager.getInstance().getUserDetailsDao().insert(username, "", password);
                    // Success message
                    response.put("success", true);
                    response.put("message", "Inscription validée ! Retournez sur le formulaire de connexion");
                    connection.sendPacket(response);
                } else {
                    response.put("success", false);
                    response.put("message", "Ce nom d'utilisateur est déjà pris");
                    connection.sendPacket(response);
                }
            } else {
                response.put("success", false);
                response.put("message", "Les mots de passe ne correspondent pas");
                connection.sendPacket(response);
            }
        } else {
            response.put("success", false);
            response.put("message", "Tous les champs doivent être remplis");
            connection.sendPacket(response);
        }
    }
}
