package edu.kh.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Spring EL 같은 경우 DTO 객체 출력할 때 getter 가 필수 작성 되어있어야 함!
// -> ${Student.name} == ${Student.getName()}

@Data // setter/getter, toString(), equals(), hashCode() 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자 자동 생성
@Builder // Builder 패턴을 위한 어노테이션
public class Student {

  private String studentNo; // 학생 번호
  private String Name;      // 이름
  private int age;          // 나이

}