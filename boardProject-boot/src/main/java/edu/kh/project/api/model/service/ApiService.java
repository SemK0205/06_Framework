package edu.kh.project.api.model.service;

import java.util.List;

import edu.kh.project.api.model.dto.Api;

public interface ApiService {

	/** 전체 Todo 목록 조회 서비스
	 * @return
	 */
	List<Api> selectTodoList();

	/** Todo 추가 서비스
	 * @param todo
	 * @return
	 */
	int insertTodo(Api todo);

	/** 완료 수정 서비스
	 * @param todo
	 * @return
	 */
	int updateTodo(Api todo);

	/** 삭제 서비스
	 * @param todo
	 * @return
	 */
	int deleteTodo(Api todo);

	/** 제목 수정 서비스
	 * @param updateTodo
	 * @return
	 */
	int updateTodoTitle(Api updateTodo);

}
