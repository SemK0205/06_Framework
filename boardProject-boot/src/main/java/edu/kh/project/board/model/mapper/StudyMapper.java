package edu.kh.project.board.model.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Cal;
import edu.kh.project.member.model.dto.Member;

@Mapper
public interface StudyMapper {

	List<Cal> StudyCalendarList(Member loginMember);

	void calendarSave(Cal vo);

	void calendarDelete(String no);

	void eventUpdate(Cal vo);

	int bringStudyNo(int memberNo);
}
