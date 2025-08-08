package org.example.almasenesmorelos1.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Encriptar la contraseña
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verificar si una contraseña coincide con el hash
    public static boolean verificarPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
