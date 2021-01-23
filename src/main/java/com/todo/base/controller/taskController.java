package com.todo.base.controller;

import java.sql.Date;
import java.sql.Time;
//import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo.base.URL;
import com.todo.base.entity.taskEntity;
import com.todo.base.form.taskForm;
import com.todo.base.service.taskAccessService;

@Controller
//変数使う、汎用性高い
//GetPostに関わらずこれを経由する、URLが長い時に便利
@RequestMapping(URL.TODO)
public class taskController {

	@Autowired
	taskAccessService taskAcsServ;


	//ホーム
	@GetMapping(URL.LIST)
	//litFormを作る
	public String displayList(Model model,taskForm taskForm) {

		//DBからデータ探してくる
		List<taskEntity> todolists =taskAcsServ.searchAll();

		model.addAttribute("todolists",todolists);

		//タイトル追加
		model.addAttribute("title", "toDoList");

		//http://localhost:8080/todo/list
		return URL.TODO + URL.LIST;

	}

	//作成押下時
	@PostMapping(URL.CREATE)
	public String complete( @Validated taskForm taskForm, 
			Model model) {	

		//エンティティのインスタンス作成
		taskEntity taskEntity = new taskEntity();

		//エンティティにタイトルを渡す
		taskEntity.setTodo_title(taskForm.getTodo_title());

		//日付を入れる（今の）
		//taskEntity.setTodo_date(taskEntity.getTodo_date());

		//DBへインサートする
		taskAcsServ.taskInsert(taskEntity);


		model.addAttribute("title", "toDoList");


		//http://localhost:8080/todo/list
		//return URL.TODO + URL.LIST;
		return "redirect:/todo/list";

	}

	/*
	 * 詳細画面へ遷移
	 * データを一件取得する処理
	 * データベースにはtodo_idをformから受け取り条件式Whereで絞り込む
	 * */
	@GetMapping(URL.DETAILS)
	public String displayDtl(taskForm taskForm, Model model) {

		//決まったデータ表示
		taskEntity oneTask = taskAcsServ.findTask(taskForm.getTodo_id());

		//タイトル
		model.addAttribute("title","My Task");

		//Entityを渡す
		model.addAttribute("oneTask", oneTask);

		//詳細画面へ
		return URL.TODO + URL.DETAILS;


	}

	/**
	 * 詳細画面からホームに戻ったときの処理
	 * @param Formに入っているものを保持
	 * @param model　titleを渡す
	 * @return list画面
	 */

	@PostMapping(URL.LIST)
	public String taskUpdate(@Validated taskForm taskForm, Model model) {

		//updateするため
		//DBへ渡すEntitiy
		taskEntity updateEntity = new taskEntity();

		//idを入れる
		updateEntity.setTodo_id(taskForm.getTodo_id());

		//タイトルを更新＝入れる
		updateEntity.setTodo_title(taskForm.getTodo_title());

		//場所を更新＝入れる
		updateEntity.setTodo_place(taskForm.getTodo_place());

		/**
		 * パラメーター　：yyyy/MM/dd
		 * 戻り値　：yyyy/MM/dd 
		 * 文字列の日付をjava.sql.Dateに変換する
		 * java.util.Dateではない。
		 * sqlパッケージのDateはデータベースのデータ型のdateと互換性があります
		 *  データベースの日付をJavaで処理したい場合に使用します。
		 */
		//日付を入れる
		updateEntity.setTodo_date(Date.valueOf(taskForm.getTodo_date()));


		//updateEntity.setTodo_time(Time.valueOf((taskForm.getTodo_time())));が解決できなくて以下で一旦

		/**
		 * valueOf public static Time valueOf(String s) JDBC時間エスケープ形式中の文字列をTime値に変換します。
		 * パラメータ: s - hh:mm:ss形式の時間 戻り値: 対応するTimeオブジェクト
		 */

		//未入力だったら
		if(taskForm.getTodo_time().length() < 6 ) {

			StringBuilder buf = new StringBuilder();
			//hh:mm
			buf.append(taskForm.getTodo_time());	
			//:ss
			buf.append(":00");
			//00を語尾につけて、hh:mm:ssのストリングをタイム型変換
			updateEntity.setTodo_time(Time.valueOf((buf.toString())));

			//既に入力されていたら
		}else{
			//hh:mm:ssのストリングをタイム型変換
			updateEntity.setTodo_time(Time.valueOf(taskForm.getTodo_time()));	

		}

		//データを更新する
		taskAcsServ.taskUpdatek(updateEntity); 


		//全データを取得してくる
		//DBからデータ探してくる
		List<taskEntity> todolists =taskAcsServ.searchAll();

		model.addAttribute("todolists",todolists);

		//タイトル追加
		model.addAttribute("title", "toDoList");

		//http://localhost:8080/todo/list
		//return URL.TODO + URL.LIST;
		return "redirect:/todo/list";
	}






}
