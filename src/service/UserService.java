/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import common.Evn;
import dao.UserDao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 * 进行用户模型的增删改查的服务的实现
 * @author Administrator
 */
public class UserService {
    public boolean addUser(String name,String pwd,char permition){
        try {
            User user = new User();
            user.setUser_authority(permition);
            user.setUser_name(name);
            user.setUser_pwd(pwd);
            return new UserDao().addUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<User> getUserList(){
        try {
            return new UserDao().getUsers();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public User getUser(String name){
        try {
            return new UserDao().getUser(name);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public User getUser(int ID){
        try {
            return new UserDao().getUser(ID);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public boolean deleteUser(int ID){
        try {
            User user = new User();
            user.setUser_ID(ID);
            return new UserDao().deleteUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean deleteUser(User user){
        try {
            return new UserDao().deleteUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean changeUser(User user){
        try {
            return new UserDao().changeUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean changePermition(int ID,Evn.USER_AUTHORITY a){
        try {
            User user = new UserDao().getUser(ID);
            switch(a){
                case USER:
                    user.setUser_authority('u');
                    new UserDao().changeUser(user);
                    break;
                case EDITER:
                    user.setUser_authority('e');
                    new UserDao().changeUser(user);
                    break;
                case ADMIN:
                    user.setUser_authority('a');
                    new UserDao().changeUser(user);
                    break;
                default :
                    return false;
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean changeName(int ID,String name){
        try {
            User user = new UserDao().getUser(ID);
            user.setUser_name(name);
            return new UserDao().changeUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean changePwd(int ID,String pwd)
    {
        try {
            User user = new UserDao().getUser(ID);
            user.setUser_pwd(pwd);
            return new UserDao().changeUser(user);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
