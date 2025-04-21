package edu.kh.todo.model.service;

import java.util.List;
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

int changeComplete(Todo todo);

int todoUpdate(Todo todo);

int todoDelete(Todo todo);

/** 전체 할 일 개수 조회
 * @return totalCount
 */
int getTotalCount();

/** 완료된 할 일 개수 조회 및 출력
 * @return completeCount
 */
int getCompleteCount();

List<Todo> selectList();

int todoDelete2(int todoNo);
  
}
