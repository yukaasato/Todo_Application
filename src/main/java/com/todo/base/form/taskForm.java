package com.todo.base.form;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.sun.istack.NotNull;

/**
 * フォームからの入力値をまとめるクラス
 */

@Component
public class taskForm {

		//インクリメント
		@Id
		//列名
		@Column(name = "TODO_ID")
		@NotNull
		private int todo_id;
		
	
		@NotNull
		@Size(min = 1, max = 60, message = "※タイトルが未入力です")
		@Column(name = "TODO_TITLE")
		private String todo_title;	
		
		@Column(name = "TODO_DATE")
		private String todo_date;
		

		
		@Column(name = "TODO_TIME") 
		private String todo_time;
		 
		

		@Column(name = "TODO_PLACE")
		private String todo_place;

		
/*
		 * @Column(name = "CREATE_TIME") private String create_time;
		 * 
		 * 
		 * @Column(name = "COMPLETE_TIME") private String complete_time;
		 * 
		 */		
		@Column(name = "DELETE_FLAG")
		private int delete_flag;
		
		
		@Column(name = "COMPLETE_FLAG")
		private int complete_flag;


		public int getTodo_id() {
			return todo_id;
		}


		public void setTodo_id(int todo_id) {
			this.todo_id = todo_id;
		}


		public String getTodo_title() {
			return todo_title;
		}


		public void setTodo_title(String todo_title) {
			this.todo_title = todo_title;
		}


		public String getTodo_date() {
			return todo_date;
		}


		public void setTodo_date(String todo_date) {
			this.todo_date = todo_date;
		}


		public String getTodo_place() {
			return todo_place;
		}


		public void setTodo_place(String todo_place) {
			this.todo_place = todo_place;
		}


		public int getDelete_flag() {
			return delete_flag;
		}


		public void setDelete_flag(int delete_flag) {
			this.delete_flag = delete_flag;
		}


		public int getComplete_flag() {
			return complete_flag;
		}


		public void setComplete_flag(int complete_flag) {
			this.complete_flag = complete_flag;
		}


		public String getTodo_time() {
			return todo_time;
		}


		public void setTodo_time(String todo_time) {
			this.todo_time = todo_time;
		}








		
}
