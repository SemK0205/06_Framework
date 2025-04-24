package edu.kh.project.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("member")
@Slf4j
public class MemberController {

	@Autowired
	private MemberService service;
	
	/*
	 * [로그인]
	 * - 특정 사이트에 아이디/비밀번호 등을 입력해서
	 * 	 해당 정보가 DB에 있으면 조회/서비스 이용
	 * 
	 * - 로그인 한 회원 정보는 session에 기록하여
	 * 로그아웃 또는 브라우저 종료 시 까지
	 * 해당 정보를 계속 이용할 수 있게 함
	 * 
	 */
	
	/** 로그인
	 * @param inputMember : 커맨드 객체 (@ModelAttribute 생략)
	 * 						memberEmail, memberPw 세팅된 상태
	 * @param ra : 리다이렉트 시 request scope -> session scope -> request 로 데이터 전달
	 * @param model : 데이터 전달용 객체(기본 request scope)
	 * 				/ (@SessionAttributes 어노테이션과 함께 사용 시 session scope 이동)
	 * @return 
	 */
	@PostMapping("login")
	public String login(Member inputMember, RedirectAttributes ra, Model model,
				@RequestParam(value="saveId", required = false) String saveId,
				HttpServletResponse resp) {
		
		// 체크박스
		
		// 로그인 서비스 호출
		Member loginMember = service.login(inputMember);
		
		// 로그인 실패 시
		if(loginMember == null) {
			ra.addFlashAttribute("message", "이메일 또는 비밀번호가 일치하지 않습니다.");
		} else {
			// 로그인 성공 시
			
			// session scope에 loginMember 추가
			model.addAttribute("loginMember", loginMember);
			// 1단계 : model을 이용하여 request scope에 세팅됨
			// 2단계 : 클래스 위에 @SessionAttributes() 어노테이션 작성하여
			//			session scope로 loginMember를 이동
			
			// ************* Cookie *****************
			// 로그인 시 작성한 이메일 저장 (쿠키에)
			
			// 쿠키 객체 생성(k:v)
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			// saveId=user01@kh.or.kr
			
			// 쿠키가 적용될 경로 설정
			// -> 클라이언트가 어떤 요청을 할 때 쿠키를 첨부할지 지정
			// ex) "/" : IP 또는 도메인 또는 localhost
			//			--> 메인페이지 + 그 하위 주소 모두
			cookie.setPath("/");
			
			// 쿠키의 만료 기간 지정
			if(saveId != null) { // 아이디 저장 체크박스 체크했을 때
				cookie.setMaxAge(60 * 60 * 24 * 30); // 30일 (초단위로 지정)
				
			} else { // 체크 안했을 때
				cookie.setMaxAge(0); // 0초 (클라이언트 쿠키 삭제)
				
			}
			
			// 응답 객체에 쿠키 추가 -> 클라이언트에게 전달
			resp.addCookie(cookie);
			
		}
		
		
		
		return "redirect:/"; // 메인페이지로 재요청
	}
	
	/** 로그아웃 : session에 저장된 로그인된 회원 정보를 없앰
	 * @param SessionStatus : @SessionAttributes로 지정된 특정 속성을 세션에서 제거 기능 제공 객체
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete(); // 세션을 완료 시킴 ( == @SessionAttributes로 등록된 세션 제거)
		
		return "redirect:/";
	}
	
	/** 회원 가입 페이지로 이동
	 * @return
	 */
	@GetMapping("signup")
	public String signupPage() {
		return "member/signup";
	}
	
	/** 이메일 중복검사(비동기 요청)
	 * @return 중복된 데이터의 개수
	 */
	@ResponseBody // 응답 본문(fetch)으로 돌려보냄
	@GetMapping("checkEmail") // GET 방식 요청 /member/checkEmail
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		
		int result = service.checkEmail(memberEmail);
		
		return result;
	}
	
	/** 닉네임 중복검사(비동기 요청)
	 * @param memberNickname
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("memberNickname") String memberNickname) {
		return service.checkNickname(memberNickname);
	}

	/** 전화번호 중복검사(비동기 요청)
	 * @param memberTel
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkMemberTel")
	public int checkMemberTel(@RequestBody Map<String, String> map) {
		String memberTel = map.get("memberTel");
		return service.checkMemberTel(memberTel);
	}
	
	/** 회원가입
	 * @param member : 입력된 회원 정보(memberEmail, memberPw, memberNickname, memberTel, memberAddress(따로처리)
	 * @param ra : 리다이렉트 시 request -> session -> request 로 데이터 전달하는 객체
	 * @param memberAddress : 입력한 주소 input 3개의 값을 배열로 전달 [우편번호, 도로명/지번주소, 상세주소]
	 * @return
	 */
	@PostMapping("signup")
	public String memberSignUp(Member member, 
			@RequestParam("memberAddress") String[] memberAddress,
			RedirectAttributes ra) {
		
//		log.debug("member : " + member);
//		log.debug("Address : " + memberAddress);
		
		// 회원가입 서비스 호출
		int result = service.memberSignUp(member, memberAddress);
		
		String path = null;
		String message = null;
		
		if(result > 0) { // 성공
			path = "/";
			message = member.getMemberNickname()+"님의 가입을 환영합니다.";
			
		} else { // 실패
			message = "회원가입 실패..";
			path = "signup";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:"+path;
		
	}
	
	@GetMapping("findId")
	public String findId() {
		return "member/findId";
	}
	
	@GetMapping("findPw")
	public String findPw() {
		return "member/findPw";
	}
	
	@PostMapping("findId")
	public String findId(Member member, RedirectAttributes ra) {
		
		String result = service.checkEmail2(member);
		String message = null;
		if(result != null) {
			ra.addFlashAttribute("memberEmail", result);
		
		} else {
			message = "해당 정보와 일치하는 아이디가 없습니다.";
		}
		ra.addFlashAttribute("message",message);
		return "redirect:/member/findId";
	}
	
	@ResponseBody
	@PostMapping("findPw")
	public int findPw(Member member) {
		
		return service.findPw(member);
	}

	@PostMapping("findPw2")
	public String findPw2(Member member, RedirectAttributes ra) {
		
		int result = service.changeNewPw(member);
		
		String message = null;
		String path = null;
		
		if(result>0) {
			message = "새로운 비밀번호로 로그인 해 주세요.";
			path ="/";
		} else {
			message = "다시 입력해 주세요";
			path ="/member/findPw";
		}

		ra.addFlashAttribute("message",message);
		
		return "redirect:"+path;
	}
	
  
}