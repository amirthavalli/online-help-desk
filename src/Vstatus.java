

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Vstatus
 */
@WebServlet("/Vstatus")
public class Vstatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 **/
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String URL ="jdbc:mysql://localhost:3306/request";
			conn = DriverManager.getConnection(URL,"root","admin"); 
			ps = conn.prepareStatement("select status,description from req where facility = ?");
			String fname = request.getParameter("fname");
			//String paswd = request.getParameter("pswd");
			ps.setString(1, fname);
			//ps.setString(2, paswd);
			rs = ps.executeQuery();
			if(rs.next())
			{
				 out.println("<center>"+"<br>"+"<br>"+"<table border='5'");
		         out.println("<tr>");
		         out.println("<th>" + "facility" + "</th>");
		         out.println("<th>" + "description" + "</th>");
		         out.println("<th>" + "Status" + "</th>");
		         out.println("</tr>");
		         out.println("</tr>");
		         out.println("<td>" + fname + "</td>");
		         out.println("<td>" + rs.getString("description") + "</td>");
		         out.println("<td>" + rs.getString("status") + "</td>");
                 out.println("</tr>");
                 out.println("</table>");
                 out.println("</center>");
                 RequestDispatcher rd3 = request.getRequestDispatcher("./viewstatus.html");
 				 rd3.include(request,response);
			}
			else
			{
				out.println("NO SUCH FACILITY!!");
				RequestDispatcher rd3 = request.getRequestDispatcher("./viewstatus.html");
				rd3.include(request,response);
			}
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
