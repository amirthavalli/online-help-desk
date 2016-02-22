



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
 * Servlet implementation class Verify
 */
@WebServlet("/Verify")
public class Verify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
			String URL ="jdbc:mysql://localhost:3306/user";
			conn = DriverManager.getConnection(URL,"root","admin"); 
			ps = conn.prepareStatement("select * from usrinfo where uname = ? and password=?");
			String uname = request.getParameter("uname");
			String paswd = request.getParameter("pswd");
			
			ps.setString(1, uname);
			ps.setString(2, paswd);
			rs = ps.executeQuery();
			if(rs.next())
			{
				String type = rs.getString("typeofuser");
				HttpSession session = request.getSession(true);
				session.setAttribute("uname", uname);
				
		        if(type.equals("admin"))
		        {
				RequestDispatcher rd1 = request.getRequestDispatcher("./fadminhome.html");
				rd1.include(request,response);
		        }
		        else 
		        {
		        	RequestDispatcher rd = request.getRequestDispatcher("./studenthome.html");
					rd.include(request,response);
		        }
					
			}
			else
			{
				out.println("INVALID USERNAME OR PASSWORD!!");
				RequestDispatcher rd3 = request.getRequestDispatcher("./form1.html");
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
