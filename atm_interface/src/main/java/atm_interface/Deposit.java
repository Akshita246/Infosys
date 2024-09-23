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

@WebServlet("/deposit")
public class Deposit extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNumber = req.getParameter("accountNumber");
        String depositAmountStr = req.getParameter("depositAmount");
        PrintWriter o = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jdbc_login";
            Connection con = DriverManager.getConnection(url, "root", "akshitabhandari24@gmail.com");

            // Validate the account number and get the current balance
            String checkAccountQuery = "SELECT balance FROM users WHERE cardNumber = ?";
            PreparedStatement ps = con.prepareStatement(checkAccountQuery);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double depositAmount = Double.parseDouble(depositAmountStr);

                // Update the balance with the deposited amount
                String updateBalanceQuery = "UPDATE users SET balance = balance + ? WHERE cardNumber = ?";
                PreparedStatement psUpdate = con.prepareStatement(updateBalanceQuery);
                psUpdate.setDouble(1, depositAmount);
                psUpdate.setString(2, accountNumber);
                int result = psUpdate.executeUpdate();

                if (result > 0) {
                    // Insert into transaction history
                    String insertHistoryQuery = "INSERT INTO transaction_history(accountNumber, transactionType, amount) VALUES(?, 'deposit', ?)";
                    PreparedStatement psHistory = con.prepareStatement(insertHistoryQuery);
                    psHistory.setString(1, accountNumber);
                    psHistory.setDouble(2, depositAmount);
                    psHistory.executeUpdate();

                    o.print("Deposit successful");
                } else {
                    o.print("Deposit unsuccessful");
                }
            } else {
                o.print("Account number not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            o.print("Error: " + e.getMessage());
        }
    }
}
