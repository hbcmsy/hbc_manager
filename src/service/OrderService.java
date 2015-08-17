package service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import dao.OrderDao;
import model.Order;

public class OrderService {
	public Order getOrder(int ID){
		return new OrderDao().getOrder(ID);
	} 
	public List<Order> getOrdersByMonth(int year,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month-1, 1, 0, 0);//获取指定月1日时间
		Timestamp f = new Timestamp(calendar.getTimeInMillis());
		calendar.clear();
		int big[] = {1,3,5,7,8,10,12};
	 	int smail[] = {3,6,9,10,11};
	 	if(month==2&&((year%4)!=0))
	 		if(year%100 == 0 && year%400 == 0)
	 			calendar.set(year, month-1,29, 23, 59);
	 		else 
	 			calendar.set(year, month-1,28, 23, 59);
	 	else
	 		calendar.set(year, month-1,29, 23, 59);
	 	
	 	for(int i = 0;i<big.length;i++)
			if(big[i] == month)
				calendar.set(year, month-1, 31,23,59);
	 	for(int i = 0;i<smail.length;i++)
			if(smail[i] == month)
				calendar.set(year, month-1, 30, 23, 59);
		Timestamp l = new Timestamp(calendar.getTimeInMillis());
		return new OrderDao().getOrders(f, l);
	}
	public List<Order> getOrdersByYear(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year,0, 0, 0, 0);//获取指定月1日时间
		Timestamp f = new Timestamp(calendar.getTimeInMillis());
		calendar.clear();	
		calendar.set(year, 11, 31, 23, 59);
		Timestamp l = new Timestamp(calendar.getTimeInMillis());
		return new OrderDao().getOrders(f, l);
	}
	public List<Order> getOrders(){
		return null;
	}
	public int addOrder(Order order){
		return new OrderDao().addOrder(order);
	} 
	public boolean deleteOrder(int ID){
		return new OrderDao().deleteOrder(ID);
	}
	public boolean editOrder(Order order){
		return new OrderDao().editeOrder(order);
	}
	
	
	
}
