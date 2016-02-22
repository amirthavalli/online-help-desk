

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
 * Servlet implementation class SendMail
 */
@WebServlet("/Sendmail")
public class Sendmail extends HttpServlet {
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
				String URL = "jdbc:mysql://localhost:3306/user";
				conn = DriverManager.getConnection(URL, "root", "admin");
				ps = conn.prepareStatement("select * from usrinfo where uname=?");
				HttpSession session = request.getSession(true);
				String aname = (String)session.getAttribute("aname");
				String fname = (String)session.getAttribute("fname");
				ps.setString(1, aname);
				
				rs = ps.executeQuery();
				if(rs.next())
				{
				String to = rs.getString("email");
				
				 String subject=new String("Facility request");  
				 String msg=new String("You are assigned with a new facility request named "+fname+" by the facility head. Login the site to know more.");  
				          
				 Mailer.send(to, subject, msg); 
				 //out.println("value of to " + to);
				 out.print("message has been sent successfully");  
				 RequestDispatcher rd2=request.getRequestDispatcher("./assignreq.html");
		         rd2.include(request,response);
		         response.sendRedirect("Messagesend");
				 out.close();
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