package com.tl.job007.regex;

import com.tl.job007.utils.RegexUtil;

public class RegexTest {
	public static void testUserParser() {
		String line = "User [id=1107717945, screenName=null, name=null, province=0, city=0, location=null, description=null, url=null, profileImageUrl=null, userDomain=null, gender=null, followersCount=0, friendsCount=0, statusesCount=0, favouritesCount=0, createdAt=null, following=false, verified=false, verifiedType=0, allowAllActMsg=false, allowAllComment=false, followMe=false, avatarLarge=null, onlineStatus=0, status=null, biFollowersCount=0, remark=���ز�, lang=null, verifiedReason=null]";
		String regex = "id=(\\d+),";
		String regex2 = "remark=(.*?),";
		System.out.println(RegexUtil.getRegexValue(line, regex, 1));
		System.out.println(RegexUtil.getRegexValue(line, regex2, 1));
	}

	public static void main(String[] args) {
		// String line =
		// "<comment><content>恭喜。 //@华远会:恭喜获得任总签名新书，请私信我们您的通讯地址及联系方式，只要是华远会粉丝，转发微博&说说您眼中的任志强，就有机会哦~~[可爱]</content><time>2012-4-5 21:29:23</time><repostsCount>45</repostsCount><commentsCount>52</commentsCount></comment>";
		String line = "<comment><content>密鑼緊鼓地準備的「NIKE慈善籃球賽 - 橫洲工業vs LOVE LIFE」門票正式開售！ $50、$100及$300的門票將於2月25日下午2時開始在灣仔修頓室內運動場發售。 購買$300門票更可獲贈紀念TEE一件，數量有限，先到先得。 希望大家多多支持，2月29日晚上八時灣仔修頓室內場見！ @黑人建州 @橫洲工業</content><time>2012-2-20 22:40:27</time><repostsCount>468</repostsCount><commentsCount>331</commentsCount></comment>";
		String regex = "<content>([\\s\\S]*?)</content>";
		String oldMatchContent = RegexUtil.getRegexValue(line, regex, 1);
		String newMatchContent = oldMatchContent.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
				.replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
		
		line = line.replace(oldMatchContent, newMatchContent);
		
		System.out.println(line);
	}
}
