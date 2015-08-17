package action;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Order;
import model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;








import service.OrderService;

import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")

public class OrderAction  extends ActionSupport{
	int order_ID;//订单号
	Timestamp order_date;//日期
	Timestamp order_create_date;//订单生成时间
	String client_name;//客户名称
	int client_No;//客户人数
	int client_class_No;//客户班级数
	int client_bus_No;//客户车数
	String order_creator;//订单生成人
	int order_refer;//项目选项
	int order_ordered;//预约or签约  1 or 0 
	String order_etc;//备注
	
	int year;
	int month;
	
	String json;
	public String getOrder(){
		if(!isUser())
			;//return null; TODO debug
		JSONObject jsonObject = new JSONObject();
		Order order = new OrderService().getOrder(order_ID);
		
		jsonObject.put("order_ID",order.getOrder_ID());
		jsonObject.put("order_date",order.getOrder_date());
		jsonObject.put("order_create_date",order.getOrder_create_date());
		jsonObject.put("client_name",order.getClient_name());
		jsonObject.put("client_No",order.getClient_No());
		jsonObject.put("client_class_No",order.getClient_class_No());
		jsonObject.put("order_creator",order.getOrder_creator());
		jsonObject.put("order_refer",order.getOrder_refer());
		jsonObject.put("order_ordered",order.getOrder_ordered());
		jsonObject.put("order_etc",order.getOrder_etc());
		
    	json = jsonObject.toString();
		return "OrderJson";
	}
	public String getOrders(){
		if(!isUser())
			;//return null; TODO debug
		JSONArray jsonArray = new JSONArray();
		List<Order> list = new OrderService().getOrdersByMonth(year,month);
		//TODO 修改参数 service没有写好
		if(list==null)
			return null;
		for(Order order:list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("order_ID",order.getOrder_ID());
			jsonObject.put("order_date",order.getOrder_date());
			jsonObject.put("order_create_date",order.getOrder_create_date());
			jsonObject.put("client_name",order.getClient_name());
			jsonObject.put("client_No",order.getClient_No());
			jsonObject.put("client_class_No",order.getClient_class_No());
			jsonObject.put("order_creator",order.getOrder_creator());
			jsonObject.put("order_refer",order.getOrder_refer());
			jsonObject.put("order_ordered",order.getOrder_ordered());
			jsonObject.put("order_etc",order.getOrder_etc());
			jsonArray.add(jsonObject);
		}
    	json = jsonArray.toString();
    	return "OrderJson";
	}
	public String saveOrder(){
		/*
		 * 根据order_ID 值来判断是edit order还是create order 
		 */
		if(!isUser())
			;//return null   TODO debug 后修改次内容;
		Order order = new Order();
		
		order.setOrder_ID(order_ID);	
		order.setOrder_date(order_date);
		order.setOrder_create_date(new Timestamp(System.currentTimeMillis()));
		order.setClient_name(client_name);
		order.setClient_No(client_No);
		order.setClient_class_No(client_class_No);
		order.setClient_bus_No(client_bus_No);
		order.setOrder_creator(order_creator);
		order.setOrder_refer(order_refer);
		order.setOrder_ordered(order_ordered);
		order.setOrder_etc(order_etc);
		
		if(order_ID>=0){
			if(!new OrderService().editOrder(order))
				order_ID = -2;
		}
		else 
			order_ID = new OrderService().addOrder(order);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("order_ID",order_ID);
		json = jsonObject.toString();
		
		return "OrderJson";
		
		
	}
	boolean isUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		User user =(User)request.getSession().getAttribute("userInfo");		//获取用户名
		if(user == null)
			return false;
		else 
			return true;
	}
	public int getOrder_ID() {
		return order_ID;
	}
	public void setOrder_ID(int order_ID) {
		this.order_ID = order_ID;
	}
	public Timestamp getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}
	public Timestamp getOrder_create_date() {
		return order_create_date;
	}
	public void setOrder_create_date(Timestamp order_create_date) {
		this.order_create_date = order_create_date;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public int getClient_No() {
		return client_No;
	}
	public void setClient_No(int client_No) {
		this.client_No = client_No;
	}
	public int getClient_class_No() {
		return client_class_No;
	}
	public void setClient_class_No(int client_class_No) {
		this.client_class_No = client_class_No;
	}
	public int getClient_bus_No() {
		return client_bus_No;
	}
	public void setClient_bus_No(int client_bus_No) {
		this.client_bus_No = client_bus_No;
	}
	public String getOrder_creator() {
		return order_creator;
	}
	public void setOrder_creator(String order_creator) {
		this.order_creator = order_creator;
	}
	public int getOrder_refer() {
		return order_refer;
	}
	public void setOrder_refer(int order_refer) {
		this.order_refer = order_refer;
	}
	public int getOrder_ordered() {
		return order_ordered;
	}
	public void setOrder_ordered(int order_ordered) {
		this.order_ordered = order_ordered;
	}
	public String getOrder_etc() {
		return order_etc;
	}
	public void setOrder_etc(String order_etc) {
		this.order_etc = order_etc;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
}
