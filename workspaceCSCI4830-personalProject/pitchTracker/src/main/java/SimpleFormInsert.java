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
      String title = "Inserted Record to Table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#277714\">\n" + // Changed background color
            "<header style=\"background-color:black; color:white; text-align:center; padding:30px; position: fixed; top: 0; left: 0; width: 100%; font-size: 30px; font face = Times New Roman;\"><b>Search Results</b></header>\n" + 
            "<div style=\"margin-top: 100px;\">"); // Add margin-top to create space below the header

      out.println("<ul style=\"color: yellow; font-size: 20px;\">\n"); // Changed text color

      out.println("  <li><b>First Name</b>: " + fName + "\n" + //
            "  <li><b>Last Name</b>: " + lName + "\n" + //
            "  <li><b>Games Won</b>: " + gamesWon + "\n" + //
            "  <li><b>Games Played</b>: " + gamesPlayed + "\n" + //
            "  <li><b>Average Bet</b>: " + averageBet + "\n" + //
            "  <li><b>Average Points Won</b>: " + averagePts + "\n" + //
            "  <li><b>Average Points Lost</b>: " + averagePtsLost + "\n" + //
            "  <li><b>Total Points</b>: " + totalPts + "\n" + //

            "</ul>\n");

      out.println("<form action=\"/pitchTracker/simpleFormSearch.html\" method=\"get\"><button type=\"submit\" style=\"background-color: transparent; border: 1px solid red; color: red; font-size: 16px; padding: 10px;\">Search Data</button></form>"); 
      
      out.println("</div>");
      
      out.println("<footer style=\"background-color: red; color: white; clear:both; text-align:center; padding:5px; position: fixed; bottom: 0; left: 0; width: 100%;\">");
      out.println("Copyright Jaxon Jensen");
      out.println("</footer>");

      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
