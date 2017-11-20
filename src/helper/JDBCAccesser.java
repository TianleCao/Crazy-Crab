package helper;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class JDBCAccesser {
	public class Person{
		public String name;
		public int time;
		public Person(String name,int time){
			this.name=name;
			this.time=time;
		}
	};
	ArrayList<Person> person=new ArrayList<Person>();
	Connection con=null;
	ResultSet rs=null;
	public JDBCAccesser() throws ClassNotFoundException, SQLException{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con=DriverManager.getConnection("jdbc:odbc:UIAL", "Crazy", "Crab");
	}
	public String GetUserCode(String Value) throws SQLException{
		String se="SELECT * FROM User WHERE NickName=?";
		PreparedStatement pstmt=con.prepareStatement(se);
		pstmt.setString(1,Value);
		ResultSet rs=pstmt.executeQuery();
		rs.next();
		String code=rs.getString("PassWord");
		pstmt.close();
		return code;
	}
	public int GetUserTime(String Value) throws SQLException{
		String se="SELECT * FROM User WHERE NickName=?";
		PreparedStatement pstmt=con.prepareStatement(se);
		pstmt.setString(1,Value);
		ResultSet rs=pstmt.executeQuery();
		rs.next();
		int time=rs.getInt("Time");
		pstmt.close();
		return time;
	}
	public void AddUserTime(String Value,long time)throws SQLException{
		String se="SELECT * FROM User WHERE NickName=?";
		PreparedStatement pstmt=con.prepareStatement(se);
		pstmt.setString(1,Value);
		ResultSet rs=pstmt.executeQuery();
		rs.next();
		int nowtime=rs.getInt("Time")+(int)(time/60000);
		pstmt.close();
		se="Update User SET [Time]=? where NickName=?";
		PreparedStatement pstmt1=con.prepareStatement(se);
		pstmt1=con.prepareStatement(se);
		pstmt1.setInt(1, nowtime);
		pstmt1.setString(2,Value);
		pstmt1.executeUpdate();
		pstmt1.close();
	}
	public Date GetRegDate(String Value) throws SQLException{
		String se="SELECT * FROM User WHERE NickName=?";
		PreparedStatement pstmt=con.prepareStatement(se);
		pstmt.setString(1,Value);
		ResultSet rs=pstmt.executeQuery();
		rs.next();
		Date date=rs.getDate("RegDate");
		pstmt.close();
		return date;
	}
	public void AddUser(String Name,String Code,Date date)throws SQLException{
		int time=0;
		String SQLOrder;
		Statement stmt=con.createStatement();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SQLOrder = "INSERT INTO User Values("+"'"+Name+"','"+Code+"',"+time+","+sd.format(date)+")";
		stmt.executeUpdate(SQLOrder);
		stmt.close();
	}
	public ArrayList<Person> GetSorted() throws SQLException{
		String SQLOrder;
		Statement stmt=con.createStatement();
		SQLOrder = "SELECT * FROM User ORDER BY time DESC";
		ResultSet rs=stmt.executeQuery(SQLOrder);
		int m=0;
		while ((rs.next())&&(m<5)){
			person.add(new Person(rs.getString("NickName"),rs.getInt("Time")));
			m++;
		}
		stmt.close();
		return person;
	}
	public void Close() throws SQLException{
		con.close();
	}
}
