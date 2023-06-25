package rikkei.academy.service.cartdetai;

import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Cart;
import rikkei.academy.model.CartDetail;
import rikkei.academy.service.cart.ICartService;
import rikkei.academy.service.product.ProductServiceIMPL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDetailServiceIMPL implements ICartDetailService {
    private Connection connection = ConnectMySQL.getConnection();
    ProductServiceIMPL productServiceIMPL = new ProductServiceIMPL();
    @Override
    public List<CartDetail> findAll() {
        return null;
    }

    @Override
    public void save(CartDetail cartDetail) {
        if (cartDetail.getId()==0){
            createNewCart(cartDetail);
        }else {
            updateCart(cartDetail);
        }

    }

    private void updateCart(CartDetail cartDetail) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call UpdateQuantityCartDetail(?,?)}");
            callableStatement.setInt(1, cartDetail.getId());
            callableStatement.setInt(2, cartDetail.getQuantityBuy());
            int row = callableStatement.executeUpdate();
            System.out.println(row);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void createNewCart(CartDetail cartDetail) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call CreateCartDetail(?,?,?)}");
            callableStatement.setInt(1, cartDetail.getIdCart());
            callableStatement.setInt(2, cartDetail.getProduct().getId());
            callableStatement.setInt(3, cartDetail.getQuantityBuy());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call DeleteCartDetailById(?)}");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CartDetail findById(int id) {
        return null;
    }

    @Override
    public List<CartDetail> findByName(String name) {
        return null;
    }

    @Override
    public List<CartDetail> findCartDetailByCart(Cart cart) {
        List<CartDetail> cartDetailList = new ArrayList<>();
        CartDetail cartDetail = null;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call FindCartdetailByCartId(?)}");
            callableStatement.setInt(1,cart.getId());
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                cartDetail = new CartDetail();
                cartDetail.setId(resultSet.getInt("id"));
                cartDetail.setIdCart(resultSet.getInt("idCart"));
                cartDetail.setProduct(productServiceIMPL.findById(resultSet.getInt("idProduct")));
                cartDetail.setQuantityBuy(resultSet.getInt("quantityBuy"));
                cartDetailList.add(cartDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cartDetailList ;
    }




}
