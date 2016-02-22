

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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Pchange
 */
@WebServlet("/Pchange")
public class Pchange extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	//Connection conn1 = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try
		{
			int r;
			Class.forName("com.mysql.jdbc.Driver");
			String URL = "jdbc:mysql://localhost:3306/user";
			conn = DriverManager.getConnection(URL, "root", "admin");
			ps = conn.prepareStatement("update usrinfo set password=? where uname=?");
			String password = request.getParameter("cpswd");
			
			HttpSession session = request.getSession(true);
			String uname = (String)session.getAttribute("uname");
			ps.setString(1, password);
			ps.setString(2, uname);
			
			
			r = ps.executeUpdate();
			if(r > 0)
			{
				 //out.println("<h2>welcome " +" " + uname +"</h2>");
				 out.println("PASSWORD UPADATED successfully!!" + "<br>");
				 RequestDispatcher rd2=request.getRequestDispatcher("./passchange.html");
	                rd2.include(request,response);
			}
			else
			{
				out.println("Update Failed!!");
				RequestDispatcher rd2=request.getRequestDispatcher("./passchange.html");
                rd2.include(request,response);
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch(Exception e)
		{
			//out.println(e);
		}
	}
}
