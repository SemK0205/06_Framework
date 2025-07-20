package edu.kh.project.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.api.model.dto.Api;
import edu.kh.project.api.model.service.ApiService;

@RestController
@CrossOrigin(origins = "http://localhost:5174"/*, allowCredentials = "true"*/) 
// allowCredentials = "true" 클라이언트로부터 들어오는 쿠키 허용
@RequestMapping("api")
public class ApiController {
	
	@Autowired
	private ApiService service;
	
	private List<Api> todoList = new ArrayList<>();

    // Todo 전체 조회
    @GetMapping("/selectTodo")
    public List<Api> selectTodo() {
        return service.selectTodoList();
    }

    // Todo 추가
    @PostMapping("/insertTodo")
    public int insertTodo(@RequestBody Api todo) {
        todoList.add(todo);
        int result = service.insertTodo(todo);
        return result;
    }

    // Todo 수정
    @PostMapping("/updateTodo")
    public int updateTodo(@RequestBody Api todo) {
    	int result = service.updateTodo(todo);
        return result;
    }
    
    // Todo 제목 수정
    @PostMapping("/updateTodoTitle")
    public int updateTodoTitle(@RequestBody Api todo) {
		int todoId = todo.getTodoNo();
		String newTitle = todo.getTodoTitle();
		
		Api updateTodo = new Api();
		updateTodo.setTodoNo(todoId);
		updateTodo.setTodoTitle(newTitle);
		
		int result = service.updateTodoTitle(updateTodo);
		return result;
	}
    
    // Todo 삭제
    @PostMapping("/deleteTodo")
    public int deleteTodo(@RequestBody Api todo) {
    	int result = service.deleteTodo(todo);
    	return result;
    }

}
