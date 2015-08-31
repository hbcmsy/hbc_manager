/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Administrator
 */
public class User {
    int user_ID;
    String user_name;
    String user_pwd;
    char user_authority;
    public User() {
    	user_ID = -1;
    	user_pwd = null;
    	user_name = null;
    	
	}
    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public char getUser_authority() {
        return user_authority;
    }

    public void setUser_authority(char user_authority) {
        this.user_authority = user_authority;
    }
    
}
