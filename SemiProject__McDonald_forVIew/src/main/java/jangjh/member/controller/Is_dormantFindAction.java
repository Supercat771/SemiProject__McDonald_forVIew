package jangjh.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import jangjh.member.model.InterMemberDAO;
import jangjh.member.model.MemberDAO;

public class Is_dormantFindAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); //"GET" 또는 "POST"
		
		if("POST".equalsIgnoreCase(method)) {
			// 비밀번호 찾기 모달창에서 찾기 버튼을 클릭했을 경우
			
			String userid = request.getParameter("userid");
			String email = request.getParameter("email");
			
			InterMemberDAO mdao = new MemberDAO();
			
			Map<String, String> paraMap = new HashMap<>();
			
			paraMap.put("userid", userid);
			paraMap.put("email", email);
			
			boolean isUserExist = mdao.isUserExist(paraMap);
			
			boolean sendMailSuccess = false;// 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도
			
			if(isUserExist) {
				//회원으로 존재하는 경우
				
				//인증코드(인증키)를 랜덤하게 생성하도록 한다.
				Random rnd = new Random();
				
				String certificationCode = "";
				//인증코드(인증키)는 영문소문자 5글자 + 숫자 7글자로 만들겠습니다.
				//예: certificationCode ==> dkdkx1538456
				
				for(int i=0; i<5; i++) {
				/*
                min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
                int rndnum = rnd.nextInt(max - min + 1) + min;
                   영문 소문자 'a' 부터 'z' 까지 랜덤하게 1개를 만든다.     
				*/   
					
					char rndchar = (char)(rnd.nextInt('z' - 'a' + 1) + 'a');	
					
					certificationCode += rndchar;
				
				}//end of for(int i=0; i<5; i++)
				
				for(int i=0; i<7; i++) {
					
					int rndnum = rnd.nextInt(9 - 0 + 1) + 0;	
					
					certificationCode += rndnum;
					
				}//end of for(int i=0; i<7; i++)
				
				//System.out.println("~~~확인용 certificationCode =>" + certificationCode);
				//~~~확인용 certificationCode =>kzrcr1819879
				
				// 랜덤하게 생성한 인증코드(certificationCode)를 비밀번호 찾기를 하고자 하는 사용자의 email로 전송시킨다.
				GoogleMail mail = new GoogleMail();
				
				try {
					
				 mail.sendmail(email,certificationCode);
				 sendMailSuccess = true; //메일 전송이 성공했음을 기록함
				 
				 //세션불러오기
				 HttpSession session = request.getSession();
				 session.setAttribute("certificationCode", certificationCode);
				 //발급한 인증코드를 세션에 저장시킴!!
				 
				}catch(Exception e) {// 메일 전송이 실패한 경우
					System.out.println("~~~ 메일 전송이 실패함");
					e.printStackTrace();
					sendMailSuccess = false; //메일 전송이 실패했음을 기록함
				}
				
			}//end of if(isUserExist) ----------------------------------------------
			
			request.setAttribute("isUserExist", isUserExist);
			request.setAttribute("userid", userid);
			request.setAttribute("email", email);
			request.setAttribute("sendMailSuccess", sendMailSuccess);
			
		}//end of if("POST".equalsIgnoreCase(method)) ------------------------------
		
		request.setAttribute("method", method);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jangjh/login/is_dormantFind.jsp");
		
	}

}
