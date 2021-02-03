package com.todo.base.controller;

import java.sql.Date;
import java.sql.Time;
//import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo.base.URL;
import com.todo.base.entity.taskEntity;
import com.todo.base.form.InquiryForm;
import com.todo.base.form.taskForm;
import com.todo.base.service.taskAccessService;

@Controller
//変数使う、汎用性高い
//GetPostに関わらずこれを経由する、URLが長い時に便利
@RequestMapping(URL.TODO)
public class taskController {

	@Autowired
	taskAccessService taskAcsServ;

/*******************************************************************************/
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

	/*******************************************************************************/
	//作成押下時
	@PostMapping(URL.CREATE)							//Formの後に必ず置く、でないとエラーでる
	public String complete( @Validated taskForm taskForm,BindingResult result, 
			Model model) {	
		
		if(result.hasErrors()) {
			model.addAttribute("title", "toDoList");
			
			List<taskEntity> todolists =taskAcsServ.searchAll();
			
			model.addAttribute("todolists",todolists);
			
			return URL.TODO + URL.LIST;
			}
		
		
		//エンティティのインスタンス作成
		taskEntity taskEntity = new taskEntity();

		//エンティティにタイトルを渡す
		taskEntity.setTodo_title(taskForm.getTodo_title());
		
		//フラグに0を入れる　※デフォルトでNullになってしまうため
		taskEntity.setComplete_flag(false);
		taskEntity.setDelete_flag(false);
	
		//日付を入れる（今の）
		//taskEntity.setTodo_date(taskEntity.getTodo_date());

		//DBへインサートする
		taskAcsServ.addTask(taskEntity);


		model.addAttribute("title", "toDoList");


		//http://localhost:8080/todo/list
		//return URL.TODO + URL.LIST;
		return "redirect:/todo/list";

	}

	/*******************************************************************************/
	/**
	 * 詳細画面へ遷移
	 * データを一件取得する処理
	 * データベースにはtodo_idをformから受け取り条件式Whereで絞り込む
	 * */
	@GetMapping(URL.DETAILS)
	public String displayDtl(taskForm taskForm, Model model) {
		
		//データを作る
		taskEntity detailEntity =new taskEntity();
		
		//idフォームからエンティティへセットする
		detailEntity.setTodo_id(taskForm.getTodo_id());
		
		//決まったデータ表示
		taskEntity oneTask = taskAcsServ.findTask(detailEntity.getTodo_id());

		//タイトル
		model.addAttribute("title","My Task");

		//Entityを渡す
		model.addAttribute("oneTask", oneTask);

		//詳細画面へ
		return URL.TODO + URL.DETAILS;


	}

	
	/*******************************************************************************/
	/**
	 * アップデート処理
	 * @param Form に入っているものを保持
	 * @param model　titleを渡す
	 * @return list画面
	 */

	@PostMapping(URL.LIST)
	public String taskUpdate(@Validated taskForm taskForm,BindingResult result, Model model) {

		if(result.hasErrors()) {
			
			taskEntity detailEntity =new taskEntity();
			
			//idフォームからエンティティへセットする
			detailEntity.setTodo_id(taskForm.getTodo_id());
			
			//決まったデータ表示
			taskEntity oneTask = taskAcsServ.findTask(detailEntity.getTodo_id());

			//タイトル
			model.addAttribute("title","My Task");

			//Entityを渡す
			model.addAttribute("oneTask", oneTask);

			//詳細画面へ
			return URL.TODO + URL.DETAILS;

		}

		//updateするため
		//エンティティのインスタンス作成
		taskEntity updateEntity = new taskEntity();

		//idをFormからエンティティへ渡す
		updateEntity.setTodo_id(taskForm.getTodo_id());

		//タイトルをFormからエンティティへ渡す
		updateEntity.setTodo_title(taskForm.getTodo_title());

		//場所をFormからエンティティへ渡す
		updateEntity.setTodo_place(taskForm.getTodo_place());

		
		//日付をFormからエンティティへ渡す
		//入力されていたら
		if(taskForm.getTodo_date().length() == 10 ) {
			
			//FormのString型からDate型に変換し、エンティティへ渡す
			updateEntity.setTodo_date(Date.valueOf(taskForm.getTodo_date()));
			
		}


		//時間をFormからエンティティへ渡す
		//入力されていたら（初回）
		if(taskForm.getTodo_time().length() == 5 ) {
			
			//手順1.　書式をjavaとSQLで一致させる
			//文字列の連結の為にインスタンス生成をする
			StringBuilder buf = new StringBuilder();
			
			//hh:mm　に当たる時間を buf に入れる
			buf.append(taskForm.getTodo_time());	
			
			//:ss　にあたる文字列　00を buf に入れる
			buf.append(":00");
			
			
			//手順2.　型をjavaとSQLで一致させる
			//hh:mm:ssのストリングをタイム型変換
			updateEntity.setTodo_time(Time.valueOf((buf.toString())));
		}
		
		//既に入力されていたら
		if(taskForm.getTodo_time().length() == 9 ) {
			
			//hh:mm:ssのストリングをタイム型変換
			updateEntity.setTodo_time(Time.valueOf(taskForm.getTodo_time()));	
		}
		
		//空だったら""でとれている
		//一旦何もしないでおく、ＤＢにはNULLで登録される
		
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
	
	/*******************************************************************************/
	@PostMapping(URL.DELETE)
	public String taskDelete (taskForm taskForm, Model model) {

		//削除用のデータを作る
		taskEntity deleteEntity =new taskEntity();
		
		//Formからidをエンティティへセットする
		deleteEntity.setTodo_id(taskForm.getTodo_id());
		
		//アクセスクラスを使って削除する=フラグを切り替える
		taskAcsServ.taskDelete(deleteEntity.getTodo_id()); 
		
		//データを読み込む
		
		//全データを取得してくる
		//DBからデータ探してくる
		List<taskEntity> todolists =taskAcsServ.searchAll();

		//全データのリストをHTML側へ引き渡す
		model.addAttribute("todolists",todolists);

		//タイトル追加
		model.addAttribute("title", "toDoList");
		
		
		return URL.TODO + URL.LIST;
	}
	
	/*******************************************************************************/
	@PostMapping(URL.COMPLETE)
	public String taskComplete(taskForm taskForm, Model model) {

		//完了用のデータを作る
		taskEntity completeEntity =new taskEntity();
		
		//Formからidをエンティティへセットする
		completeEntity.setTodo_id(taskForm.getTodo_id());
		if(taskForm.getComplete_flag() ==1){
			completeEntity.setComplete_flag(true);
		}else if(taskForm.getComplete_flag() ==0){
			completeEntity.setComplete_flag(false);
		}
		
		System.out.println(completeEntity.getComplete_flag());
		if(completeEntity.getComplete_flag() == true) {
			taskAcsServ.taskClear(completeEntity.getTodo_id()); 
		}
		
		if(completeEntity.getComplete_flag() == false) {
			//アクセスクラスを使って削除する=フラグを切り替える
			taskAcsServ.taskComplete(completeEntity.getTodo_id()); 	}
	
		
		//データを読み込む
		
		//全データを取得してくる
		//DBからデータ探してくる
		List<taskEntity> todolists =taskAcsServ.searchAll();

		//全データのリストをHTML側へ引き渡す
		model.addAttribute("todolists",todolists);

		//タイトル追加
		model.addAttribute("title", "toDoList");
		
		
		return URL.TODO + URL.LIST;
	}
	
	
	
	
	/*
	 * @PostMapping(URL.ALLDELETE) public String taskAllDelete (taskForm taskForm,
	 * Model model) {
	 * 
	 * 
	 * //アクセスクラスを使ってて削除する=フラグを切り替える taskAcsServ.taskAllDelete();
	 * 
	 * //データを読み込む
	 * 
	 * //全データを取得してくる //DBからデータ探してくる List<taskEntity> todolists
	 * =taskAcsServ.searchAll();
	 * 
	 * //全データのリストをHTML側へ引き渡す model.addAttribute("todolists",todolists);
	 * 
	 * //タイトル追加 model.addAttribute("title", "toDoList");
	 * 
	 * 
	 * return "redirect:/todo/list"; }
	 */
		//時間をいれる
		/*解説*/
		/*
		 * updateEntity.setTodo_time(Time.valueOf((taskForm.getTodo_time())));
		 * にすると桁数が違うためエラーになる（ HTML(type="time(11:22)"→ Java hh:mm → SQL: hh:mm:ss hh:mm の文字列）
		 * 
		 * で、:00をつける →SQL に入れられるように加工 。
		 * そして、型を変換（型が違うとSQLでエラーが起こるので）
		 * するのにTime.valeOf(String s)を使う ※import java.sql.Time;
		 */
		
		/*
		 * 私的にややこしかった理由は
		 *  文字列の書式と型 2つを
		 *  一致させないといけないから。
		 *   書式：今回はStringBuilder 
		 *   型 ：Time.valeOf(Strings)
		 *   
		 *   
		 *   あとHTMILのinput type="time"　も知らなかったから(hh:mm)
		 *   SQL側のＴＩＭＥ型のhh:mm:ssの書式も知らないから
		 *   
		 *   次詰まったら
		 *   HTMI SQLの書式　をまず確認
		 *   文字列を一致させる
		 *   valueOfメソッド使う
		 *   でok
		 */
		
		
		/*参考サイト*/
		/*https://dodododo.jp/java/javase_6_docs_ja/api/java/sql/Time.html#valueOf(java.lang.String)*/
		/**
		 * 引数：yyyy/MM/dd
		 * 戻り値　：yyyy/MM/dd 
		 * 文字列の日付をjava.sql.Dateに変換する
		 * java.util.Dateではない。
		 * sqlパッケージのDateはデータベースのデータ型のdateと互換性があります
		 *  データベースの日付をJavaで処理したい場合に使用します。
		 */
		/**
		 * 引数: s - hh:mm:ss形式の時間
		 * 戻り値: 対応するTimeオブジェクト
		 * valueOf public static Time valueOf(String s) 
		 * JDBC時間エスケープ形式中の文字列をTime値に変換します。
		 *  
		 */

	

	





}
