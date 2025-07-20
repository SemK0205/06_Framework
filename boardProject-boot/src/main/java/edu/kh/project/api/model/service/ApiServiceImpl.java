package edu.kh.project.api.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.api.model.dto.Api;
import edu.kh.project.api.model.mapper.ApiMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class ApiServiceImpl implements ApiService {

	@Autowired
	private ApiMapper mapper;
	
	@Override
	public List<Api> selectTodoList() {
		return mapper.selectTodoList();
	}

	@Override
	public int insertTodo(Api todo) {
		return mapper.insertTodo(todo);
	}

	@Override
	public int updateTodo(Api todo) {
		return mapper.updateTodo(todo);
	}

	@Override
	public int deleteTodo(Api todo) {
		return mapper.deleteTodo(todo);
	}

	@Override
	public int updateTodoTitle(Api updateTodo) {
		return mapper.updateTodoTitle(updateTodo);
	}

}
