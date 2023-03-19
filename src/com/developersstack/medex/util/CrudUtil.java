package com.developersstack.medex.util;

import com.developersstack.medex.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    // legacy code
    private static PreparedStatement execute(String sql, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstm.setObject((i + 1), params[i]);
        }
        return pstm;
    }

    public static boolean executeUpdate(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return execute(sql, params).executeUpdate() > 0;
    }

    public static ResultSet executeQuery(String sql, Object... params) throws SQLException, ClassNotFoundException {
        return execute(sql, params).executeQuery();
    }
}
