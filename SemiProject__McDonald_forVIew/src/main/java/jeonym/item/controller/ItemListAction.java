package jeonym.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import jangjh.member.model.MemberVO;
import jeonym.item.model.InterItemDAO;
import jeonym.item.model.ItemDAO;
import jeonym.item.model.ItemVO;



public class ItemListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
	
	 	
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null && "kingkingadmin".equals(loginuser.getUserid())) {
			// 관리자(kingkingadmin) 으로 로그인 했을 경우

			InterItemDAO idao = new ItemDAO();
			
			// 카테고리 목록을 조회해오기 
	
			String searchType = request.getParameter("searchType");
			// 회원목록 페이지로 보여줄 때 즉, 처음에는 넘어온 값이 없다. 그러므로 searchType 에는 null 이 들어온다.
			// 회원목록 페이지에서 "검색" 버튼을 클릭시 넘어온다. 그러므로 searchType 에는 null 이 아닌 어떤 값이 들어온다.
			String searchWord = request.getParameter("searchWord"); 
			// 회원목록 페이지로 보여줄 때 즉, 처음에는 넘어온 값이 없다. 그러므로 searchWord 에는 null 이 들어온다.
			// 회원목록 페이지에서 "검색" 버튼을 클릭시 넘어온다. 그러므로 searchWord 에는 null 이 아닌 어떤 값이 들어온다.
			
			
			/////////////////////////////////////////////////////////////////////////////////////////
			
			if(searchType == null || 
			   (!"item_no".equals(searchType) && !"item_name".equals(searchType)) ) {  // searchType 이 select 의 옵션중 아닌 것이 들어오면  
				searchType = ""; // 공백 => 선택하세요
			}
			
			if(searchWord == null || 
			  (searchWord != null && searchWord.trim().isEmpty())) { // null 은 아니지만 공백
				searchWord = "";
			}

			
			
			String category_id = request.getParameter("category_id"); // 초기치 null
					
			String currentShowPageNo = request.getParameter("currentShowPageNo"); // 현재 보고자하는 페이지바의 번호
			// currentShowPageNo 은 사용자가 보고자 하는 페이지바의 페이지 번호이다.
			// 카테고리 메뉴에서 카테고림 명만을 클릭했을 경우에는 currentShowPageNo 는 null 이 된다.
			// currentShowPageNo 가 null 이라면 currentShowPageNo 를 1 페이지로 바꾸어야 한다.
			
			String sizePerPage = request.getParameter("sizePerPage");
			// 한 페이지당 화면상에 보여줄 회원의 갯수
			// 메뉴에서 회원목록만을 클릭했을 경우에는 sizePerPage 는 null 이 된다.
			// sizePerPage 가 null 이라면 sizePerPage 를 15으로 바꾸어야 한다.
			// "15" 또는 "10" 또는 "5"

			if(sizePerPage == null ||
					  !("5".equals(sizePerPage) || "10".equals(sizePerPage) || "15".equals(sizePerPage))) {
						sizePerPage = "10";
			}

			
			
			int totalPage = 0; // 페이징 처리를 위해 총 페이지수를 구해야 한다.
			
			if(currentShowPageNo == null) { // 초기치에는 1을 준다.
				currentShowPageNo = "1";
			}
			
			if(category_id == null) { // null 인경우 공백 넣어줌
				category_id = "";
			}
			
			try {
				
				if(Integer.parseInt(currentShowPageNo) < 1) { // paraMap에 값이 들어가기 전에 처리해야한다.
					currentShowPageNo = "1"; // 음수일 경우 1을 준다.
				}
				
				
				
				if(!category_id.trim().isEmpty() && Integer.parseInt(category_id) < 1) { // category_id 가 공백이 아니면서 음수라면
					category_id = "";
				}
				
				
			}catch (NumberFormatException e) {
				currentShowPageNo = "1"; // 문자를 입력할 경우 1을 준다.
				category_id = "";
			}
			
			// TODO.. 존재하지 않는 category_id 가 넘어왔는지 check
			
			if(!"".equals(category_id) && !idao.isExist_category_id(category_id)) { // 공백이 아닌경우에만 검사
				
				
				String message = "존재하지 않는 카테고리 번호입니다.";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/jeonym/msg.jsp");
				
				return;
				
			}
			
	
	
			Map<String, String> paraMap = new HashMap<>();
			
			paraMap.put("currentShowPageNo", currentShowPageNo); // 초기치 1
			paraMap.put("sizePerPage", sizePerPage); 			//한페이지당 보여줄 행의개수
			paraMap.put("category_id", category_id); // 초기치 ""
			paraMap.put("searchType", searchType);
			paraMap.put("searchWord", searchWord);

			
		
			totalPage = idao.getTotalPage(paraMap); // category_id의 유무에 따라 totalPage는 달라진다.
			
			if(Integer.parseInt(currentShowPageNo) > totalPage) { 
				currentShowPageNo = "1";
				paraMap.put("currentShowPageNo", currentShowPageNo);
			} // url 로 currentShowPageNo 를 장난치는 것을 막아주기
			
			
			List<ItemVO> ItemList = idao.getItemList(paraMap); // 페이지에 보여줄 제품의 리스트를 구해온다.
			
			request.setAttribute("ItemList", ItemList);
			request.setAttribute("searchType", searchType);
			request.setAttribute("searchWord", searchWord);
			request.setAttribute("sizePerPage", sizePerPage);
			request.setAttribute("category_id", category_id);

			
			
			int blockSize = 10; 
			// blockSize 는 블럭 당 보여지는 페이지 번호의 갯수이다.
			//  10
			
			int loop = 1; 
			//  loop 는 1부터 증가하여 1개 블럭을 이루는 페이지 번호의 개수(지금은 10개) 까지만 증가하는 용도이다.
			//  1 ~ 10
			
			// !!!! 다음은 pageNo 를 구하는 공식이다. !!!! //
			// ( (currentShowPageNo - 1)/blockSize ) * blockSize + 1
			int pageNo  = (( Integer.parseInt(currentShowPageNo) - 1) / blockSize) * blockSize + 1; 
			// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
			
	
			// **** [맨처음][이전] 만들기 **** //
			String pageBar = "";
			
			pageBar += "<li class='page-item'><a class='page-link' href='itemList.run?searchType="+searchType+"&searchWord="+searchWord+"&sizePerPage="+sizePerPage+"&currentShowPageNo=1&category_id="+category_id+"'>[맨처음]</a></li>"; // bootstrap 에 있는 pageBar 이다.
			
			if(pageNo != 1) {
				pageBar += "<li class='page-item'><a class='page-link' href='itemList.run?searchType="+searchType+"&searchWord="+searchWord+"&sizePerPage="+sizePerPage+"&currentShowPageNo="+(pageNo - 1)+"&category_id="+category_id+"'>[이전]</a></li>"; // bootstrap 에 있는 pageBar 이다.
			}
			// 맨처음 페이지를 제외한 나머지 페이지에는 이전 버튼이 있다.
			
			
			while(!(loop > blockSize || pageNo > totalPage )) { // while 문을 빠져나오는 조건 2개
																// 페이지바를 만들어주는 것
				
				if(pageNo == Integer.parseInt(currentShowPageNo)) {	
					pageBar += "<li class='page-item active selected_page'><a class='page-link' href='#'>" + pageNo + "</a></li>"; // bootstrap 에 있는 pageBar 이다.
				}
				else {	
					pageBar += "<li class='page-item'><a class='page-link' href='itemList.run?searchType="+searchType+"&searchWord="+searchWord+"&sizePerPage="+sizePerPage+"&currentShowPageNo="+pageNo+"&category_id="+category_id+"'>" + pageNo + "</a></li>"; // bootstrap 에 있는 pageBar 이다.
				}
																									 
					
					loop++; // 1 2 3 4 5 6 7 8 9 10
					
					pageNo++; //  1  2  3  4  5  6  7  8  9 10
							  // 11 12 13 14 15 16 17 18 19 20
							  // 21 22 23 24 25 26 27 28 29 30
							  // 31 32 33 34 35 36 37 38 39 40
							  // 41 42
				
			} // end of while ---------------------------------------------------------------
			
			// **** [다음][마지막] 만들기 **** //
	
			if(pageNo <= totalPage) {
				pageBar += "<li class='page-item'><a class='page-link' href='itemList.run?searchType="+searchType+"&searchWord="+searchWord+"&sizePerPage="+sizePerPage+"&currentShowPageNo="+pageNo+"&category_id="+category_id+"'>[다음]</a></li>"; // bootstrap 에 있는 pageBar 이다.
			}
			// 마지막 페이지를 제외하고 나머지가 페이지에는 다음 버튼이 있다.
			// 반복문을 빠져나온 직후의 pageNo 는 11, 21, 31, 41 이 된다.
			
			pageBar += "<li class='page-item'><a class='page-link' href='itemList.run?searchType="+searchType+"&searchWord="+searchWord+"&sizePerPage="+sizePerPage+"&currentShowPageNo="+totalPage+"&category_id="+category_id+"'>[마지막]</a></li>"; // bootstrap 에 있는 pageBar 이다.
			
			request.setAttribute("pageBar", pageBar);
	
			
			// 카테고리 목록을 조회해오기  
			super.getCategoryList(request);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jeonym/item/itemList.jsp");	// 제품목록 페이지
		
		}
		
		else {
			
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우
			String message = "관리자만 접근이 가능합니다.";
			String loc = request.getContextPath() + "/main.run";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jeonym/msg.jsp");
		}

	}// end of execute() ---------------------------------------------------------------------------------------

}
