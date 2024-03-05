package Perform;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList; // Import ArrayList
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Calculator")
public class Calculator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<center>");
        HttpSession session = request.getSession(true);

        try {
            int a = Integer.parseInt(request.getParameter("t1"));
            int b = Integer.parseInt(request.getParameter("t2"));
            int c = 0;
            String op = request.getParameter("btn");

            if (op.equals("+"))
                c = a + b;
            if (op.equals("-"))
                c = a - b;
            if (op.equals("*"))
                c = a * b;
            if (op.equals("/"))
                c = a / b;

            // Retrieve history from session
            ArrayList<String> history = (ArrayList<String>) session.getAttribute("history");

            // If history doesn't exist, create a new one
            if (history == null) {
                history = new ArrayList<>();
            }

            // Add current calculation to history at the beginning
            history.add(0, a + " " + op + " " + b + " = " + c);
            
            // Update session with the new history
            session.setAttribute("history", history);

            out.println("<h3>" + a + " " + op + " " + b + " = " + c + "</h3>");

            // Display history on the right side
            out.println("<div style='float:right;'>");
            out.println("<h3>History:</h3>");
            for (String entry : history) {
                out.println("<p>" + entry + "</p>");
            }
            out.println("</div>");

        } catch (Exception e) {
            out.println("Error:" + e.getMessage());
        } finally {
            out.println("<br>");
            out.println("To go to the main page <a href=index.html>click here</a>");
            out.println("<br>");
            out.println("<form action='ClearHistory' method='post'>"); // Form to clear history
            out.println("<input type='submit' value='Clear History'>");
            out.println("</form>");
            out.println("</center>");
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
