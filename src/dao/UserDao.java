/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import common.SqlHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 * 进行用户的增删改查过程
 * @author Administrator
 */
public class UserDao {
    public User getUser(int ID) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = SqlHelper.connect();
            String sql = "select * from hbc_user where user_ID=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, ID);
            rs = ps.executeQuery();
            User data = new User();
            if(rs.next()){
                data.setUser_ID(rs.getInt(1));
                data.setUser_authority(rs.getString(2).charAt(0));
                data.setUser_pwd(rs.getString(3));
                data.setUser_name(rs.getString(4));
            }
            return data;
        }finally {
            SqlHelper.closeResult(rs);
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);  
	}
    }
    public User getUser(String name) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = SqlHelper.connect();
            String sql = "select * from hbc_user where user_name = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,name);
            rs = ps.executeQuery();
            User data = new User();
            if(rs.next()){
                data.setUser_ID(rs.getInt(1));
                data.setUser_authority(rs.getString("user_authority").charAt(0));
                data.setUser_pwd(rs.getString(3));
                data.setUser_name(rs.getString(4));            
            }
            return data;
        }finally{
            SqlHelper.closeResult(rs);
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);
        }
        
    }
    public List<User> getUsers() throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = SqlHelper.connect();
            String sql = "selete * from hbc_user";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while(rs.next()){
                User data = new User();
                data.setUser_ID(rs.getInt(1));
                data.setUser_authority(rs.getString(2).charAt(1));
                data.setUser_pwd(rs.getString(3));
                data.setUser_name(rs.getString(4));            
                list.add(data);
            }
            return list;
        }finally{
            SqlHelper.closeResult(rs);
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);
        }
    }
    
    public boolean changeUser(User user) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = SqlHelper.connect();
            String sql = "update hbc_user set user_authority = ? ,user_pwd = ? ,user_name = ? where user_ID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1,user.getUser_authority()+new String());
            ps.setString(2,user.getUser_pwd());
            ps.setString(3,user.getUser_name());
            ps.setInt(4,user.getUser_ID());
            ps.executeUpdate();
            return true;
        }finally{
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);
        }
    }
    public boolean deleteUser(User user) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = SqlHelper.connect();
            String sql = "delete frome  hbc_user where user_ID = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,user.getUser_ID());
            ps.executeUpdate();
            return true;
        }finally{
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);
        }
    }
    
    public boolean addUser(User user) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try{
            con = SqlHelper.connect();
            String sql = "insert into hbc_user (user_authority,user_pwd,user_name)value(?,?,?)";
            ps = con.prepareStatement(sql);
            //ps.setInt(1,user.getUser_ID());
            ps.setString(1,user.getUser_authority()+new String());
            ps.setString(2,user.getUser_pwd());
            ps.setString(3,user.getUser_name());
            ps.executeUpdate();
            return true;
        }finally{
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);
        }
    }
}
