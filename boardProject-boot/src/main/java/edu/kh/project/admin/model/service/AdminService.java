package edu.kh.project.admin.model.service;

import java.util.List;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.member.model.dto.Member;

public interface AdminService {

	/** 관리자 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);

	/** 최대 조회 수 게시글 조회 서비스
	 * @return
	 */
	Board maxReadCount();

	/** 최대 좋아요 수 게시글 조회 서비스
	 * @return
	 */
	Board maxLikeCount();

	/** 최대 댓글 수 게시글 조회 서비스
	 * @return
	 */
	Board maxCommentCount();

	/** 신규 회원 조회 서비스(7일 이내)
	 * @return
	 */
	List<Member> newMemberList();

	/** 탈퇴한 회원 목록 조회
	 * @return
	 */
	List<Member> selectWithdrawnMemberList();

	/** 삭제한 게시글 목록 조회
	 * @return
	 */
	List<Board> selectDeleteBoardsList();

	/** 탈퇴한 회원 복구
	 * @param member
	 * @return
	 */
	int restoreMember(Member member);

	/** 삭제한 게시글 복구
	 * @param board
	 * @return
	 */
	int restoreBoard(Board board);

	/** 관리자 목록 조회
	 * @return
	 */
	List<Member> getAuthorityMemberList();

	String createAdminAccount(Member member);

	/** 중복된 이메일인지 검사
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail); 
}
