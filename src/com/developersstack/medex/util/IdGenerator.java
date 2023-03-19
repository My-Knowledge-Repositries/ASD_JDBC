package com.developersstack.medex.util;

import com.developersstack.medex.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdGenerator {
    public int generateId() {
        try {
            String sql = "SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1";
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()) {
                return rst.getInt(1) + 1;
            }
            return 1;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
