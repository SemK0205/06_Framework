package edu.kh.demo.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter, Setter, toString 자동 생성
@NoArgsConstructor
public class MemberDTO {

  private String memberId;    // 아이디
  private String memberPw;    // 비밀번호
  private String memberName;  // 이름
  private int memberAge;      // 나이

}
