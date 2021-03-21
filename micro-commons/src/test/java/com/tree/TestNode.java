package com.tree;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import micro.commons.tree.Node;

@Getter
@Setter
@ToString
public class TestNode extends Node {

	private static final long serialVersionUID = 3669312409033657785L;

	/**
	 * 货名
	 **/
	private String cargoInfoName;

	/**
	 * 货名编码
	 **/
	private String cargoInfoCode;

	/**
	 * 作业公司
	 **/
	private String companyCode;

	/**
	 * 作业公司名称
	 **/
	private String companyName;
	
	/**
	 * 进出口
	 **/
	private String inoutType;

	/**
	 * 内外贸
	 **/
	private String tradeType;

	/**
	 * 货主id
	 **/
	private Long shipperId;

	/**
	 * 货主名称
	 * **/
	private String shipperName;
	
	/**
	 * 货代id
	 **/
	private Long cargoAgentId;
	
	/**
	 * 货代名称
	 * **/
	private String cargoAgentName;

	/**
	 * 包装
	 **/
	private String packages;

	/**
	 * 唛头
	 **/
	private String mark;

	/**
	 * 规格
	 **/
	private String spec;
}
