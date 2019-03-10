<%-- 
    Document   : guitar-test
    Created on : Mar 2, 2019, 4:38:33 PM
    Author     : DATTTSE62330
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>test SHOWING GUITAR </title>
    </head>
    <body>
        <h1>SHOWING GUITAR TESTING </h1>
        <c:import var="xmlFile" url="WEB-INF/classes/tiendat/fileOutput/output_guitar_station.xml" charEncoding="UTF-8"/>
        <x:parse var="doc" doc="${xmlFile}" scope="session" />
        <c:if test="${not empty doc}">
            <x:set var="guitars" select="$doc//*[local-name()='guitar']"/>
            <table>
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Image</th>
                        <th>Attribute</th>
                    </tr>
                </thead>
                <tbody>
                    <x:forEach var="guitar" select="$guitars" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                <x:out select="$guitar/*[local-name()='name']"/>
                            </td>
                            <td>
                                <x:out select="$guitar/*[local-name()='category']"/> 
                            </td>
                            <td>
                                <x:out select="$guitar/*[local-name()='price']"/> VNĐ
                            </td>
                            <td>
                                <img src="<x:out select="$guitar/*[local-name()='imageUrl']"/>" alt="Guitar Images" width="300" height="200"/>
                            </td>
                            <td>
                                <table>
                                    <tbody>
                                        <x:forEach var="attr" select="$guitar/*[local-name()='attributes']/*[local-name()='attribute']">
                                            <tr>                                                
                                                <td><x:out select="$attr/*[local-name()='name']"/></td>
                                                <td><x:out select="$attr/*[local-name()='content']"/></td>
                                            </tr>
                                        </x:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </x:forEach>
                </tbody>
            </table>                        
        </c:if>
        <c:if test="${empty doc}">
            <h1><font color="red">There are no doc</font></h1>
            </c:if>
    </body>
</html>
