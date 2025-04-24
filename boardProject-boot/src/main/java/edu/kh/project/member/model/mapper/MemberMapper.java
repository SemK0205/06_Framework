package edu.kh.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(String memberEmail);

	int checkEmail(String memberEmail);

	int checkNickname(String memberNickname);

	int checkMemberTel(String memberTel);

	int memberSignUp(Member member);

	String checkEmail2(Member member);

	int findPw(Member member);

	int changeNewPw(Member member);

}
