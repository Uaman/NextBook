<%--
  Created by IntelliJ IDEA.
  User: Lena
  Date: 16.10.15
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="footer">
        <div class="footer-banners">
            <blockquote>
                <div class="row quotes">
                    <span>THIS IS RANDOM QUOTE</span><br/>Quote's author
                </div>
            </blockquote>
        </div>
        <div class="footer-info soc-content">
            <div class="row">
                <div class="social-content">
                    <ul class="social">
                        <li style="text-align: left;"><a class="facebook" href="https://facebook.com/next.books"><span>Facebook</span></a></li>
                        <li style="text-align: left;"><a class="twitter" href="https://twitter.com/nextbooker"><span>Twitter</span></a></li>
                        <li style="text-align: left;"><a class="email" href="mailto:marketing@kassiopeya.com"><span>Email</span></a></li>
                    </ul>
                </div>
                <div class="partners">
                    <h4><spring:message code="our.partners.title"/>:</h4>
                    <ul>
                        <li><a title="Книжкова біржа" href="http://logobook.ua" target="_blank"><img title="Книжкова Біржа" onmouseover="this.src='../resources/images/logobook.png';" onmouseout="this.src='../resources/images/logobook_gray.png';" src="../resources/images/logobook_gray.png" alt="" width="79" height="50"></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>