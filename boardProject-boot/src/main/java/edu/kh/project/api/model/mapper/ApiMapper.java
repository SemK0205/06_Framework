package edu.kh.project.api.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.api.model.dto.Api;

@Mapper
public interface ApiMapper {

	/** 전체 Todo 목록 조회
	 * @return
	 */
	List<Api> selectTodoList();

	/** Todo 추가
	 * @param todo
	 * @return
	 */
	int insertTodo(Api todo);

	/** 완료 수정
	 * @param todo
	 * @return
	 */
	int updateTodo(Api todo);

	/** 삭제
	 * @param todo
	 * @return
	 */
	int deleteTodo(Api todo);

	/** 제목 수정
	 * @param updateTodo
	 * @return
	 */
	int updateTodoTitle(Api updateTodo);

}
