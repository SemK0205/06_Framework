package edu.kh.project.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;

@Mapper
public interface MyPageMapper {

	int updateInfo(Member inputMember);

	int changePw(Map<String, String> paramMap);

	String selectPw(int memberNo);

	int secession(int memberNo);

	int insertUploadFile(UploadFile uf);

	List<UploadFile> fileList(int memberNo);

	int profile(Member member);

}
