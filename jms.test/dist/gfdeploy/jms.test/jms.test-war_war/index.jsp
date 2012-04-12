<%-- 
    Document   : index
    Created on : Mar 26, 2012, 9:08:22 PM
    Author     : are
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
   xmlns:h="http://java.sun.com/jsf/html">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
       <h:form>
            <h:outputText value="Info om modulen:" />
            <h:inputText value=#{"objectBean.getText()"} />
            
        </h:form>
    </body>
</html> 
