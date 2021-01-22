package com.todo.base.entity;


/**
 * 
 * お問い合わせ情報Entity
 * 実際のDBと同じ情報を持つ
 * @author suzuki
 *
 */


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
//エンティティは値を格納やセットするばしょ
//あとはInquiryForm.javaと同じ


//エンティティ formの違い
@Entity
//テーブル名はinauiry SQLは大文字小文字区別なし　DBと紐づけ
@Table(name = "INQUIRY")

public class InquiryEntity {
	
	//ユニークキー
	@Id
	//DBの列名がnameにはいっている　DBと紐づけ
	@Column(name = "INQUIRY_ID")
	//制約もSQLのテーブルと一緒にする、データベースの実態と一緒にする
	@NotNull
	//ここにsetされる
	private int inquiryId;
	
	@Column(name = "INQUIRY_TYPE")
	@NotNull
	private int inquiryType;

	@Column(name = "INQUIRY_USER")
	@NotNull
	private String inquiryUser;

	@Column(name = "INQUIRY_USER_MAIL")
	@NotNull
	private String inquiryUserMail;

	@Column(name = "INQUIRY_INFO")
	@NotNull
	private String inquiryInfo;

	@Column(name = "INQUIRY_TIME")
	@NotNull
	private Timestamp inquiryTime;
	public int getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(int inquiryId) {
		this.inquiryId = inquiryId;
	}

	public int getInquiryType() {
		return inquiryType;
	}

	public void setInquiryType(int inquiryType) {
		this.inquiryType = inquiryType;
	}

	public String getInquiryUser() {
		return inquiryUser;
	}

	public void setInquiryUser(String inquiryUser) {
		this.inquiryUser = inquiryUser;
	}


	public String getInquiryUserMail() {
		return inquiryUserMail;
	}

	public void setInquiryUserMail(String inquiryUserMail) {
		this.inquiryUserMail = inquiryUserMail;
	}

	public String getInquiryInfo() {
		return inquiryInfo;
	}

	public void setInquiryInfo(String inquiryInfo) {
		this.inquiryInfo = inquiryInfo;
	}
	
	public Timestamp getInquiryTime() {
		return inquiryTime;
	}

	public void setInquiryTime(Timestamp inquiryTime) {
		this.inquiryTime = inquiryTime;
	}
	
	//NotNull 
	//事前に入れるデータ
	@PrePersist
	public void onPrePersist() {						//タイムスタンプ型　にデート型をキャストしている
		setInquiryTime(new Timestamp((new Date()).getTime()));
	}
	

}
