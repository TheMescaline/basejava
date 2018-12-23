<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.javawebinar.model.ContactType" %>
<%@ page import="ru.javawebinar.model.ListSection" %>
<%@ page import="ru.javawebinar.model.SectionType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.model.Resume" scope="request"/>
    <title>Resume of ${resume.fullName}</title>
</head>
<jsp:include page="fragments/header.jsp"/>
<body>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt><strong>Name</strong></dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Contacts:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt><strong>${contactType.title}</strong></dt>
                <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}">
                </dd>
            </dl>
        </c:forEach>
        <hr/>
        <h3>Sections:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
                    <dl>
                        <dt><strong>${sectionType.title}</strong></dt>
                        <dd><input type="text" name="${sectionType.name()}" size="110"
                                   value="${resume.getSection(sectionType)}"></dd>
                    </dl>
                </c:when>

                <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT)}">
                    <dl>
                        <dt><strong>${sectionType.title}</strong><br/><input type="button"
                                                                             onclick="addLine('${sectionType.name()}', 'textSection')"
                                                                             value="Add Line" align="right"/></dt>
                        <dd id="textSection">
                            <c:forEach var="line"
                                       items="<%=((ListSection)resume.getSection(SectionType.ACHIEVEMENT)).getListOfLines()%>">
                                <input type="text" name="${sectionType.name()}" size="110"
                                       value="${line}"><br/>
                            </c:forEach>
                        </dd>
                    </dl>
                </c:when>

                <c:when test="${sectionType.equals(SectionType.QUALIFICATIONS)}">
                    <dl>
                        <dt><strong>${sectionType.title}</strong><br/><input type="button"
                                                                             onclick="addLine('${sectionType.name()}', 'listSection')"
                                                                             value="Add Line" align="right"/></dt>
                        <dd id="listSection">
                            <c:forEach var="line"
                                       items="<%=((ListSection)resume.getSection(SectionType.QUALIFICATIONS)).getListOfLines()%>">
                                <input type="text" name="${sectionType.name()}" size="110"
                                       value="${line}"><br/>
                            </c:forEach>
                        </dd>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>

        <script>
            function addLine(type, elementId) {
                var div = document.createElement("div");

                var text = document.createElement("input");
                text.setAttribute("type", "text");
                text.setAttribute("name", type);
                text.setAttribute("size", "110");

                div.appendChild(text);
                document.getElementById(elementId).appendChild(div);
            }
        </script>

        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
