import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM pitch";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM pitch WHERE firstName LIKE ?";
            String theUserName = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String fName = rs.getString("firstName").trim();
            String lName = rs.getString("lastName").trim();
            String gamesWon = rs.getString("gamesWon").trim();
            String gamesPlayed = rs.getString("gamesPlayed").trim();
            String averageBet = rs.getString("averageBet").trim();
            String averagePts = rs.getString("averagePts").trim();
            String averagePtsLost = rs.getString("averagePtsLost").trim();
            String totalPts = rs.getString("totalPts").trim();

            if (keyword.isEmpty() || fName.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("First Name: " + fName + "<br>");
               out.println("Last Name: " + lName + "<br>");
               out.println("Games Won: " + gamesWon + "<br>");
               out.println("Games Played: " + gamesPlayed + "<br>");
               out.println("Average Bet: " + averageBet + "<br>");
               out.println("Average Points Won: " + averagePts + "<br>");
               out.println("Average Points Lost: " + averagePtsLost + "<br>");
               out.println("Total Points Won: " + totalPts + "<br>");
            }
         }
         out.println("<a href=/pitchTracker/simpleFormSearch.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
