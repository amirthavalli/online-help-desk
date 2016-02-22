

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
 * Servlet implementation class Assign
 */
@WebServlet("/Assign")
public class Assign extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Connection conn1 = null;
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
			String fname = request.getParameter("fname");
			ps1 = conn.prepareStatement("select * from req where facility=?");
			ps1.setString(1, fname);
			rs1 = ps1.executeQuery();
			if(rs1.next())
			{
				out.println("facility description:" + rs1.getString("description"));
			}
			else
			{
				out.println("No records found!!");
			}
			ps1.close();
			rs1.close();
			ps = conn.prepareStatement("update req set assignee=?,status=? where facility=?");
			
			String aname = request.getParameter("aname");
			String status = new String("assigned");
			HttpSession session = request.getSession(true);
			session.setAttribute("aname", aname);
			session.setAttribute("fname", fname);
			ps.setString(1, aname);
			ps.setString(2, status);
			ps.setString(3, fname);
			
			r = ps.executeUpdate();
			if(r > 0)
			{
				  out.println("ASSIGNED successfully!!" + "<br>");
				  response.sendRedirect("Sendmail");
				  
			}
			else
			{
				out.println("Assign Failed!!");
				RequestDispatcher rd2=request.getRequestDispatcher("./assignreq.html");
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
