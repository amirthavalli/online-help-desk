

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
 * Servlet implementation class Newreq
 */
@WebServlet("/Newreq")
public class Newreq extends HttpServlet {
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
			String URL ="jdbc:mysql://localhost:3306/request";
			conn = DriverManager.getConnection(URL,"root","admin"); 
			ps = conn.prepareStatement("insert into req (requestor,facility,req_date,assignee,status,description) values(?,?,?,null,?,?)");
			HttpSession session = request.getSession(true);
			String status = new String("not assigned");
			String uname = (String)session.getAttribute("uname");
			//out.println("Username = " + uname);
			String facility = request.getParameter("fname");
			String desc = request.getParameter("desc");
			String date = request.getParameter("reqdate");
			
			//String paswd = request.getParameter("pswd");
			ps.setString(1, uname);
			ps.setString(2, facility);
			ps.setString(3, date);
			ps.setString(4, status);
			ps.setString(5, desc);
			//ps.setString(4, type);
			int r = ps.executeUpdate();
			if(r > 0)
			{
				out.println("Request created successfull!!");
				RequestDispatcher rd = request.getRequestDispatcher("./newrequest.html");
				rd.include(request,response);
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
