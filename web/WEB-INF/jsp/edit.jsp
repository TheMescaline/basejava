<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.javawebinar.model.ContactType" %>
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
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required></dd>
        </dl>
        <h3>Contacts:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt><strong>${contactType.title}</strong></dt>
                <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}">
                </dd>
            </dl>
        </c:forEach>
        <br/>
        <h3>Sections:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
                    <dl>
                        <dt><strong>${sectionType.title}</strong></dt>
                        <dd><input type="text" name="${sectionType.name()}" size="111"
                                   value="${resume.getSection(sectionType)}"></dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)}">
                    <dl>
                        <dt><strong>${sectionType.title}</strong></dt>
                        <dd><textarea wrap="soft" name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea>
                        </dd>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <button class="btn btn1" type="submit"><strong>Save</strong></button>
        <button class="btn btn1" onclick="window.history.back()"><strong>Cancel</strong></button>
    </form>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
