/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.opensymphony.xwork2.ActionSupport;

import common.Evn;
import common.PageList;
import common.UrlString;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.User;

import org.apache.struts2.ServletActionContext;

import service.UserService;

/**
 *
 * @author yyf
 */
@SuppressWarnings("serial")
public class UserAction  extends ActionSupport{
	String username="";
	String userpwd="";
	User user;
	//char userauthority;
    //int user_ID;
        
	String userManage = " <a href="+UrlString.UserAction+"!list.action  target=\"blank\">管理</a>";
	String str = "<a  href=\""+UrlString.RootPath+"/manager/userLogin.jsp\" >登录</a><a  href=\""+UrlString.RootPath+"/hbc/manager/userAdd.jsp\" >注册</a>";
	
	PageList<User> plist;
	String operID;
	int pageNo = 1;
	UserService us = new UserService();
	public  String login(){
		user = us.getUser(username);
		if(user==null){
			str = "用户不存在";
			return "showUser";
		}
		if(userpwd.equals(user.getUser_pwd())){
			str = username+ "<a href="+UrlString.RootPath+"/Action/UserAction!logOut.action>登出</a>";
			HttpServletRequest request = ServletActionContext.getRequest();
			request.getSession().setAttribute("userInfo",user);
			if(isAuthor())
				str +=userManage;
		}else{
			str = "密码错误";
		}
		return "showUser";
	}
	public  String logOut(){
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().removeAttribute("userInfo");
		return  "showUser";
	}
	public  String show(){
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getSession().getAttribute("userInfo") == null)
			str = "<a  href="+UrlString.RootPath+"/manager/userLogin.jsp>登录</a><a  href="+UrlString.RootPath+"/manager/userAdd.jsp >注册</a>";
		else{
			user =(User)request.getSession().getAttribute("userInfo");
			str = user.getUser_name()+ "<a href="+UrlString.RootPath+"/Action/UserAction!logOut.action>登出<a>";
		}
		if(isAuthor())
			str +=userManage;
		return "showUser";
	}
	public String list(){
		HttpServletRequest request = ServletActionContext.getRequest();
		user =(User)request.getSession().getAttribute("userInfo");
                List<User> userList; 
                if(isAuthor()){
			userList = us.getUserList();
			plist = new PageList<>(userList,userList.size(),30,pageNo,"");
			return "showUserList";
		}
		str += "不知道这是什么" ;
		return "showUser";
	}
	public String deleteUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		user =(User)request.getSession().getAttribute("userInfo");
		List<User> userList; 
                if(isAuthor()){
			us.deleteUser(Integer.valueOf(operID));
			userList = us.getUserList();
			plist = new PageList<>(userList,userList.size(),30,pageNo,"");
			return "showUserList";
		}
		str += "这个是什么？" ;
		return "showUserList";
	}
         public String promotUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		user =(User)request.getSession().getAttribute("userInfo");
		if(isAuthor()){
			us.changePermition(Integer.valueOf(operID),Evn.USER_AUTHORITY.EDITER);
			 return "showUserList";
		}
		str += "不知道。。。。。。。" ;
		return "showUser";
	}
	public String changePwd(){
		//还没写
		return "changePwd";
	}
	public String addUser(){
		if(!username.isEmpty()){
			if(!us.addUser(username, userpwd, 'e')){
				str = username + "已经存在";
				return "showUser";
			}	
			HttpServletRequest request = ServletActionContext.getRequest();
                        user = us.getUser(username);
			request.getSession().setAttribute("userInfo", user);
		}
		str = username+ "<a href="+UrlString.RootPath+"/Action/UserAction!logOut.action>登出</a>";
		return "showUser";
	}
	public boolean isAuthor(){
            if(user==null)
                return false;
            switch(user.getUser_authority())
            {
                case 'u':
                    return false;
                case 'e':
                    return false;
                case 'a':
                    return true;
                default:
                    return false;
            }
	}

	public String getOperID() {
		return operID;
	}

	public void setOperID(String operID) {
		this.operID = operID;
	}
		
	public PageList<User> getPlist() {
		return plist;
	}

	public void setPlist(PageList<User> plist) {
		this.plist = plist;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}
	
}
