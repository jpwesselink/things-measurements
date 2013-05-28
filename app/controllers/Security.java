package controllers;

import models.User;

public class Security extends Secure.Security {
	static boolean authenticate(String email, String password) {
		return User.connect(email, password);
    }
	static void onAuthenticated() {
		flash.success("secure.welcome_user");
	}

	public static boolean isConnected() {
		return session.contains("email");
	}

	static void onDisconnect() {
		renderArgs.put("sessionUser", null);
		renderArgs.put("currentUser", null);
	}
}
