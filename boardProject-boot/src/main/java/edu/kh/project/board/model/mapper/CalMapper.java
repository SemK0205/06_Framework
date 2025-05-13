package edu.kh.project.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Cal;
import edu.kh.project.member.model.dto.Member;

@Mapper
public interface CalMapper {

	List<Cal> calendarList();

	void calendarSave(Cal vo);

	void calendarDelete(String no);

	void eventUpdate(Cal vo);

}
