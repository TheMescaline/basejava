<%@ page import="ru.javawebinar.model.ListSection" %>
<%@ page import="ru.javawebinar.model.OrganizationsSection" %>
<%@ page import="ru.javawebinar.model.TextSection" %>
<%@ page import="ru.javawebinar.util.HtmlUtil" %>
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
    <table class="center color2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.model.SectionType, ru.javawebinar.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javawebinar.model.Section"/>

            <tr>
                <td class="view" width="15%"><h3>${type.title}</h3></td>
                <c:if test="${type == 'OBJECTIVE'}">
                    <td><h3><%=((TextSection) section).getText()%>
                    </h3></td>
                </c:if>
            </tr>
            <c:if test="${type!='OBJECTIVE'}">
                <c:choose>
                    <c:when test="${type=='PERSONAL'}">
                        <tr>
                            <td class="view"><%=((TextSection) section).getText()%></td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='ACHIEVEMENT'||type=='QUALIFICATIONS'}">
                        <tr>
                            <td class="view" colspan="2">
                                <ul>
                                    <c:forEach var="item" items="<%=((ListSection) section).getListOfLines()%>">
                                        <li>
                                            ${item}
                                        </li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE'||type=='EDUCATION'}">
                        <c:forEach var="organization" items="<%=((OrganizationsSection) section).getOrganizationsList()%>">
                            <tr>
                                <td class="view">
                                    <c:choose>
                                        <c:when test="${empty organization.url}">
                                            <strong>${organization.name}</strong>
                                        </c:when>
                                        <c:otherwise>
                                            <strong><a href="${organization.url}">${organization.name}</a></strong>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="position" items="${organization.positions}">
                                <jsp:useBean id="position" type="ru.javawebinar.model.Organization.Position"/>
                                <tr>
                                    <td class="view" width="20%" valign="top"><%=HtmlUtil.getDates(position)%></td>
                                    <td class="view" valign="top"><strong>${position.position}</strong><br/>${position.info}</td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>

                </c:choose>
            </c:if>

        </c:forEach>
    </table>
    </p>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
