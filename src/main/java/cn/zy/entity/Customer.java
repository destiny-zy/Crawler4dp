package cn.zy.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Customer extends IdEntity {
	private Integer userid;
	private String name;
	private String rank;
	private String isvip;
	private String pingfen;
	private String kouwei;
	private String huanjing;
	private String fuwu;
	private String content;
	private String time;
	private Shop shop;
	private String frompage;

	public Customer(Integer userid, String name, String rank, String isvip,
			String pingfen, String kouwei, String huanjing, String fuwu,
			String content, String time, Shop shop, String frompage) {
		super();
		this.userid = userid;
		this.name = name;
		this.rank = rank;
		this.isvip = isvip;
		this.pingfen = pingfen;
		this.kouwei = kouwei;
		this.huanjing = huanjing;
		this.fuwu = fuwu;
		this.content = content;
		this.time = time;
		this.shop = shop;
		this.frompage = frompage;
	}

	public String getFrompage() {
		return frompage;
	}

	public void setFrompage(String frompage) {
		this.frompage = frompage;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getIsvip() {
		return isvip;
	}

	public void setIsvip(String isvip) {
		this.isvip = isvip;
	}

	public String getPingfen() {
		return pingfen;
	}

	public void setPingfen(String pingfen) {
		this.pingfen = pingfen;
	}

	public String getKouwei() {
		return kouwei;
	}

	public void setKouwei(String kouwei) {
		this.kouwei = kouwei;
	}

	public String getHuanjing() {
		return huanjing;
	}

	public void setHuanjing(String huanjing) {
		this.huanjing = huanjing;
	}

	public String getFuwu() {
		return fuwu;
	}

	public void setFuwu(String fuwu) {
		this.fuwu = fuwu;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@ManyToOne
	@JoinColumn(name = "shop_id", referencedColumnName = "shopid")
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}
