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

@WebServlet("/transaction_history")
public class TransactionHistory extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accountNumber = req.getParameter("accountNumber");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        
        // Begin HTML content
        out.println("<html><head><title>Transaction History</title>");
        out.println("<link rel='stylesheet' href='transaction_history.css'>"); // Include your CSS file
        out.println("</head><body>");
        
        if (accountNumber != null && !accountNumber.isEmpty()) {
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                // Database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/jdbc_login";
                con = DriverManager.getConnection(url, "root", "akshitabhandari24@gmail.com");
                
                // SQL query
                String query = "SELECT id, accountNumber, transactionType, amount, timestamp FROM transaction_history WHERE accountNumber = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, accountNumber);
                rs = ps.executeQuery();

                if (!rs.isBeforeFirst()) {
                    out.println("<p>No transactions found for account number: " + accountNumber + "</p>");
                } else {
                    out.println("<h2>Transaction History for Account Number: " + accountNumber + "</h2>");
                    out.println("<table class='table'>"); // Add your class for styling
                    out.println("<thead><tr><th>ID</th><th>Account Number</th><th>Transaction Type</th><th>Amount</th><th>Timestamp</th></tr></thead><tbody>");

                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td>" + rs.getInt("id") + "</td>");
                        out.println("<td>" + rs.getString("accountNumber") + "</td>");
                        out.println("<td>" + rs.getString("transactionType") + "</td>");
                        out.println("<td>" + rs.getDouble("amount") + "</td>");
                        out.println("<td>" + rs.getTimestamp("timestamp") + "</td>");
                        out.println("</tr>");
                    }

                    out.println("</tbody></table>");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<p>Error fetching transaction history: " + e.getMessage() + "</p>");
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            out.println("<p>Please enter a valid account number.</p>");
        }
        
        out.println("</body></html>");
    }
}
