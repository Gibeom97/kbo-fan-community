package com.semi.member.model.dto;

import com.semi.common.model.vo.Attachment;
import com.semi.member.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	private Member m;
	private Attachment at;

}
