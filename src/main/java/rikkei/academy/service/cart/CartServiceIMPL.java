package rikkei.academy.service.cart;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Cart;
import rikkei.academy.model.CartDetail;
import rikkei.academy.model.Product;
import rikkei.academy.model.User;
import rikkei.academy.service.cartdetai.CartDetailServiceIMPL;

import javax.servlet.http.HttpSession;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartServiceIMPL implements ICartService{
    private Connection connection = ConnectMySQL.getConnection();

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public void save(Cart cart) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call CreateCart(?,?)}");
            callableStatement.setInt(1, cart.getIdUser());
            callableStatement.setBoolean(2, cart.isStatus());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Cart findById(int id) {
        return null;
    }

    @Override
    public List<Cart> findByName(String name) {
        return null;
    }

    @Override
    public Cart findByUserId(int id) {
        Cart cart = null;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call FindCartByUserIdAndStatus(?,?)}");
            callableStatement.setInt(1, id);
            callableStatement.setBoolean(2, false);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                cart = new Cart();
                cart.setId(resultSet.getInt("id"));
                cart.setIdUser(resultSet.getInt("idUser"));
                cart.setStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cart;
    }

    @Override
    public void changeStatusCart(int id, boolean pay) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call UpdateStatusCart(?,?)}");
            callableStatement.setInt(1,id);
            callableStatement.setBoolean(2,pay);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cart createNewCartForUser(User user) {
        save(new Cart(0, user.getId(), false));
        return findByUserId(user.getId());
    }
}
