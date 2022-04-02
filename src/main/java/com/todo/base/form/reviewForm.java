package com.todo.base.form;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

//3 SpringBooot用にコンポーネントの目印をつける
@Component
public class reviewForm {

	
	//変数をつくる
		//データベースでいう番号が自動で降られていくもの
		//インクリメント
		@Id
		//列名
		@Column(name = "REVIEW_ID")
		@NotNull
		private int review_id;


		//ユーザーが入力するものを受け取る箱
		@NotNull
		@Size(min = 1, max = 60, message = "Titleは必須")
		@Column(name = "REVIEW_TITLE") 
		private String review_title;


		public int getReview_id() {
			return review_id;
		}


		public void setReview_id(int review_id) {
			this.review_id = review_id;
		}


		public String getReview_title() {
			return review_title;
		}


		public void setReview_title(String review_title) {
			this.review_title = review_title;
		}


		

	//getterとsetterを作成する
	
		//4.テーブルを作る
		/*
		 * CREATE TABLE REVIEW //テーブル名はREVIEW
		 *  (				//カラムを定義していく
		 *  REWIEW_ID INT(7) PRIMARY KEY AUTO_INCREMENT,//プライマリーキー、自動で++していく
		 *  REVIEW_TITLE VARCHAR(30) NOT NULL//タイトル必須、長さは30,CHARとの違いも今度抑える
		 *  );
		 *  CREATE TABLE REVIEW (REWIEW_ID INT(7) PRIMARY KEY AUTO_INCREMENT,REVIEW_TITLE VARCHAR(30));
		 */
		
	//5.HTMLをつくる
	
}
