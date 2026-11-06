package com.semi.board.model.dto;


import com.semi.board.model.vo.Board;
import com.semi.common.model.vo.Attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardDTO {
	private Board b;
	private Attachment at;
}
