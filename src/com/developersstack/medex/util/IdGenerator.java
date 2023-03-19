package com.developersstack.medex.util;

import java.sql.*;

public class IdGenerator {
    public int generateId() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medex",
                    "root",
                    "1234"
            );
            String sql = "SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()) {
                return rst.getInt(1)+1;
            }
            return 1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
