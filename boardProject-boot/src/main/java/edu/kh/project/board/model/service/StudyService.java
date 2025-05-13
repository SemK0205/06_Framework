package edu.kh.project.board.model.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.kh.project.board.model.dto.Cal;
import edu.kh.project.member.model.dto.Member;

public interface StudyService {

	List<Cal> StudyCalendarList(Member loginMember) throws Exception;
	
	void calendarSave(Cal vo) throws Exception;

	void calendarDelete(String no) throws Exception;

	void eventUpdate(Cal vo) throws Exception;

	int bringStudyNo(int memberNo);

}
