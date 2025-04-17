package edu.kh.todo.model.service;

import java.util.Map;

import edu.kh.todo.model.dto.Todo;

public interface TodoService {


  String testTitle();

Map<String, Object> selectAll();

/** 할 일 추가
 * @param todoTitle
 * @param todoContent
 * @return
 */
int addTodo(String todoTitle, String todoContent);

/** 할 일 상세조회
 * @param todoNo
 * @return todo
 */
Todo todoDetail(int todoNo);
  
}
