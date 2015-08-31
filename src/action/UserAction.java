/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.opensymphony.xwork2.ActionSupport;



import javax.servlet.http.HttpServletRequest;

import model.User;
import net.sf.json.JSONObject;

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
	
	String json;
	
	UserService us = new UserService();
	
	/*
	user_ID	-1 没有此用户 
			-2密码错误
			-3已登出
	*/
	public  String login(){
		User user = us.getUser(username);
		JSONObject jsonObject = new JSONObject();
		if(user==null)
			return null;
		if(user.getUser_ID()==-1){
			jsonObject.put("user_ID",-1);
		}else {
			if(userpwd.equals(user.getUser_pwd())){
				HttpServletRequest request = ServletActionContext.getRequest();
				request.getSession().setAttribute("userInfo",user);
				user =(User)request.getSession().getAttribute("userInfo");
				jsonObject.put("user_ID",user.getUser_ID());
				jsonObject.put("user_name", user.getUser_name());
			}else{
				jsonObject.put("user_ID",-2);
			}
		}
		
		json = jsonObject.toString();
		return "UserJson";
	}
	public  String logOut(){
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().removeAttribute("userInfo");
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user_ID",-3);
		json = jsonObject.toString();
		return  "UserJson";
	}
	public  String show(){
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject jsonObject = new JSONObject();
		User user;
		if(request.getSession().getAttribute("userInfo") == null)
			jsonObject.put("user_ID",-3);
		else{
			user =(User)request.getSession().getAttribute("userInfo");
			jsonObject.put("user_ID",user.getUser_ID());
			jsonObject.put("user_name", user.getUser_name());
		}
		json = jsonObject.toString();
		return "UserJson";
	}
	/*public String list(){
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
*/
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
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

		
}
