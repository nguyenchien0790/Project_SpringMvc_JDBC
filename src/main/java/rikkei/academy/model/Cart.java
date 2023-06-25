package rikkei.academy.model;

import java.util.List;
import java.util.Map;

public class Cart {
    private int id;
    private int idUser;
    private boolean status = false;

    public Cart() {
    }

    public Cart(int id, int idUser, boolean status) {
        this.id = id;
        this.idUser = idUser;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
