package com.tl.job007.xml.parser;

import org.dom4j.DocumentException;

import com.tl.job007.utils.Dom4JUtil;

public class Dom4JTest {
	public static void main(String[] args) throws DocumentException {
		String xmlString = "<comment><content>姜汝祥博士上午到万通用了三个小时与我们分享了企业云管理，通过移动、互动、行动建立员工的互信互助，大大降低内部交易成本，革命性的改变企业的管理。万通的MIP从今天开始，今天值得铭记。@姜汝祥</content><time>2012-3-13 13:10:50</time><repostsCount>72</repostsCount><commentsCount>23</commentsCount></comment>";
		
		System.out.println(Dom4JUtil.getElementTextByRoot(xmlString, "content"));
		// 这行是为了格式化美观而存在
		System.out.println();
	}
}
