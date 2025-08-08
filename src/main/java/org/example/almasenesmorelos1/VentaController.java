package org.example.almasenesmorelos1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class VentaController {

    @FXML
    private TextField searchField;

    // Botones de la barra lateral para navegación
    @FXML
    private Button inicioButton;
    @FXML
    private Button almacenesButton;
    @FXML
    private Button logoutButton;

    // Tarjetas de almacenes y sus botones de "Comprar"
    @FXML
    private VBox card1, card2, card3, card4;
    @FXML
    private Button buyButton1, buyButton2, buyButton3, buyButton4;

    /**
     * Este método se inicializa al cargar la vista FXML.
     */
    @FXML
    public void initialize() {
        System.out.println("VentaController inicializado.");
    }

    // Métodos para la barra de navegación superior

    @FXML
    private void handleSearchAction(MouseEvent event) {
        String searchText = searchField.getText();
        System.out.println("Buscando: " + searchText);
    }

    @FXML
    private void handleProfileClick(MouseEvent event) {
        System.out.println("Ícono de perfil clicado.");
    }

    @FXML
    private void handleNotificationsClick(MouseEvent event) {
        System.out.println("Ícono de notificaciones clicado.");
    }

    // Métodos de navegación de la barra lateral

    /**
     * Navega a la vista de Inicio.fxml
     */
    @FXML
    private void handleInicioAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Navegando a Inicio.fxml");
        } catch (IOException e) {
            System.err.println("Error al cargar la vista Inicio.fxml: " + e.getMessage());
        }
    }

    /**
     * Navega a la vista de Renta.fxml (Almacenes).
     */
    @FXML
    private void handleAlmacenesAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Renta.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Navegando a Renta.fxml");
        } catch (IOException e) {
            System.err.println("Error al cargar la vista Renta.fxml: " + e.getMessage());
        }
    }

    /**
     * Cierra la sesión y navega a la vista de Login.fxml.
     */
    @FXML
    private void handleLogoutAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Cerrando sesión y navegando a Login.fxml");
        } catch (IOException e) {
            System.err.println("Error al cargar la vista Login.fxml: " + e.getMessage());
        }
    }

    // Métodos para los botones de compra

    /**
     * Maneja la acción del botón "Comprar" en cualquiera de las tarjetas.
     */
    @FXML
    private void handleBuyAction(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        String buttonId = sourceButton.getId();

        System.out.println("Botón 'Comprar' con ID " + buttonId + " presionado.");

        // Lógica de compra basada en el ID del botón
        if (buttonId.equals("buyButton1")) {
            System.out.println("Comprando almacén 1.");
        } else if (buttonId.equals("buyButton2")) {
            System.out.println("Comprando almacén 2.");
        } else if (buttonId.equals("buyButton3")) {
            System.out.println("Comprando almacén 3.");
        } else if (buttonId.equals("buyButton4")) {
            System.out.println("Comprando almacén 4.");
        }
    }
}