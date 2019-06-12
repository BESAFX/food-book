package com.besafx.app.dto;

import lombok.Data;

@Data
public class Paging {

	private Integer pageIndex;
	private Integer pageSize;
	private Integer total;

}
