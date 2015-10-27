<%--
  Created by IntelliJ IDEA.
  User: Lena
  Date: 15.10.15
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <footer>
        <div class="row">
            <ul class="links">
                <li><a href="#"><spring:message code="menu.about.us"/></a></li>
                <li><a href="#"><spring:message code="menu.how.it"/></a></li>
                <li><a href="#"><spring:message code="menu.contacts"/></a></li>
            </ul>
            <div class="grid_6">
                <address><a href="/">next.book</a> © 2015</address>
            </div>
        </div>
    </footer>

<!-- top scroll -->
<script type="text/javascript">
    $(function(){
        $.fn.scrollToTop=function(){
            $(this).hide().removeAttr("href");
            if($(window).scrollTop()!="0"){
                $(this).fadeIn("slow")
            }
            var scrollDiv=$(this);
            $(window).scroll(function(){
                if($(window).scrollTop()=="0"){
                    $(scrollDiv).fadeOut("slow")
                }else{
                    $(scrollDiv).fadeIn("slow")
                }
            });
            $(this).click(function(){
                $("html, body").animate({scrollTop:0},"slow")
            })
        }
    });
    $(function() {$("#toTop").scrollToTop();});
</script>
<a href="#" id="toTop">Top</a>
