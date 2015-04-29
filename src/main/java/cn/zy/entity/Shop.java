package cn.zy.entity;

import javax.persistence.Entity;

@Entity
public class Shop extends IdEntity {
	private String name;
	private Integer shopid;
	private String category;
	private String place;
	private String address;
	private String average;
	private String kouwei;
	private String huanjing;
	private String fuwu;
	private String reviewnum;
	private String rank;
	private String frompage;

	public Shop() {
		super();
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
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

	public String getReviewnum() {
		return reviewnum;
	}

	public void setReviewnum(String reviewnum) {
		this.reviewnum = reviewnum;
	}

	public String getFrompage() {
		return frompage;
	}

	public void setFrompage(String frompage) {
		this.frompage = frompage;
	}

	public Shop(String name, Integer shopid, String category, String place,
			String address, String average, String kouwei, String huanjing,
			String fuwu, String reviewnum, String rank, String frompage) {
		super();
		this.name = name;
		this.shopid = shopid;
		this.category = category;
		this.place = place;
		this.address = address;
		this.average = average;
		this.kouwei = kouwei;
		this.huanjing = huanjing;
		this.fuwu = fuwu;
		this.reviewnum = reviewnum;
		this.rank = rank;
		this.frompage = frompage;
	}

}