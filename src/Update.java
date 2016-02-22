

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
 * Servlet implementation class Update
 */
@WebServlet("/Update")
public class Update extends HttpServlet {
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
			int r;
			Class.forName("com.mysql.jdbc.Driver");
			String URL = "jdbc:mysql://localhost:3306/request";
			conn = DriverManager.getConnection(URL, "root", "admin");
			ps = conn.prepareStatement("update req set assignee=?,status=? where facility=?");
			String fname = request.getParameter("fname");
			String aname = request.getParameter("aname");
			String status = request.getParameter("status");
			HttpSession session = request.getSession(true);
			session.setAttribute("aname", aname);
			ps.setString(1, aname);
			ps.setString(2, status);
			ps.setString(3, fname);
			
			r = ps.executeUpdate();
			if(r > 0)
			{
				 //out.println("<h2>welcome " +" " + uname +"</h2>");
				 out.println("Updated successfully!!" + "<br>");
				 RequestDispatcher rd2=request.getRequestDispatcher("./updatestatus.html");
	                rd2.include(request,response);
				 //response.sendRedirect("Sendmail");

			}
			else
			{
				out.println("Update Failed!!");
				RequestDispatcher rd2=request.getRequestDispatcher("./updatestatus.html");
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
