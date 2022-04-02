package com.todo.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.todo.base.form.reviewForm;

//1.コントローラーの目印をつける
@Controller
public class dataInsert {

	
	//2.ホーム画面を返すメソッドを作る
	
	//URL
	@GetMapping("review/insert")
	//FormクラスとModelのインスタンスを生成して、空のままHTMLに渡す
	public String form(reviewForm insertForm, Model model) {
		
		System.out.println("aaa");
		//HTMLのタイトル（変数）にデータ登録の文字を入れる
		model.addAttribute("title", "データ登録");
		
		//HTMLを返す
		return "/review/insert";
	}
	
}
//3.FORMを作る
