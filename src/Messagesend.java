

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Messagesend")
public class Messagesend extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //String api_id = "httpapiid";
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
					String user = new String("user");
			        String pass = new String("password");
			        String name = new String("name");
			        String to = "91"+rs.getString("phno");
			        //out.println("value of to " + to);

			        URL url = new URL("http://bulksms.mysmsmantra.com:8080/WebSMS/SMSAPI.jsp?" +
			                "username=" + user +
			                "&password=" + pass +
			                "&sendername=" + name +
			                "&mobileno=" + to +
			                "&message=" + "Your request " + fname + " status has been updated. Visit the site for further details" );
			        //out.println("url"+url);
			        out.println("<a href='" + url +"'>"+"send message"+"</a>");
			        HttpURLConnection conn;
			        BufferedReader br;
			        String line;
			        String result = null;
			        try {
			            conn = (HttpURLConnection) url.openConnection();
			            conn.setRequestMethod("GET");
			            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			            while ((line = br.readLine()) != null) {
			                result += line;
			            }
			            br.close();
			        } catch (Exception e) {
			            e.printStackTrace();
			        }

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