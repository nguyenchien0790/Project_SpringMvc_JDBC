package rikkei.academy.service.product;

import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Category;
import rikkei.academy.model.Product;
import rikkei.academy.service.category.CategoryServiceIMPL;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceIMPL implements IProductService {
    private Connection connection = ConnectMySQL.getConnection();
    CategoryServiceIMPL categoryServiceIMPL = new CategoryServiceIMPL();

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();

        try {
            CallableStatement callableStatement = connection.prepareCall("{call ShowListProduct()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                int idCategory = rs.getInt("idCategory");
                product.setCategory(categoryServiceIMPL.findById(idCategory));
                product.setImage(rs.getString("image"));
                product.setTitle(rs.getString("title"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStock(rs.getInt("stock"));
                product.setPrice(rs.getFloat("price"));
                product.setStatus(rs.getBoolean("status"));
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    @Override
    public void save(Product product) {
        if (findById(product.getId()) == null) {
            // create
            create(product);
        } else {
            // update
            update(product);
        }
    }

    private void create(Product product) {
        try {
//            connection.setAutoCommit(false);
            CallableStatement callableStatement = connection.prepareCall("{call CreateProduct(?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, product.getName());
            callableStatement.setInt(2, product.getCategory().getIdCategory());
            callableStatement.setString(3, product.getImage());
            callableStatement.setString(4, product.getTitle());
            callableStatement.setInt(5, product.getQuantity());
            callableStatement.setInt(6, product.getStock());
            callableStatement.setFloat(7, product.getPrice());
            callableStatement.setBoolean(8, product.isStatus());
            callableStatement.executeUpdate();
//            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update(Product product) {

        try {
            CallableStatement callableStatement = connection.prepareCall("{call updateProduct(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, product.getId());
            callableStatement.setString(2, product.getName());
            callableStatement.setInt(3, product.getCategory().getIdCategory());
            callableStatement.setString(4, product.getImage());
            callableStatement.setString(5, product.getTitle());
            callableStatement.setInt(6, product.getQuantity());
            callableStatement.setInt(7, product.getStock());
            callableStatement.setFloat(8, product.getPrice());
            callableStatement.setBoolean(9, product.isStatus());
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call DeleteProductById(?)}");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(int id) {
        Product product = null;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call FindProductById(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                int idCategory = resultSet.getInt("idCategory");
                product.setCategory(categoryServiceIMPL.findById(idCategory));
                product.setImage(resultSet.getString("image"));
                product.setTitle(resultSet.getString("title"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setStock(resultSet.getInt("stock"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStatus(resultSet.getBoolean("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> productList = new ArrayList<>();;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call FindProductByName(?)}");
            callableStatement.setString(1, name);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                int idCategory = rs.getInt("idCategory");
                product.setCategory(categoryServiceIMPL.findById(idCategory));
                product.setImage(rs.getString("image"));
                product.setTitle(rs.getString("title"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStock(rs.getInt("stock"));
                product.setPrice(rs.getFloat("price"));
                product.setStatus(rs.getBoolean("status"));
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    @Override
    public List<Product> showByCatalog(int id) {
        return null;
    }

    @Override
    public List<Product> findProductByPage(int page, int count) {
//        List<Product> list = findAll();
//        List<Product> result = new ArrayList<>();
//        int count1 = 0;
//        int start = (page - 1) * count;
//        while(count1 < count) {
//            Product p = list.get(start);
//            result.add(p);
//            count1++;
//            start++;
//        }

//        int start = (page - 1) * count;
//        for (int i = start; i < start + count && i < list.size(); i++) {
//            result.add(list.get(i));
//        }

        List<Product> productList = new ArrayList<>();
        int start = (page - 1) * count;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call FindProductByPage(?,?)}");
            callableStatement.setInt(1, start);
            callableStatement.setInt(2, count);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                int idCategory = rs.getInt("idCategory");
                product.setCategory(categoryServiceIMPL.findById(idCategory));
                product.setImage(rs.getString("image"));
                product.setTitle(rs.getString("title"));
                product.setQuantity(rs.getInt("quantity"));
                product.setStock(rs.getInt("stock"));
                product.setPrice(rs.getFloat("price"));
                product.setStatus(rs.getBoolean("status"));
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public int countProduct() {
        try {
            PreparedStatement ps = connection.prepareStatement("select count(*) from product");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
