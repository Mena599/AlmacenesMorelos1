package org.example.almasenesmorelos1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Store central de la app.
 * - Mantiene el inventario (almacenes).
 * - Se conecta con las vistas mediante listas observables.
 * - Fácil de migrar a BD: solo persistes aquí y la UI se actualiza sola.
 */
public class AppStore {

    // --- Singleton ---
    private static final AppStore INSTANCE = new AppStore();
    public static AppStore getInstance() { return INSTANCE; }
    private AppStore() {}

    // --- Estado central ---
    private final ObservableList<Almacen> inventario = FXCollections.observableArrayList();

    // --- Getter para que las vistas se suscriban ---
    public ObservableList<Almacen> getInventario() {
        return inventario;
    }

    // --- Operaciones básicas ---
    public void addAlmacen(Almacen a) {
        inventario.add(a);
    }

    public void removeAlmacen(Almacen a) {
        inventario.remove(a);
    }

    public void clearAll() {
        inventario.clear();
    }
}
