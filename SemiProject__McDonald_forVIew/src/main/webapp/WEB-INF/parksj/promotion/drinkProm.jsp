<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- promotion CSS --%> 
 <link rel="stylesheet" href="<%= request.getContextPath()%>/css/promotion/promotion.css" type="text/css"/>

<style type="text/css">

.topimage{
background:url(<%= request.getContextPath() %>/images/프로모션상단이미지.jpg) 50% 50% no-repeat;
}

.next,
.prev{
background:url(<%= request.getContextPath()%>/images/btn_bbs_prev.png);
}
</style>

<jsp:include page="/WEB-INF/header_footer/header.jsp"/>
<!-- 메인 컨테이너 시작-->
<div class="MCcontent">
	<div class="topimage">
		<div class="toptext" style="padding-top: 50px;">
			<h1 class="titDep1">맥도날드 프로모션</h1>
		</div>
		<div class="toptext2">
			<ul>
				<li class="listfirst"><a href="<%= request.getContextPath()%>/main.run">Home</a></li>
				<li class="listsecond"><a href="<%= request.getContextPath()%>/promotion/promotionMain.run">맥도날드 프로모션</a></li>
			</ul>
		</div>
	</div>
	<!-- 상단이미지 끝-->
	<div class="container" style="width: 100%; position: relative; margin-bottom: 40px;" >
		<div class="titl">
			<div class="tex">
				<h2 style="font-size:15pt;">상콤달콤!</h2>
				<h2 style="font-size:15pt;">NEW 자두 천도복숭아 칠러 출시!</h2>
			</div>
		</div>
		<div class="main" style="margin-top: 50px;">
			<img src="<%= request.getContextPath()%>/images/promotion_1.jpg" style="width:100%;">
		</div>
		
		<hr>
		
		<div class="btnArea">
			<a href="<%= request.getContextPath()%>/promotion/promotionMain.run" role="button" class="btnMC btnM"><span>목록보기</span></a>
			<button type="button" onclick="location.href='<%= request.getContextPath()%>/promotion/mcMorningProm.run'" class="next"></button>
		</div>
	</div>
		
		
	
	
</div>

<!-- 메인 컨테이너 끝-->

<jsp:include page="/WEB-INF/header_footer/footer.jsp"/>

