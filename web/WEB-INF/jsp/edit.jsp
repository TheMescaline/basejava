<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.javawebinar.model.ContactType" %>
<%@ page import="ru.javawebinar.model.SectionType" %>
<%@ page import="ru.javawebinar.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.model.Resume" scope="request"/>
    <title>Editing ${resume.fullName}</title>
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
                        <dd><textarea wrap="soft"
                                      name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea>
                        </dd>
                    </dl>
                </c:when>

                <c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">
                    <dl>
                        <dt><strong>${sectionType.title}</strong>
                            <input class=".sml" type="button" onclick="addOrganization('${sectionType}')"
                                   value="Add Section"></dt>
                        <dd id="${sectionType}">
                            <c:forEach items="${(resume.getSection(sectionType)).getOrganizationsList()}"
                                       var="organization" varStatus="counter">
                                Название организации<input class="org" type="text" value="${organization.getName()}" name="${sectionType}" size="45">
                                Ссылка на сайт<input class="org" type="text" value="${organization.getUrl()}" name="${sectionType}url" size="81"><br/>
                                <c:forEach items="${organization.getPositions()}" var="position">
                                    <jsp:useBean id="position" type="ru.javawebinar.model.Organization.Position"/>
                                    <input class="org" type="text" value="<%=DateUtil.format(position.getStartDate())%>"
                                           name="${sectionType}${counter.index}startDate" placeholder="MM/yyyy">
                                    -
                                    <input class="org" type="text" value="<%=DateUtil.format(position.getEndDate())%>"
                                           name="${sectionType}${counter.index}endDate" placeholder="MM/yyyy">
                                    Позиция
                                    <input class="org" type="text" value="${position.getPosition()}"
                                           name="${sectionType}${counter.index}position">
                                    Доп информация
                                    <input class="org" type="text" value="${position.getInfo()}"
                                           name="${sectionType}${counter.index}info" size="71">
                                    <br/>
                                </c:forEach><br/>
                            </c:forEach>
                        </dd>
                    </dl>
                </c:when>

            </c:choose>
        </c:forEach>

        <script>
            function addOrganization(elementId) {
                var div = document.createElement('div');

                var organizationName = document.createElement('input');
                organizationName.type = "text";
                organizationName.name = "name";
                organizationName.size = 45;
                organizationName.setAttribute("class", "org");

                var url = document.createElement('input');
                url.type = "text";
                url.name = elementId + "url";
                url.size = 81;
                url.setAttribute("class", "org");

                var startDate = document.createElement('input');
                startDate.type = "date";
                startDate.name = "startDate";
                startDate.setAttribute("class", "org");

                var endDate = document.createElement('input');
                endDate.type = "date";
                endDate.name = "endDate";
                endDate.setAttribute("class", "org");

                var position = document.createElement('input');
                position.type = "text";
                position.name = "position";
                position.setAttribute("class", "org");

                var info = document.createElement('input');
                info.type = "text";
                info.name = "info";
                info.size = 71;
                info.setAttribute("class", "org");


                div.appendChild(document.createTextNode("Название организации"));
                div.appendChild(organizationName);
                div.appendChild(document.createTextNode(" Ссылка на сайт"));
                div.appendChild(url);
                div.appendChild(document.createElement('br'));
                div.appendChild(startDate);
                div.appendChild(document.createTextNode(" - "));
                div.appendChild(endDate);
                div.appendChild(document.createTextNode(" Позиция "));
                div.appendChild(position);
                div.appendChild(document.createTextNode(" Доп информация "));
                div.appendChild(info);
                document.getElementById(elementId).appendChild(div);

                function addPosition() {

                }
            }

        </script>

        <button class="btn btn1 lrg" type="submit"><strong>Save</strong></button>
        <button class="btn btn1 lrg" onclick="window.history.back()"><strong>Cancel</strong></button>
    </form>
</section>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
