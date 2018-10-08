<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setBundle basename="localization.en_bundle" scope="session"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="header.title"/></title>

    <fmt:message key="header.description" var="description" />
    <fmt:message key="header.keywords" var="keywords" />
    <meta name="Description" content="${description}">
    <meta name="Keywords" content="${keywords}">
    <meta name="viewport" content="width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes">

    <link rel="manifest" href="manifest.json">
    <link rel="icon" type="image/png" href="img/favicon.ico?v=1">

    <link rel="stylesheet" href="web/css/bootstrap.min.css">
    <link rel="stylesheet" href="web/css/style.css">
    <script src="web/js/jquery-3.3.1.min.js"></script>
    <script src="web/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <script defer src="https://use.fontawesome.com/releases/v5.3.1/js/all.js" integrity="sha384-kW+oWsYx3YpxvjtZjFXqazFpA7UP/MbiY4jvs+RWZo2+N94PFZ36T6TFkc9O3qoB" crossorigin="anonymous"></script>
</head>
<body>

<fmt:message key="currency.value" var="currency_value_str"/>
<fmt:parseNumber var="currency_value" value="${currency_value_str}" integerOnly="false"/>

<%@include file="navbar.jsp"%>

<div class="container">
