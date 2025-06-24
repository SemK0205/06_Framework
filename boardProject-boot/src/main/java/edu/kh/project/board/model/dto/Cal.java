package edu.kh.project.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cal {
        private Long calendarNo;
        private String title;
        private String start1;
        private String end;
        private int memberNo;
        private int studyNo;
}