package edu.kh.project.admin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.admin.model.mapper.AdminMapper;
import edu.kh.project.board.model.dto.Board;
import edu.kh.project.common.util.Utility;
import edu.kh.project.member.model.dto.Member;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Override
	public Member login(Member inputMember) {
		
		// bCypte를 이용한 비밀번호 암호화 비교
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		if(loginMember == null) {
			return null;
		}
		
		if(!bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		
		loginMember.setMemberPw(null);
		
		
		return loginMember;
	}

	@Override
	public Board maxReadCount() {
		return mapper.maxReadCount();
	}

	@Override
	public Board maxLikeCount() {
		return mapper.maxLikeCount();
	}

	@Override
	public Board maxCommentCount() {
		return mapper.maxCommentCount();
	}

	@Override
	public List<Member> newMemberList() {
		return mapper.newMemberList();
	}

	@Override
	public List<Member> selectWithdrawnMemberList() {
		return mapper.selectWithdrawnMemberList();
	}

	@Override
	public List<Board> selectDeleteBoardsList() {
		return mapper.selectDeleteBoardsList();
	}

	@Override
	public int restoreMember(Member member) {
		return mapper.restoreMember(member);
	}

	@Override
	public int restoreBoard(Board board) {
		return mapper.restoreBoard(board);
	}

	@Override
	public List<Member> getAuthorityMemberList() {
		return mapper.getAuthorityMemberList();
	}

	@Override
	public String createAdminAccount(Member member) {
		
		// 1. 랜덤 비밀번호 생성
		// 영어(대소문자), 숫자도 포함 6자리 난수로 만든 비밀번호를 암호화 한 값 구하기
		String rawPw = Utility.generatePassword();
		
		// 2. 평문 비밀번호를 암호화 하여 저장
		String encPw = bcrypt.encode(rawPw);
		
		// 3. member에 암호화된 비밀번호 세팅
		member.setMemberPw(encPw);
		
		// 4. DB에 암호화된 비밀번호가 세팅된 member를 전달하여
		//		계정 발급
		int result = mapper.createAdminAccount(member);
		
		// 5. 계정 발급 정상처리 되었다면, 발급된 평문 비밀번호 리턴
		if(result > 0) {
			return rawPw;
		}
		return null;
		
	}

	@Override
	public int checkEmail(String memberEmail) {
		return mapper.checkEmail(memberEmail);
	}

}
