package com.student.web;

import java.io.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/edit")
public class EditStudentServlet extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/student_db";
    String username = "root";
    String password = "7396045254";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE id=?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h2>Edit Student</h2>");

                out.println("<form action='update' method='post'>");

                out.println("<input type='hidden' name='id' value='" + id + "'>");

                out.println("Name: <input name='name' value='" + rs.getString("name") + "'><br><br>");
                out.println("Age: <input name='age' value='" + rs.getInt("age") + "'><br><br>");
                out.println("Course: <input name='course' value='" + rs.getString("course") + "'><br><br>");

                out.println("<button type='submit'>Update</button>");

                out.println("</form>");
            }

            con.close();

        } catch (Exception e) {
            out.println("Error: " + e);
        }
    }
}