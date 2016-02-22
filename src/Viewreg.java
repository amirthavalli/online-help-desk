

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Viewreg
 */
@WebServlet("/Viewreg")
public class Viewreg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
			ps = conn.prepareStatement("select * from usrinfo");
			//HttpSession session = request.getSession(true);
			//String uname = (String)session.getAttribute("uname");
			//ps.setString(1, uname);
			rs = ps.executeQuery();
	        out.println("<br>"+"<br>"+"<table border='5'");
            out.println("<tr>");
            out.println("<th>" + "Username" + "</th>");
            out.println("<th>" + "email" + "</th>");
            out.println("<th>" + "phone number" + "</th>");
            //out.println("<th>" + "Remarks" + "</th>");
            out.println("</tr>");
			while(rs.next())
			{
				 //out.println("<h2>welcome " +" " + uname +"</h2>");
         
                 out.println("<tr>");
                 out.println("<td>" + rs.getString("uname") + "</td>");
                 out.println("<td>" + rs.getString("email") + "</td>");
                 out.println("<td>" + rs.getString("phno") + "</td>");
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
