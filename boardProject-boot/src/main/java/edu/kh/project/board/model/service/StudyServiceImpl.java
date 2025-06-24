package edu.kh.project.board.model.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.dto.Cal;
import edu.kh.project.board.model.mapper.StudyMapper;
import edu.kh.project.member.model.dto.Member;

@Service
@Transactional(rollbackFor = Exception.class)
public class StudyServiceImpl implements StudyService {
	
	@Autowired
	private StudyMapper mapper;
	
	@Override
	public List<Cal> StudyCalendarList(Member loginMember) throws Exception {

		return mapper.StudyCalendarList(loginMember);
	}
	

    @Override
    public void calendarSave(Cal vo) throws Exception {
        mapper.calendarSave(vo);
    }

    @Override
    public void calendarDelete(String no) throws Exception {
        mapper.calendarDelete(no);
    }

    @Override
    public void eventUpdate(Cal vo) throws Exception {
        mapper.eventUpdate(vo);
    }
    
    @Override
    public int bringStudyNo(int memberNo) {
    	return mapper.bringStudyNo(memberNo);
    }

}
