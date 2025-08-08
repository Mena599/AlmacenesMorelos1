package org.example.almasenesmorelos1.utils;

import org.example.almasenesmorelos1.utils.PasswordUtils;

public class TestPassword {
    public static void main(String[] args) {
        // Contraseña a encriptar
        String contrasena = "admin123";
        String hash = PasswordUtils.hashPassword(contrasena);

        System.out.println("🔑 Hash generado: " + hash);

        // Verificar contraseña
        boolean coincide = PasswordUtils.verificarPassword("admin123", hash);
        if (coincide) {
            System.out.println("✅ Contraseña válida");
        } else {
            System.out.println("❌ Contraseña incorrecta");
        }
    }
}
