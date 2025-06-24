package edu.kh.project.admin.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.member.model.dto.Member;

@Mapper
public interface AdminMapper {

	/** 관리자 정보 조회
	 * @param memberEmail
	 * @return
	 */
	Member login(String memberEmail);

	/** 최대 조회 수 게시글 조회
	 * @return
	 */
	Board maxReadCount();

	/** 최대 좋아요 수 게시글 조회
	 * @return
	 */
	Board maxLikeCount();

	/** 최대 댓글 수 게시글 조회
	 * @return
	 */
	Board maxCommentCount();

	/** 신규 회원 조회(7일 이내)
	 * @return
	 */
	List<Member> newMemberList();

	/** 탈퇴한 회원 조회
	 * @return
	 */
	List<Member> selectWithdrawnMemberList();

	/** 삭제한 게시글 조회
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

	/** 중복된 이메일 조회
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	/** 관리자 계정 생성
	 * @param member
	 * @return
	 */
	int createAdminAccount(Member member);

}
