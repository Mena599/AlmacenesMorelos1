package org.example.almasenesmorelos1;

import javafx.collections.ObservableList;
import org.example.almasenesmorelos1.data.DataStore;
import org.example.almasenesmorelos1.daos.AlmacenDAO;

public class AppStore {

    private static final AppStore INSTANCE = new AppStore();
    public static AppStore getInstance() { return INSTANCE; }
    private AppStore() {}

    private final DataStore ds = DataStore.getInstance();
    private final AlmacenDAO almacenDAO = new AlmacenDAO();

    public ObservableList<Almacen> getInventario() {
        return ds.getInventario();
    }

    public void addAlmacen(Almacen a) {
        try {
            // 1) Persiste en BD
            almacenDAO.insertar(a);
            // 2) Refleja en UI (sin duplicados)
            ds.addAlmacen(a);
        } catch (Exception e) {
            e.printStackTrace();
            // aquí podrías notificar a la UI con un Alert desde el controller si lo deseas
        }
    }

    public void removeAlmacen(Almacen a) {
        try {
            almacenDAO.eliminar(a.getId());
            ds.removeAlmacen(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAll() {
        ds.clearInventario();
    }

    public void refrescarAlmacenesDesdeBD() {
        ds.refrescarAlmacenesDesdeBD();
    }
}
