package com.todo.base.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		
		
		
		
	    
	    try {
		String stDate ="2020/10/14 01:23:34"; 
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss");
		System.out.println("koko");
		System.out.println(taskForm.getTodo_date());
		Date dates = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(taskForm.getTodo_date()); //StringからDateへ
		
		//Date dates = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(stDate); //StringからDateへ
		updateEntity.setTodo_date(dates);
		System.out.println(dates);
	    }catch(ParseException ex){
	    	ex.printStackTrace();
	    }
		
		
		//データを更新する
		taskAcsServ.taskUpdatek(updateEntity); //落ちる。。。。。。。。。。。。。。。
		

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
