package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.Resume;
import ru.javawebinar.storage.Storage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Storage sqlStorage = Config.getInstance().getStorage();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\" utf - 8\">");
        out.println("<title>table</title>");
        out.println("<link rel=\" stylesheet\" href=\" style.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<center>");
        out.println("<h1>Resumes</h1>");
        out.println("<table border=\"1\" cellpadding=\"5\">");
        out.println("<tr>");
        out.println("<th>UUID</th>");
        out.println("<th>Full name</th>");
        out.println("</tr>");
        List<Resume> listOfResumes = sqlStorage.getAllSorted();
        for (Resume resume : listOfResumes) {
            out.println("<tr>");
            out.println("<td style=\"width: 310px;\">" + resume.getUuid() + "</td>");
            out.println("<td>" + resume.getFullName() + "</td>");
            out.println("</tr>");
        }
        out.println("</tr>");
        out.println("</table>");
        out.println("</center>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
