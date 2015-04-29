package cn.zy.util;

public class Patterns {
	public static final int depth = 3;

	public final static String index = "http://www.dianping.com";
	public final static String meishi = "http://www.dianping.com/search/category/10/10";

	public final static String lianjie = "href=\"([/h].+/10/10/[^ ]+)\"";
	public final static String allAttr = "sml-str.+title=\"(.+)\"[\\d\\D]+?<b>(\\d+)</b>[\\d\\D]+?条点评[\\d\\D]+?人均[\\d\\D]+?(?:<b>.{1}(\\d+)</b>|-)[\\d\\D]+?<span class=\"tag\">(.*)</span>[\\d\\D]+?<span class=\"tag\">(.*)</span>[\\d\\D]+?<span class=\"addr\">(.*)</span>[\\d\\D]+?口味<b>(.*)</b>[\\d\\D]+?环境<b>(.*)<\\/b>[\\d\\D]+?服务<b>(.*)</b>[\\d\\D]+?data-name=\"(.+)\"[\\d\\D]+?data-shopid=\"(.+)\"";

	public final static String isvip = "icon-vip[\\d\\D]+?member/(.+)\"";
	public final static String userid = "user-id=\"(.+)\" [\\d\\D]+?href=\"/member.+>(.+)</a>[\\d\\D]+?urr-rank(.+)\" [\\d\\D]+?irr-star(.+)0\"[\\d\\D]+?口味(.+)<em[\\d\\D]+?环境(.+)<em[\\d\\D]+?服务(.+)<em[\\d\\D]+?J_brief-cont\">([\\d\\D]+?)</div>[\\d\\D]+?time\">(.+)</span>";
	public final static String pageno = "pageno=(.+)\" d";
}
