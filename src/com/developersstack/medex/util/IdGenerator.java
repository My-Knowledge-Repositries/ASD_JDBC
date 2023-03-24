package com.developersstack.medex.util;

import com.developersstack.medex.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdGenerator {
    public String generateId(String sql, String prefix){
        try{
            ResultSet rst = CrudUtil.execute(sql);
            if (rst.next()){
                String tempId =  rst.getString(1);
                int id= Integer.parseInt(tempId.split("-")[1]); // -->
                id++;
                return prefix+"-"+id;
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return prefix+"-1";
    }
}
