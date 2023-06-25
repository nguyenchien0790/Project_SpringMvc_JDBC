package rikkei.academy.service.category;

import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Category;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryServiceIMPL implements ICategoryService{
    private Connection connection = ConnectMySQL.getConnection();
    @Override
    public List<Category> findAll() {
        List<Category> categoryList = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call ShowListCategory()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setIdCategory(rs.getInt("idCategory"));
                category.setNameCategory(rs.getString("nameCategory"));
                categoryList.add(category);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoryList;
    }


    @Override
    public void save(Category category) {
        if(findById(category.getIdCategory()) == null) {
            // create
            System.out.println("CREATE CATEGORY !");
            try {
                CallableStatement callSt = connection.prepareCall("{call CreateCategory(?)}");
                callSt.setString(1, category.getNameCategory());
                callSt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            // update
            System.out.println("UPDATE CATEGORY");
            try {
                CallableStatement callSt = connection.prepareCall("{call UpdateCategory(?,?)}");
                callSt.setInt(1, category.getIdCategory());
                callSt.setString(2, category.getNameCategory());
                callSt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(int id) {
        try {
            CallableStatement callSt = connection.prepareCall("{call DeleteCategory(?)}");
            callSt.setInt(1, id);
            callSt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category findById(int id) {
        Category category = null;
        try {
            CallableStatement callSt = connection.prepareCall("{call FindCategoryById(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setIdCategory(rs.getInt(1));
                category.setNameCategory(rs.getString(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    @Override
    public List<Category> findByName(String name) {
        List<Category> categoryList = new ArrayList<>();
        Category category;
        try {
            CallableStatement callSt = connection.prepareCall("{call FindCategoryByName(?)}");
            callSt.setString(1, '%' + name + '%');
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                category = new Category();
                category.setIdCategory(rs.getInt(1));
                category.setNameCategory(rs.getString(2));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryList;
    }

}
