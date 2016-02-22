

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Weelkyreport
 */
@WebServlet("/Weelkyreport")
public class Weelkyreport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String URL = "jdbc:mysql://localhost:3306/request";
			conn = DriverManager.getConnection(URL, "root", "admin");
			ps = conn.prepareStatement("select * from req where req_date between ? and ?");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			out.println("Current Date : " + dateFormat.format(cal.getTime()));
			ps.setString(2, dateFormat.format(cal.getTime()));
			cal.add(Calendar.DATE, -7);
			ps.setString(1, dateFormat.format(cal.getTime()));
			rs = ps.executeQuery();
	        out.println("<br>"+"<br>"+"<table border='5'");
            out.println("<tr>");
            out.println("<th>" + "Request Id" + "</th>");
            out.println("<th>" + "Requestor" + "</th>");
            out.println("<th>" + "facility" + "</th>");
            out.println("<th>" + "Request date" + "</th>");
            out.println("<th>" + "Assignee" + "</th>");
            out.println("<th>" + "Status" + "</th>");
            out.println("<th>" + "Reason" + "</th>");
            out.println("<th>" + "Description" + "</th>");
            //out.println("<th>" + "Remarks" + "</th>");
            out.println("</tr>");
			while(rs.next())
			{
				 //out.println("<h2>welcome " +" " + uname +"</h2>");
         
                 out.println("<tr>");
                 out.println("<td>" + rs.getString("req_id") + "</td>");
                 out.println("<td>" + rs.getString("requestor") + "</td>");
                 out.println("<td>" + rs.getString("facility") + "</td>");
                 out.println("<td>" + rs.getString("req_date") + "</td>");
                 out.println("<td>" + rs.getString("assignee") + "</td>");
                 out.println("<td>" + rs.getString("status") + "</td>");
                 out.println("<td>" + rs.getString("reason") + "</td>");
                 out.println("<td>" + rs.getString("description") + "</td>");
                 //out.println("<td>" + rs.getString("remarks") + "</td>");
                 out.println("</tr>");
                		
			}
			 out.println("</table>");
             
			 //RequestDispatcher rd = request.getRequestDispatcher("./view.html");
			 //rd.include(request,response);
			
			rs.close();
			ps.close();
			conn.close();
		}
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
