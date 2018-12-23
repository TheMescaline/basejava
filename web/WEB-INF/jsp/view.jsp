<%@ page import="ru.javawebinar.web.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.model.ContactType, java.lang.String>"/>
    <dl>
        <dt><strong><%=contactEntry.getKey().getTitle()%>
        </strong></dt>
        <dd><%=HtmlUtil.printContact(contactEntry.getKey(), contactEntry.getValue())%>
        </dd>
    </dl>
    </c:forEach>
    <br/>
    <table>
        <tbody>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.model.SectionType, ru.javawebinar.model.Section>"/>
            <dl>
                <dt><strong><%=sectionEntry.getKey().getTitle()%>
                </strong></dt>
                <dd><%=HtmlUtil.printSection(sectionEntry.getKey(), sectionEntry.getValue())%>
                </dd>
            </dl>
        </c:forEach>
        </tbody>
    </table>
    </p>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
