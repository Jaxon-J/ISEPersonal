
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String fName = request.getParameter("fName");
      String lName = request.getParameter("lName");
      String gamesWon = request.getParameter("gamesWon");
      String gamesPlayed = request.getParameter("gamesPlayed");
      String averageBet = request.getParameter("averageBet");
      String averagePts = request.getParameter("averagePts");
      String averagePtsLost = request.getParameter("averagePtsLost");
      String totalPts = request.getParameter("totalPts");

      Connection connection = null;
      String insertSql = " INSERT INTO pitch (id, firstName, lastName, gamesWon, gamesPlayed, averageBet, averagePts, averagePtsLost, totalPts) values (default, ?, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, fName);
         preparedStmt.setString(2, lName);
         preparedStmt.setString(3, gamesWon);
         preparedStmt.setString(4, gamesPlayed);
         preparedStmt.setString(5, averageBet);
         preparedStmt.setString(6, averagePts);
         preparedStmt.setString(7, averagePtsLost);
         preparedStmt.setString(8, totalPts);
         
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>First Name</b>: " + fName + "\n" + //
            "  <li><b>Last Name</b>: " + lName + "\n" + //
            "  <li><b>Games Won</b>: " + gamesWon + "\n" + //
            "  <li><b>Games Played</b>: " + gamesPlayed + "\n" + //
            "  <li><b>Average Bet</b>: " + averageBet + "\n" + //
            "  <li><b>Average Points Won</b>: " + averagePts + "\n" + //
            "  <li><b>Average Points Lost</b>: " + averagePtsLost + "\n" + //
            "  <li><b>Total Points</b>: " + totalPts + "\n" + //

            "</ul>\n");

      out.println("<a href=/pitchTracker/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
