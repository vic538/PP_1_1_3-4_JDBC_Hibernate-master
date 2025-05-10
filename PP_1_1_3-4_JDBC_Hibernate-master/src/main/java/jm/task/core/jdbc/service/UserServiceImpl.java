package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        String sql = "create table if not exists mavi(id bigint primary key auto_increment, name varchar(50), lastName varchar(50), age tinyint)" ;
        try (Connection conn = Util.getConnection();
        Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS MAVI";
        try (Connection con = Util.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into mavi (name, lastName, age) values (?, ?, ?)";

        try (Connection conn = Util.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setString(2,lastName);
            ps.setByte(3,age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String sql = "delete from mavi where id="+id;
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM mavi";
        List<User> list = new ArrayList<>();

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public void cleanUsersTable() {
        String sql = "delete from mavi";
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
