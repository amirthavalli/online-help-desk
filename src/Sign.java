



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
 * Servlet implementation class Sign
 */
@WebServlet("/Sign")
public class Sign extends HttpServlet {
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
			ps = conn.prepareStatement("insert into usrinfo values(?,?,?,?,?)");
			String uname = request.getParameter("uname");
			String type = new String("na");
			String email = request.getParameter("email");
			String paswd = request.getParameter("pswd");
			String phno = request.getParameter("phno");
			ps.setString(1, uname);
			ps.setString(2, email);
			ps.setString(3, paswd);
			ps.setString(4, type);
			ps.setString(5, phno);
			ps.executeUpdate();
			HttpSession session = request.getSession(true);
			session.setAttribute("uname", uname);
			RequestDispatcher rd = request.getRequestDispatcher("./studenthome.html");
			rd.include(request,response);
			
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
