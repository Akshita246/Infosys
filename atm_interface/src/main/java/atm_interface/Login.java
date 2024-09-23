package atm_interface;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginForm")
public class Login extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name= req.getParameter("name");
		String cardNumber=req.getParameter("cardNumber");
		String pin=req.getParameter("pin");
		PrintWriter o=resp.getWriter();
		o.print("your data is collected " + name);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url ="jdbc:mysql://localhost:3306/jdbc_login";
			Connection con = DriverManager.getConnection(url,"root","akshitabhandari24@gmail.com");
			String insertquery ="INSERT INTO users(name,cardNumber,pin) VALUES(?,?,?)";
			PreparedStatement ps =con.prepareStatement(insertquery);
			ps.setString(1, name);
			ps.setString(2, cardNumber);
			ps.setString(3, pin);
			int database_result=ps.executeUpdate();
			if(database_result>0) {
				resp.sendRedirect("services.html");
			}
			else {
				o.print(" login unsuccessful");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("invalid");
		}
	}
	
}
