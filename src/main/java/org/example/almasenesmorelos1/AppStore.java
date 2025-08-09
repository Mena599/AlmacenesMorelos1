package org.example.almasenesmorelos1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.math.BigDecimal;

/**
 * Store central de la app.
 * - Mantiene el inventario (almacenes).
 * - Mantiene las publicaciones (venta y renta).
 * - Expone listas filtradas reactivas para Venta y Renta.
 *
 * Mañana, cuando conectes BD, basta con persistir aquí dentro:
 * las vistas siguen funcionando igual (reactividad por ObservableList).
 */
public class AppStore {

    // --- Singleton ---
    private static final AppStore INSTANCE = new AppStore();
    public static AppStore getInstance() { return INSTANCE; }
    private AppStore() {}

    // --- Estado central ---
    private final ObservableList<Almacen> inventario = FXCollections.observableArrayList();
    private final ObservableList<Publicacion> publicaciones = FXCollections.observableArrayList();

    // Filtradas reactivas (se actualizan en automático)
    private final FilteredList<Publicacion> publicacionesVenta =
            new FilteredList<>(publicaciones, p -> p.getTipo() == TipoPublicacion.VENTA);
    private final FilteredList<Publicacion> publicacionesRenta =
            new FilteredList<>(publicaciones, p -> p.getTipo() == TipoPublicacion.RENTA);

    // --- Getters para que las vistas se suscriban ---
    public ObservableList<Almacen> getInventario() { return inventario; }
    public FilteredList<Publicacion> getPublicacionesVenta() { return publicacionesVenta; }
    public FilteredList<Publicacion> getPublicacionesRenta() { return publicacionesRenta; }

    /**
     * Agrega un Almacén al inventario y opcionalmente crea una publicación
     * de VENTA o RENTA con precio. Si el tipo es NINGUNA, solo lo agrega al inventario.
     */
    public void addAlmacen(Almacen a, TipoPublicacion tipo, BigDecimal precio) {
        inventario.add(a);

        if (tipo != null && tipo != TipoPublicacion.NINGUNA) {
            if (precio == null) {
                // Por seguridad, si no te pasan precio, lo forzamos a 0.00
                precio = BigDecimal.ZERO;
            }
            publicaciones.add(new Publicacion(a, tipo, precio));
        }
    }

    // Helpers opcionales para borrar/limpiar (útiles en sprints futuros)
    public void removeAlmacen(Almacen a) {
        // 1) elimina publicaciones asociadas
        publicaciones.removeIf(p -> p.getAlmacen().equals(a));
        // 2) elimina del inventario
        inventario.remove(a);
    }

    public void clearAll() {
        publicaciones.clear();
        inventario.clear();
    }
}
