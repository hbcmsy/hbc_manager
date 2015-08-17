package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import common.SqlHelper;
import model.Order;

public class OrderDao {
	public Order getOrder(int ID) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = SqlHelper.connect();
			String sql = "select * from hbc_order where order_ID=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, ID);
			rs = ps.executeQuery();
			Order data = new Order();
			if (rs.next()) {
				data.setOrder_ID(ID);
				data.setOrder_date(rs.getTimestamp(2));
				data.setOrder_create_date(rs.getTimestamp(3));
				data.setClient_name(rs.getString(4));
				data.setClient_No(rs.getInt(5));
				data.setClient_class_No(rs.getInt(6));
				data.setClient_bus_No(rs.getInt(7));
				data.setOrder_creator(rs.getString(8));
				data.setOrder_refer(rs.getInt(9));
				data.setOrder_ordered(rs.getInt(10));
				data.setOrder_etc(rs.getString(11));
			}
			return data;
		} catch (SQLException e) {
			// 
			e.printStackTrace();
		} finally {
			SqlHelper.closeResult(rs);
			SqlHelper.closePreparedStatement(ps);
			SqlHelper.closeConneciton(con);
		}
		return null;
	}

	public List<Order> getOrders(Timestamp f,Timestamp l) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = SqlHelper.connect();
			String sql = "select * from hbc_order where order_date between ? and ?";
			ps = con.prepareStatement(sql);
			ps.setTimestamp(1, f);
			ps.setTimestamp(2, l);
			rs = ps.executeQuery();
			List<Order> list = new ArrayList<Order>();
			while (rs.next()) {
				Order data = new Order();
				data.setOrder_ID(rs.getInt(1));
				data.setOrder_date(rs.getTimestamp(2));
				data.setOrder_create_date(rs.getTimestamp(3));
				data.setClient_name(rs.getString(4));
				data.setClient_No(rs.getInt(5));
				data.setClient_class_No(rs.getInt(6));
				data.setClient_bus_No(rs.getInt(7));
				data.setOrder_creator(rs.getString(8));
				data.setOrder_refer(rs.getInt(9));
				data.setOrder_ordered(rs.getInt(10));
				data.setOrder_etc(rs.getString(11));
				list.add(data);
			}
			return list;
		} catch (SQLException e) {
			// 
			e.printStackTrace();
		} finally {
			SqlHelper.closeResult(rs);
			SqlHelper.closePreparedStatement(ps);
			SqlHelper.closeConneciton(con);
		}
		return null;
		// 参数修改 按照月获取
	}

	public int addOrder(Order order) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = SqlHelper.connect();
            String sql ="INSERT INTO hbc_order(order_date,order_create_date,client_name,client_No,"
            		+"client_class_No,client_bus_No,order_creator,order_refer,order_ordered,order_etc)"
            		+"VALUES(?,?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);					//建立预处理,设置返回ID
            ps.setTimestamp(1, order.getOrder_date());
            ps.setTimestamp(2, order.getOrder_create_date());
            ps.setString(3, order.getClient_name());
            ps.setInt(4, order.getClient_No());
            ps.setInt(5, order.getClient_class_No());
            ps.setInt(6, order.getClient_bus_No());
            ps.setString(7, order.getOrder_creator());
            ps.setInt(8, order.getOrder_refer());
            ps.setInt(9,order.getOrder_ordered());
            ps.setString(10, order.getOrder_etc());
            
            ps.executeUpdate();
            int id = -1;																	//初始化返回的article_ID,
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
			// 
			e.printStackTrace();
			return -1;
		}finally {																			//释放资源
            SqlHelper.closeResult(rs);
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);  
        }
		// 返回ID 失败返回-1\
        
	}

	public boolean deleteOrder(int ID) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = SqlHelper.connect();
            String sql ="DELETE form hbc_order where order_ID = ?";
            ps = con.prepareStatement(sql);	
            ps.setInt(1,ID);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
			// 
			e.printStackTrace();
			return false;
		}finally {																			//释放资源
            SqlHelper.closeResult(rs);
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);  
        }
	}

	public boolean editeOrder(Order order) {
		Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = SqlHelper.connect();
            String sql ="UPDATE hbc_order SET order_date = ? , order_create_date = ? ,"
            		+"client_name = ? , client_No = ? , client_class_No = ? , order_creator = ? ,"
            		+"order_refer = ? , order_ordered = ? , order_etc = ? where order_ID = ?";
            		
            ps = con.prepareStatement(sql);					//建立预处理,设置返回ID
            ps.setTimestamp(1, order.getOrder_date());
            ps.setTimestamp(2, order.getOrder_create_date());
            ps.setString(3, order.getClient_name());
            ps.setInt(4, order.getClient_No());
            ps.setInt(5, order.getClient_class_No());
            ps.setString(6, order.getOrder_creator());
            ps.setInt(7, order.getOrder_refer());
            ps.setInt(8,order.getOrder_ordered());
            ps.setString(9, order.getOrder_etc());
            
            ps.setInt(10, order.getOrder_ID());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
			// 
			e.printStackTrace();
			return false;
		}finally {																			//释放资源
            SqlHelper.closeResult(rs);
            SqlHelper.closePreparedStatement(ps);
            SqlHelper.closeConneciton(con);  
        }
		// 返回ID 失败返回-1\
	}

}
