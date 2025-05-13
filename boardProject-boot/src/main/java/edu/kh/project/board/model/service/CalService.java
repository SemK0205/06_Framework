package edu.kh.project.board.model.service;

import java.util.List;

import edu.kh.project.board.model.dto.Cal;
import edu.kh.project.member.model.dto.Member;

public interface CalService {

	List<Cal> calendarList() throws Exception;

	void calendarSave(Cal vo) throws Exception;

	void calendarDelete(String no) throws Exception;

	void eventUpdate(Cal vo) throws Exception;

}
