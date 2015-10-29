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
	public List<Order> getOrdersByDate(int year,int month,int date){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month-1, date, 0, 0);//获取指定月1日时间
		Timestamp f = new Timestamp(calendar.getTimeInMillis());
		calendar.clear();		
		calendar.set(year, month-1, date, 23, 59);
		Timestamp l = new Timestamp(calendar.getTimeInMillis());
		return new OrderDao().getOrders(f, l);
	}
	public List<Order> getOrdersByDay(int year,int month,int day){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month-1, 7, 0, 0);//获取指定月1日时间
		int true_day = (calendar.get(Calendar.DAY_OF_WEEK)+5)%7;//calendar获取的星期数是以周日为首的,为了获取以周一为首的,所以进行处理. 处理后.周一为零,以此类推
		int date = (day-1)*7-true_day;
		int date_end = date+6;
		
	 	//Reset date_end
 		int temp = this.getMonthDate(year, month);
 		if(date_end > temp)
 			date_end = temp;
		if(date>date_end)
			return null;
		if(date<1)
			date = 1;
		
		calendar.set(year, month-1, date, 0, 0);
		Timestamp f = new Timestamp(calendar.getTimeInMillis());
		calendar.set(year, month-1, date_end, 23, 59);
		Timestamp l = new Timestamp(calendar.getTimeInMillis());
		calendar.clear();
		return new OrderDao().getOrders(f, l);
	}
	public List<Order> getOrdersByMonth(int year,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month-1, 1, 0, 0);//获取指定月1日时间
		Timestamp f = new Timestamp(calendar.getTimeInMillis());
		calendar.clear();
		calendar.set(year, month-1,this.getMonthDate(year, month), 23, 59);
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
	public List<Order> getOrders(int year,int month,int date,int end_year,int end_month,int end_date){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year,month-1,date, 0, 0);//获取指定月1日时间
		Timestamp f = new Timestamp(calendar.getTimeInMillis());
		calendar.clear();	
		calendar.set(end_year,end_month-1,end_date, 23, 59);
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
	private int getFebDate(int year) {
		int date_end = 29;
 		if((year%4) != 0){
			date_end = 28;
 		}
		if(year%100 == 0 && year%400 != 0){
			date_end = 28;
		}
		return date_end;
	}
	private int getMonthDate(int year,int month) {
		int big[] = {1,3,5,7,8,10,12};
	 	int smail[] = {4,6,9,11};
	 	if(month==2){
	 		return this.getFebDate(year);
 		}
	 	for(int i = 0;i<big.length;i++)
			if(big[i] == month)
				return 31;
	 	for(int i = 0;i<smail.length;i++)
			if(smail[i] == month)
				return 30;
	 	return -1;
	}
	
}
