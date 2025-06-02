package edu.kh.project.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.project.admin.model.service.AdminService;
import edu.kh.project.board.model.dto.Board;
import edu.kh.project.member.model.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:5173"/*, allowCredentials = "true"*/) 
// allowCredentials = "true" 클라이언트로부터 들어오는 쿠키 허용
@RequestMapping("admin")
@SessionAttributes({"loginMember"})
@Slf4j
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	@PostMapping("login")
	public Member login(@RequestBody Member inputMember, Model model) {
		
		Member loginMember = service.login(inputMember);
		
		if( loginMember == null) {
			return null;
		}
		model.addAttribute("loginMember",loginMember);
		
		
		return loginMember;
	}
	
	@GetMapping("logout")
	public ResponseEntity<String> logout(HttpSession session) {
		
		// ResponseEntity
		// Spring 에서 제공하는 Http 응답 데이터를
		// 커스터마이징 할 수 있도록 지원하는 클래스
		// -> HTTP 상태코드, 헤더, 응답 본문(body)을 모두 설정 가능
		try {
			session.invalidate(); // 세션 무효화 처리
			return ResponseEntity.status(HttpStatus.OK)
					.body("로그아웃이 완료되었습니다."); // 200
		} catch (Exception e) {
			// 세션 무효화 중 예외 발생한 경우
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("로그아웃 중 예외발생 : "+e.getMessage());
		}
		
	}
	
	// ---------- 통계 ----------
	
	/** 최대 조회수 게시글 조회
	 * @return
	 */
	@GetMapping("maxReadCount")
	public ResponseEntity<?> maxReadCount(){
		try {
			Board board = service.maxReadCount();
			return ResponseEntity.status(HttpStatus.OK).body(board);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/** 최대 좋아요 게시글 조회
	 * @return
	 */
	@GetMapping("maxLikeCount")
	public ResponseEntity<?> maxLikeCount(){
		try {
			Board board = service.maxLikeCount();
			return ResponseEntity.status(HttpStatus.OK).body(board);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	/** 최대 댓글 게시글 조회
	 * @return
	 */
	@GetMapping("maxCommentCount")
	public ResponseEntity<?> maxCommentCount(){
		try {
			Board board = service.maxCommentCount();
			return ResponseEntity.status(HttpStatus.OK).body(board);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	/** 신규 회원 조회(7일 이내)
	 * @return
	 */
	@GetMapping("newMemberList")
	public ResponseEntity<List<Member>> newMemberList(){
		try {
			List<Member> member = service.newMemberList();
			return ResponseEntity.status(HttpStatus.OK).body(member);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	// -------------------- 복구 ------------------------
	
	@GetMapping("withdrawnMemberList")
	public ResponseEntity<Object> selectWithdrawnMemberList(){
		// 성공 시 List<Member> 반환, 실패 시 String 반환 -> Object 사용
		try {
			List<Member> withdrawnMemberList = service.selectWithdrawnMemberList();
			return ResponseEntity.status(HttpStatus.OK).body(withdrawnMemberList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("탈퇴한 회원 목록 조회 중 문제 발생 : " + e.getMessage());
		}
	}
	
	@GetMapping("deleteBoardsList")
	public ResponseEntity<Object> selectDeleteBoardsList(){
		// 성공 시 List<Member> 반환, 실패 시 String 반환 -> Object 사용
		try {
			List<Board> deleteBoardsList = service.selectDeleteBoardsList();
			return ResponseEntity.status(HttpStatus.OK).body(deleteBoardsList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제된 게시글 목록 조회 중 문제 발생 : " + e.getMessage());
		}
	}
	
	@PutMapping("restoreMember")
	public ResponseEntity<String> restoreMember(@RequestBody Member member){
		try {
			int result = service.restoreMember(member);
			
			if(result > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(member.getMemberNo()+"번 회원 복구 완료"); // 400
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지않은 memberNo : " + member.getMemberNo()); // 400
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("탈퇴 회원 복구 중 문제 발생 : "+e.getMessage());
		}
	}
	
	@PutMapping("restoreBoard")
	public ResponseEntity<String> restoreBoard(@RequestBody Board board){
		try {
			int result = service.restoreBoard(board);
			
			if(result > 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(board.getBoardNo()+"번 게시글 복구 완료"); // 400
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지않은 boardNo : " + board.getBoardNo()); // 400
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("탈퇴 회원 복구 중 문제 발생 : "+e.getMessage());
		}
	}
	
	@GetMapping("getAuthorityMemberList")
	public ResponseEntity<?> getAuthorityMemberList(){
		try {
			List<Member> getAuthorityMemberList = service.getAuthorityMemberList();
			return ResponseEntity.status(HttpStatus.OK).body(getAuthorityMemberList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("관리자 목록 불러오기 중 문제 발생 : "+e.getMessage());
		}
	}
	
	// ----------관리자 계정생성--------------
	
	@PostMapping("createAdminAccount")
	public ResponseEntity<?> createAdminAccount(@RequestBody
			Member member){
		try {
			
			// 1. 기존에 있는 이메일인지 검사
			int checkEmail = service.checkEmail(member.getMemberEmail());
			
			// 2. 있으면 발급 안함
			if(checkEmail > 0) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 닉네임입니다."); // 409 : 요청이 서버의 현재 상태와 충돌할 때 사용
			}
			
			// 3. 없으면 새로 발급
			String createAdminAccount = service.createAdminAccount(member);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(createAdminAccount); // 201 : 자원이 성공적으로 생성되었음을 나타냄
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("관리자 계정 생성 중 문제 발생(서버 문의 바람)");
		}
	}
	

}
