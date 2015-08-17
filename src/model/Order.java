package model;


import java.sql.Timestamp;

/*			
		CREATE TABLE `hbc_order` (
		  `order_ID` int(11) NOT NULL AUTO_INCREMENT,
		  `order_date` timestamp NOT NULL,
		  `order_create_date` timestamp NOT NULL,
		  `client_name` varchar(200) NOT NULL,
		  `client_No` int NOT  NULL,
		  `client_class_No` int NOT NULL,
		  `order_creator` varchar(30) NOT NULL,
		  `order_refer` int NOT NULL,
		  `order_ordered` int NOT NULL,
		  `order_etc` varchar(1000),
		  PRIMARY KEY (`order_ID`) 
		) 
 */

/*			order_ID
			order_date
			order_create_date
			client_name
			client_No
			client_class_No
			client_bus_No
			order_creator
			order_refer
			order_ordered
			order_etc
*/

public class Order {
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
	
}

