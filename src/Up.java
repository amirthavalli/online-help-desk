

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
 * Servlet implementation class Up
 */
@WebServlet("/Up")
public class Up extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
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
			String URL = "jdbc:mysql://localhost:3306/request";
			conn = DriverManager.getConnection(URL, "root", "admin");
			ps = conn.prepareStatement("select * from req where assignee=? and facility=?");
			String fname = request.getParameter("fname");
			HttpSession session = request.getSession(true);
			String aname = (String)session.getAttribute("uname");
			String status = request.getParameter("status");
			
			ps.setString(1, aname);
			//ps.setString(2, status);
			ps.setString(2, fname);
			
			rs = ps.executeQuery();
			if(!rs.next())
			{
				 //out.println("<h2>welcome " +" " + uname +"</h2>");
				 //out.println("ASSIGNED successfully!!" + "<br>");
				out.println("You are not allowed to change the status of this facility ");
				RequestDispatcher rd = request.getRequestDispatcher("./upstatus.html");
				 rd.include(request,response);
				rs.close();
				ps.close();
				conn.close();
                 
			}
			
			
			else
			{
				ps1 = conn.prepareStatement("update req set status=? where facility=?");
				//ps1.setString(1, aname);
				ps1.setString(1, status);
				ps1.setString(2, fname);
				r = ps1.executeUpdate();
				if(r > 0)
				{
					out.println("Update successful!!");
					RequestDispatcher rd = request.getRequestDispatcher("./upstatus.html");
					 rd.include(request,response);
					
				}	
				else
				{
					out.println("Update unsuccessful!!");
					RequestDispatcher rd = request.getRequestDispatcher("./upstatus.html");
					 rd.include(request,response);
				}
				ps1.close();
			}
			
		}
		catch(Exception e)
		{
			out.println(e);
		}


	}

}
