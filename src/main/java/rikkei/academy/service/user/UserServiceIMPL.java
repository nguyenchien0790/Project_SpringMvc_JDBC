package rikkei.academy.service.user;

import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Product;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;
import rikkei.academy.service.role.RoleServiceIMPL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserServiceIMPL implements IUserService{
    private Connection connection = ConnectMySQL.getConnection();
    RoleServiceIMPL roleServiceIMPL = new RoleServiceIMPL();
//    private static String SQL_CREATE_USER = "{call CreateUser(?,?,?,?,?,?,?,?,?)}";
    private String SQL_CREATE_USER = "insert into user (name, username, avatar, email, password, confirmpassword, phone, address, status) " +
        "values (?,?,?,?,?,?,?,?,?)";
    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall("{call ShowListUser()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("username");
                String avatar = rs.getString("avatar");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String confirmPassword = rs.getString("confirmPassword");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                boolean status = rs.getBoolean("status");
                Set<Role> roleSet = findRoleByUserId(id);
                userList.add(new User(id,name, username, avatar, email, password, confirmPassword, phone, address, status, roleSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  userList;

    }

    @Override
    public void save(User user) {
        if(findById(user.getId()) == null) {
            create(user);
        }
    }

    private void create(User user) {

        try {
//            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2,user.getUserName());
            ps.setString(3,user.getAvatar());
            ps.setString(4, user.getEmail());
            ps.setString(5,user.getPassword());
            ps.setString(6, user.getConfirmPassword());
            ps.setString(7,user.getPhone());
            ps.setString(8, user.getAddress());
            ps.setBoolean(9, user.isStatus());
            int row = ps.executeUpdate();
            if(row == 0) {
                connection.rollback();
                return;
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
            Role role = roleServiceIMPL.findByName(RoleName.USER);
            roleServiceIMPL.createRoleUser(user,role);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public User findById(int id) {
        User user = null;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call FindUserById(?)}");
            callableStatement.setInt(1,id);
            ResultSet resultSet = callableStatement.executeQuery();;
            while(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setConfirmPassword(resultSet.getString("confirmPassword"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setStatus(resultSet.getBoolean("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findByName(String name) {
        return null;
    }

    @Override
    public boolean existedByUserName(String username) {
        return false;
    }

    @Override
    public boolean existedByEmail(String email) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where email = ?");
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    @Override
    public User checkLogin(User user) {
        try {
            CallableStatement callableStatement = connection.prepareCall("{call CheckLogin(?,?)}");
            callableStatement.setString(1, user.getEmail());
            callableStatement.setString(2, user.getPassword());
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserName(resultSet.getString("username"));
                user.setAvatar(resultSet.getString("avatar"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setConfirmPassword(resultSet.getString("confirmPassword"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setStatus(resultSet.getBoolean("status"));
            } else {
                return null;
            }
            Set<Role> userRoles = roleServiceIMPL.findRoleByUser(user);
            user.setRoleSet(userRoles);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public Set<Role> findRoleByUserId(int user_id) {
        return null;
    }

    @Override
    public void blockUser(User user) {

    }
}
