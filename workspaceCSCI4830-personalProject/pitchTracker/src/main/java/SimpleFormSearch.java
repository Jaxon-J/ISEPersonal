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

      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>" + title + "</title>");
      out.println("<style>");
      out.println("body { background-color: #277714; color: white; }");
      out.println("table { width: 100%; text-align: center; border-color: yellow; background-color: #277714; margin-top: 100px; }");
      out.println("table th { font-size: 20px; }");
      out.println("table th, table td { color: white; }");
      out.println(".button-container { margin-top: 20px; }"); // Add margin between table and button
      out.println(".button-link button { font-size: 16px; padding: 5px 10px; font-size: 16px; background-color: #277714; border: 1px solid red; color: red; }");      
      out.println("</style>");
      out.println("</head>");
      out.println("<body>");
      out.println("<header style=\"background-color:black; color:white; text-align:center; padding:30px; position: fixed; top: 0; left: 0; width: 100%; font-size: 30px; font face = Times New Roman;\"><b>Record Inserted</b></header>"); // Modified header content
      
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
            String theUserName = keyword.toLowerCase() + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
         }
         ResultSet rs = preparedStatement.executeQuery();

         out.println("<table border='1'>");
         out.println("<tr>");
         out.println("<th>ID</th>");
         out.println("<th>First Name</th>");
         out.println("<th>Last Name</th>");
         out.println("<th>Games Won</th>");
         out.println("<th>Games Played</th>");
         out.println("<th>Average Bet</th>");
         out.println("<th>Average Points Won</th>");
         out.println("<th>Average Points Lost</th>");
         out.println("<th>Total Points Won</th>");
         out.println("</tr>");

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

             if (keyword.isEmpty() || fName.toLowerCase().contains(keyword)) {
                 out.println("<tr>");
                 out.println("<td>" + id + "</td>");
                 out.println("<td>" + fName + "</td>");
                 out.println("<td>" + lName + "</td>");
                 out.println("<td>" + gamesWon + "</td>");
                 out.println("<td>" + gamesPlayed + "</td>");
                 out.println("<td>" + averageBet + "</td>");
                 out.println("<td>" + averagePts + "</td>");
                 out.println("<td>" + averagePtsLost + "</td>");
                 out.println("<td>" + totalPts + "</td>");
                 out.println("</tr>");
             }
         }

         out.println("</table>");
         out.println("<div class=\"button-container\">");
         out.println("<a href=\"/pitchTracker/simpleFormSearch.html\" class=\"button-link\"><button>Search Data</button></a>");
         out.println("</div>");
         
         out.println("<footer style=\"background-color: red; color: white; clear:both; text-align:center; padding: 5px; left: 0; position: fixed; bottom: 0; width: 100%;\">");
         out.println("Copyright Jaxon Jensen");
         out.println("</footer>");
         
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
