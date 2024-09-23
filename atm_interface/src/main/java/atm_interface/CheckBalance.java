package atm_interface;

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

@WebServlet("/check_balance")
public class CheckBalance extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNumber = req.getParameter("accountNumber");
        PrintWriter o = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jdbc_login";
            Connection con = DriverManager.getConnection(url, "root", "akshitabhandari24@gmail.com");

            String checkBalanceQuery = "SELECT balance FROM users WHERE cardNumber=?";
            PreparedStatement ps = con.prepareStatement(checkBalanceQuery);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                o.print("Current Balance: " + balance);
            } else {
                o.print("Account not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            o.print("Error: " + e.getMessage());
        }
    }
}
