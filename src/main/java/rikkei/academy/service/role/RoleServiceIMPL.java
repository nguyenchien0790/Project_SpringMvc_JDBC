package rikkei.academy.service.role;

import rikkei.academy.config.ConnectMySQL;
import rikkei.academy.model.Category;
import rikkei.academy.model.Role;
import rikkei.academy.model.RoleName;
import rikkei.academy.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleServiceIMPL implements IRoleSerVice {

    private final Connection connection = ConnectMySQL.getConnection();

    @Override
    public Role findByName(RoleName name) {
        Role role = null;
        try {
            CallableStatement callSt = connection.prepareCall("{call FindRoleByName(?)}");
            callSt.setString(1, name.name());
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                role = new Role();
                role.setId(rs.getInt(1));
                role.setName(RoleName.valueOf(rs.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return role;
    }

    @Override
    public void createRoleUser(User user,Role role) {
        try {
            CallableStatement callableStatement =connection.prepareCall("{call CreateRoleUser(?,?)}");
            callableStatement.setInt(1,user.getId());
            callableStatement.setInt(2,role.getId());
            int row = callableStatement.executeUpdate();
//            if(row != 0) {
//                connection.commit();
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Role> findRoleByUser(User user) {
        Set<Role> roleSet = new HashSet<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call FindRoleByUser(?)}");
            callableStatement.setInt(1,user.getId());
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                int idRole = rs.getInt(1);
                String roleName = rs.getString(2);
                if(roleName.equals("ADMIN")) {
                    roleSet.add(new Role(idRole, RoleName.AMIN));
                } else if (roleName.equals("PM")) {
                    roleSet.add(new Role(idRole, RoleName.PM));
                } else {
                    roleSet.add(new Role(idRole, RoleName.USER));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return roleSet;
    }


}
