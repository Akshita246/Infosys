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

@WebServlet("/withdraw")
public class Withdraw extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cardNumber = req.getParameter("accountNumber");
        String withdrawAmountStr = req.getParameter("withdrawAmount");
        String enteredPin = req.getParameter("pin");
        PrintWriter o = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jdbc_login";
            Connection con = DriverManager.getConnection(url, "root", "akshitabhandari24@gmail.com");

            // Validate the PIN
            String checkPinQuery = "SELECT pin, balance FROM users WHERE cardNumber = ?";
            PreparedStatement ps = con.prepareStatement(checkPinQuery);
            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String originalPin = rs.getString("pin");
                double currentBalance = rs.getDouble("balance");

                if (enteredPin != null && enteredPin.equals(originalPin)) {
                    double withdrawAmount = Double.parseDouble(withdrawAmountStr);

                    if (currentBalance >= withdrawAmount) {
                        // Update the balance by deducting the withdraw amount
                        String updateBalanceQuery = "UPDATE users SET balance = balance - ? WHERE cardNumber = ?";
                        PreparedStatement psUpdate = con.prepareStatement(updateBalanceQuery);
                        psUpdate.setDouble(1, withdrawAmount);
                        psUpdate.setString(2, cardNumber);
                        int result = psUpdate.executeUpdate();

                        if (result > 0) {
                            // Insert into transaction history
                            String insertHistoryQuery = "INSERT INTO transaction_history(accountNumber, transactionType, amount) VALUES(?, 'withdrawal', ?)";
                            PreparedStatement psHistory = con.prepareStatement(insertHistoryQuery);
                            psHistory.setString(1, cardNumber);
                            psHistory.setDouble(2, withdrawAmount);
                            psHistory.executeUpdate();

                            o.print("Withdraw successful");
                        } else {
                            o.print("Withdraw unsuccessful");
                        }
                    } else {
                        o.print("Insufficient balance");
                    }
                } else {
                    o.print("Invalid PIN");
                }
            } else {
                o.print("Card number not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            o.print("Error: " + e.getMessage());
        }
    }
}
