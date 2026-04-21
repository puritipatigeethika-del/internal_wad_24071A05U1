package com.student.web;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/view")
public class ViewStudentServlet extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/student_db";
    String username = "root";
    String password = "7396045254";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // ✅ Get ID safely
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            out.println("<h3 style='color:red;'>Error: ID is missing in request</h3>");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            out.println("<h3 style='color:red;'>Error: Invalid ID format</h3>");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);

            ps = con.prepareStatement("SELECT * FROM students WHERE id=?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h2>Edit Student</h2>");

                out.println("<form action='update' method='post'>");

                // Hidden ID
                out.println("<input type='hidden' name='id' value='" + id + "'>");

                out.println("Name: <input name='name' value='" + rs.getString("name") + "'><br><br>");
                out.println("Age: <input name='age' value='" + rs.getInt("age") + "'><br><br>");
                out.println("Course: <input name='course' value='" + rs.getString("course") + "'><br><br>");

                out.println("<button type='submit'>Update</button>");
                out.println("</form>");

            } else {
                out.println("<h3 style='color:red;'>No student found with ID: " + id + "</h3>");
            }

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}