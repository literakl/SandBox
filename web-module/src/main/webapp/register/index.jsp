<%--
  Date: 5. 4. 2014
  This code is intented as show case for stackoverflow user having trouble
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration sample</title>
</head>
<body>

    <%--@elvariable id="USER" type="cz.literak.sandbox.so.register.User"--%>
    <c:choose>
        <c:when test="${USER == null}">
            <form action="${pageContext.request.contextPath}/RegistrationController" method="post">
                <table>
                    <tr>
                        <td>User name</td>
                        <td><input type="text" name="name" ></td>
                    </tr>
                    <tr>
                        <td>Phone</td>
                        <td><input type="text" name="phone"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="submit"></td>
                    </tr>
                </table>
            </form>
        </c:when>
        <c:otherwise>
            Welcome <c:out value="${USER.name}"/>
        </c:otherwise>
    </c:choose>

</body>
</html>
