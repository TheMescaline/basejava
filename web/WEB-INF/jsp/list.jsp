<%@ page import="ru.javawebinar.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>List of all resumes</title>
</head>
<jsp:include page="fragments/header.jsp"/>
<body>
<section>
    <table class="center" border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th width="320px">Full name</th>
            <th>e-mail</th>
            <th>Cell phone</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="ru.javawebinar.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${resume.getContact(ContactType.EMAIL)}</td>
                <td>${resume.getContact(ContactType.CELL)}</td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete">Delete</a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></td>
            </tr>
        </c:forEach>
    </table><br/>
    <div class="but">
    <button class="btn btn1" type="submit" onclick="window.location.href='resume?uuid=null&action=create'"><strong>Add new resume</strong></button>
    </div>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>