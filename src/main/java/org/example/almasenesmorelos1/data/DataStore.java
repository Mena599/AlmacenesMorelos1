package org.example.almasenesmorelos1.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.almasenesmorelos1.model.Sede;

public class DataStore {
    private static final DataStore INSTANCE = new DataStore();
    private final ObservableList<Sede> sedes = FXCollections.observableArrayList();

    private DataStore() {}

    public static DataStore getInstance() { return INSTANCE; }
    public ObservableList<Sede> getSedes() { return sedes; }

    public void agregarSede(Sede s) {
        // evita duplicados por id
        boolean exists = sedes.stream().anyMatch(x -> x.getId().equalsIgnoreCase(s.getId()));
        if (!exists) sedes.add(s);
    }
}
