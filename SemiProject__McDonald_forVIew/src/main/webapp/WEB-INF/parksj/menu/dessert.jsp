<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- burger CSS --%> 
 <link rel="stylesheet" href="<%= request.getContextPath()%>/css/menu/menu.css" type="text/css"/>

<style type="text/css">

.topimage{
background:url(<%= request.getContextPath() %>/images/디저트상단이미지.png) 50% 50% no-repeat;
}

</style>

<jsp:include page="/WEB-INF/header_footer/header.jsp"/>

<script type="text/javascript">

	$(document).ready(function(){
		
		$("span#totalCount").hide();
		$("span#countItem").hide();
		// HIT상품 게시물을 더보기 위하여 "더보기..." 버튼 클릭액션에 대한 초기값 호출하기 
	    // 즉, 맨처음에는 "더보기..." 버튼을 클릭하지 않더라도 클릭한 것 처럼 8개의 HIT상품을 게시해주어야 한다는 말이다. 
		displayDessert("1");
		
		// HIT 상품 게시물을 더보기 위하여 "더보기..." 버튼 클릭액션 이벤트 등록하기
		$("button#btnDessert").click(function(){
			
			displayDessert($(this).val());
	         
		});//end of $("button#btnMoreHIT").click(function()
			
				
	});// end of $(document).ready(function()
			
	let lenDessert = 6;
	
	function displayDessert(start){
		$.ajax({
			url:"<%= request.getContextPath()%>/menu/dessertJSON.run",
			type:"get",
			data:{
				"category_name":"디저트",
				"start":start,
				"len":lenDessert},
			dataType:"json",
			async:true,
			success:function(json){
				
				//console.log("확인용 json => "+ JSON.stringify(json) );
				
				let html = "";
				  
				// 처음부터 데이터가 존재하지 않는 경우
				if(start == "1" && json.length == 0){
					html += "현재 상품 준비중....";
					
					// Burger 상품 결과물 출력하기
					$("div#displayDessert").html(html);
				}
				else if(json.length > 0){
					
					$.each(json, function(index, item){						
						    html += "<div class='col-sm-6 col-lg-4 mb-3' style='width: 18rem'>"
								 	+ "<div id='bgcard' class='card card-1'>"
								 		+ "<img src='/SemiProject__McDonald_forVIew/images/"+item.item_image+"' class='card-img-top' style='width: 100%' />"
								 		+ "<div class='card-bodys'>"
								 			+ "<h5 class='card-title'>"+item.item_name+"</h5>"
								 			+ "<a href='/SemiProject__McDonald_forVIew/detail/dessertView.run?item_no="+item.item_no+"' class='stretched-link'></a>"
								 		+ "</div>"
								 	+ "</div>"
								 + "</div>";								 
					}); // end of $.each(json, function(index, item)		
					
					// Burger 상품 결과물 출력하기
					$("div#displayDessert").append(html);
					
					// >>> !!! 중요 !!! 더보기... 버튼의 value 속성에 값을 지정하기 <<< //
					$("button#btnDessert").val( Number(start)+lenDessert );
					
					// span#countItem 에 지금까지 출력된 상품의 개수를 누적해서 기록한다.
					$("span#countItem").text(Number($("span#countItem").text())+json.length );
					
					// 더보기... 버튼을 계속해서 클릭하여 countHIT 값과 totalHITCount 값이 일치하는 경우 
					if( $("span#totalCount").text() == $("span#countItem").text() ){
						
						$("button#btnDessert").hide();
						
					}
				}// end of else if(json.length > 0)
			},//end of success:function(json)
			error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }
		});
	}
</script>

<div class="MCcontent">
	<div class="topimage">
		<div class="toptext">
			<h1 class="titDep1">디저트</h1>
			<p style="margin-top: 15px;">버거를 푸짐하게 즐기고 난 후!</p>
			<p>가볍게 즐기는 디저트메뉴!</p>
		</div>
		<div class="toptext2">
			<ul>
				<li class="listfirst"><a href="<%= request.getContextPath()%>/main.run">Home</a></li>
				<li class="listsecond"><a href="<%= request.getContextPath()%>/menu/burgerMain.run">Menu</a></li>
			</ul>
		</div>
	</div>
	
	<div class="container" style="padding: 20px 15px; ">
	
		<div class="menu_tab_change text-center" style="margin-bottom: 30px; ">
			<button type="button" class="danpum active">디저트</button>
		</div>
	    <!-- 중앙 컨텐츠 시작 -->
		<div class="row" id="displayDessert">
		</div>
		<!-- row 끝-->
		
		<div class="text-center">
			<button class="extendbtn text-center" id="btnDessert" style="border: solid 0px green; background-color:#FFFFFF;">
				<img src="<%= request.getContextPath() %>/images/btn_more.png">
			</button>
			<span id="totalCount">${requestScope.totalCount}</span>
	        <span id="countItem">0</span>
		</div>
		<!-- 중앙 컨텐츠 끝 -->
	
	    <hr>
		
		
		
	</div>	
	<!-- div class="container" 끝 -->
	    

</div>
<jsp:include page="/WEB-INF/header_footer/footer.jsp"/>
