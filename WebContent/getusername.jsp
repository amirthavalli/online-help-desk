<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		String URL = "jdbc:mysql://localhost:3306/user";
		conn = DriverManager.getConnection(URL, "root", "alohomora");
		ps = conn.prepareStatement("select * from usrinfo where uname = ?");
		String uname = request.getParameter("q");
		ps.setString(1, uname);
		rs = ps.executeQuery();
		if(rs.next())
		{
			out.println("User name already exist!!");
		}
	}
	catch(Exception e)
	{
		out.println(e);
	}
	ps.close();
	conn.close();
  %>