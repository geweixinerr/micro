package com.tree;

import java.util.ArrayList;
import java.util.List;

import micro.commons.tree.Node;
import micro.commons.util.JSONUtils;
import micro.commons.util.TreeUtils;

public class Test {

	public static void main(String[] args) {
		
		TestNode t1 = new TestNode();
		t1.setId("001");
		t1.setSortNum(1);
		t1.setCargoInfoName("铁矿石");
		t1.setCargoInfoCode("000001001");
		t1.setCompanyCode("000001");
		t1.setCompanyName("大港分公司");
		t1.setInoutType("01");
		t1.setTradeType("01");
		t1.setShipperId(1024L);
		t1.setShipperName("镇江钢铁");
		t1.setCargoAgentId(1025L);
		t1.setCargoAgentName("镇江钢铁货代");
		t1.setPackages("01");
		t1.setSpec("01");
		t1.setMark("01");
		t1.setParentId("0");
		
		TestNode t2 = new TestNode();
		t2.setId("003");
		t2.setSortNum(1);
		t2.setCargoInfoName("铁矿石第一级");
		t2.setCargoInfoCode("000001001");
		t2.setCompanyCode("000001");
		t2.setCompanyName("大港分公司");
		t2.setInoutType("01");
		t2.setTradeType("01");
		t2.setShipperId(1024L);
		t2.setShipperName("镇江钢铁");
		t2.setCargoAgentId(1025L);
		t2.setCargoAgentName("镇江钢铁货代");
		t2.setPackages("01");
		t2.setSpec("01");
		t2.setMark("01");
		t2.setParentId("001");
		
		TestNode t3 = new TestNode();
		t3.setId("004");
		t3.setSortNum(1);
		t3.setCargoInfoName("铁矿石第三级");
		t3.setCargoInfoCode("000001001");
		t3.setCompanyCode("000001");
		t3.setCompanyName("大港分公司");
		t3.setInoutType("01");
		t3.setTradeType("01");
		t3.setShipperId(1024L);
		t3.setShipperName("镇江钢铁");
		t3.setCargoAgentId(1025L);
		t3.setCargoAgentName("镇江钢铁货代");
		t3.setPackages("01");
		t3.setSpec("01");
		t3.setMark("01");
		t3.setParentId("003");

		TestNode t4 = new TestNode();
		t4.setId("002");
		t4.setSortNum(1);
		t4.setCargoInfoName("铁矿石第三级");
		t4.setCargoInfoCode("000001001");
		t4.setCompanyCode("000001");
		t4.setCompanyName("大港分公司");
		t4.setInoutType("01");
		t4.setTradeType("01");
		t4.setShipperId(1024L);
		t4.setShipperName("镇江钢铁");
		t4.setCargoAgentId(1025L);
		t4.setCargoAgentName("镇江钢铁货代");
		t4.setPackages("01");
		t4.setSpec("01");
		t4.setMark("01");
		t4.setParentId("004");
		
		
		TestNode t6 = new TestNode();
		t6.setId("001001");
		t6.setSortNum(1);
		t6.setCargoInfoName("铁矿石");
		t6.setCargoInfoCode("000001001");
		t6.setCompanyCode("000001");
		t6.setCompanyName("大港分公司");
		t6.setInoutType("01");
		t6.setTradeType("01");
		t6.setShipperId(1024L);
		t6.setShipperName("镇江钢铁");
		t6.setCargoAgentId(1025L);
		t6.setCargoAgentName("镇江钢铁货代");
		t6.setPackages("01");
		t6.setSpec("01");
		t6.setMark("01");
		t6.setParentId("1111111111");
		
		TestNode t7 = new TestNode();
		t7.setId("001002");
		t7.setSortNum(1);
		t7.setCargoInfoName("铁矿石");
		t7.setCargoInfoCode("000001001");
		t7.setCompanyCode("000001");
		t7.setCompanyName("大港分公司");
		t7.setInoutType("01");
		t7.setTradeType("01");
		t7.setShipperId(1024L);
		t7.setShipperName("镇江钢铁");
		t7.setCargoAgentId(1025L);
		t7.setCargoAgentName("镇江钢铁货代");
		t7.setPackages("01");
		t7.setSpec("01");
		t7.setMark("01");
		t7.setParentId("001001");
		
		TestNode t8 = new TestNode();
		t8.setId("010");
		t8.setSortNum(1);
		t8.setCargoInfoName("铁矿石第三级");
		t8.setCargoInfoCode("000001001");
		t8.setCompanyCode("000001");
		t8.setCompanyName("大港分公司");
		t8.setInoutType("01");
		t8.setTradeType("01");
		t8.setShipperId(1024L);
		t8.setShipperName("镇江钢铁");
		t8.setCargoAgentId(1025L);
		t8.setCargoAgentName("镇江钢铁货代");
		t8.setPackages("01");
		t8.setSpec("01");
		t8.setMark("01");
		t8.setParentId("001");
		
		List<Node> listNode = new ArrayList<>();
		listNode.add(t1);
		listNode.add(t2);
		listNode.add(t3);
		listNode.add(t4);
		listNode.add(t6);
		listNode.add(t7);
		listNode.add(t8);
		
		List<Node> list1 = TreeUtils.parse(listNode);
		System.out.println(JSONUtils.NON_NULL.toJSONString(list1));
		List<Node> list = TreeUtils.twoDimensionParse(list1);
		System.out.println(JSONUtils.NON_NULL.toJSONString(list));
	}
}
