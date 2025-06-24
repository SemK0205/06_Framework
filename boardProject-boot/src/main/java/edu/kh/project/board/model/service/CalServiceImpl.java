package edu.kh.project.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.model.dto.Cal;
import edu.kh.project.board.model.mapper.CalMapper;
import edu.kh.project.member.model.dto.Member;

@Service
@Transactional(rollbackFor = Exception.class)
public class CalServiceImpl implements CalService {

	@Autowired
    private CalMapper mapper;

    @Override
    public List<Cal> calendarList() throws Exception {
    	return mapper.calendarList();
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

	
}
