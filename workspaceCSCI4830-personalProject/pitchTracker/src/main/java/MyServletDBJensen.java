import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletDBJensen")
public class MyServletDBJensen extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-18-191-134-49.us-east-2.compute.amazonaws.com:3306/personalProject";
   static String user = "user";
   static String password = "password";
   static Connection connection = null;

   public MyServletDBJensen() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().println("-------- MySQL JDBC Connection Testing ------------<br>");
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");//("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      response.getWriter().println("MySQL JDBC Driver Registered!<br>");
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection != null) {
         response.getWriter().println("You made it, take control your database now!<br>");
      } else {
         System.out.println("Failed to make connection!");
      }
      try {
         String selectSQL = "SELECT * FROM pitch order by firstName";// WHERE MYUSER LIKE ?";
//         String theUserName = "user%";
         response.getWriter().println(selectSQL + "<br>");
         response.getWriter().println("------------------------------------------<br>");
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
//         preparedStatement.setString(1, theUserName);
         ResultSet rs = preparedStatement.executeQuery();
         while (rs.next()) {
            String id = rs.getString("id");
            String fName = rs.getString("firstName");
            String lName = rs.getString("lastName");
            String gamesWon = rs.getString("gamesWon");
            String gamesPlayed = rs.getString("gamesPlayed");
            String averageBet = rs.getString("averageBet");
            String averagePts = rs.getString("averagePts");
            String averagePtsLost = rs.getString("averagePtsLost");
            String totalPts = rs.getString("totalPoints");
            response.getWriter().append("User ID: " + id + ", ");
            response.getWriter().append("First Name: " + fName + ", ");
            response.getWriter().append("Last Name: " + lName + ", ");
            response.getWriter().append("Games Won: " + gamesWon + "<br>");
            response.getWriter().append("Games Played : " + gamesPlayed + "<br>");
            response.getWriter().append("Average Bet: " + averageBet + "<br>");
            response.getWriter().append("Average Points Won: " + averagePts + "<br>");
            response.getWriter().append("Aveage Points Lost: " + averagePtsLost + "<br>");
            response.getWriter().append("Total Points Won: " + totalPts + "<br>");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}