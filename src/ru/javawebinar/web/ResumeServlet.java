package ru.javawebinar.web;

import ru.javawebinar.Config;
import ru.javawebinar.model.ContactType;
import ru.javawebinar.model.ListSection;
import ru.javawebinar.model.Resume;
import ru.javawebinar.model.SectionType;
import ru.javawebinar.model.TextSection;
import ru.javawebinar.storage.Storage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal.");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(contactType, value);
            } else {
                resume.getContacts().remove(contactType);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(sectionType.name());
                    if (value != null && value.trim().length() != 0) {
                        resume.setSection(sectionType, new TextSection(value));
                    } else {
                        resume.getSections().remove(sectionType);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] values = request.getParameterValues(sectionType.name());
                    if (values != null) {
                        List<String> list = new ArrayList<>();
                        for (String line : values) {
                            if (line.trim().length() != 0) {
                                list.add(line);
                            }
                        }
                        resume.setSection(sectionType, new ListSection(list));
                    } else {
                        resume.getSections().remove(sectionType);
                    }
                    break;
                default:
                    break;
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }
}
