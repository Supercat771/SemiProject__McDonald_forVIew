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

.main > iframe {
	margin-top: 114.5px;
	margin-left: 165px;
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
				<h2 style="font-size:15pt;">68년 노하우의 클래스</h2>
				<h2 style="font-size:15pt;">1955 트리플 어니언</h2>
			</div>
		</div>
		
		<div class="test" style="display: block;">
			<div class="main" style="margin-top: 50px; position: relative;">
			 <img src="<%= request.getContextPath()%>/images/promotion_3.jpg" style="width:100%;">
			 <iframe width="780" height="448" src="https://www.youtube.com/embed/g7EzMPH8Ir0" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen style="position: absolute; top: 0; left: 0;"></iframe>
			</div>
		</div>		
		
		<hr>
		
		<div class="btnArea">
			<button type="button" onclick="location.href='<%= request.getContextPath()%>/promotion/mcMorningProm.run'" class="prev"></button>
			<a href="<%= request.getContextPath()%>/promotion/promotionMain.run" role="button" class="btnMC btnM"><span>목록보기</span></a>
			<button type="button" onclick="location.href='<%= request.getContextPath()%>/promotion/sideProm.run'" class="next"></button>
		</div>
	</div>
		
		
	
	
</div>

<!-- 메인 컨테이너 끝-->

<jsp:include page="/WEB-INF/header_footer/footer.jsp"/>

