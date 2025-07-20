package edu.kh.project.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api {
	
	private int todoNo;
	private String todoTitle;
	private String todoContent;
	private String complete;

}
