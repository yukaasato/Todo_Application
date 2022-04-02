package com.todo.base.dao;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.todo.base.entity.taskEntity;

/**
 * クラスの説明
 * Daoクラス
 * Entitiyにセットされている値をもとに
 * SQLを実行しデータの
 * 取得、更新、削除を行う
 * 
 * */

@Repository
public class taskContentAccessDao {

	
	@Autowired
	EntityManager em;
	
	/**
	 * メソッド：SQLへ列を追加する 
	 * データ：todo_titielを登録する
	 * 引数：taskEntitiy(ユーザが入力したタスクのタイトル)
	 */
	
	public void addTask(taskEntity title) {
		
		//DBへエンティティのデータを登録する
		em.persist(title);
		
	}
	
	/*
	 * Native Query SQLを文字列で記述し、そのままDBに渡す方法
	 * 長所：新しいことを覚える必要がないこと、Oracle関数のようなDB依存のクエリも投げられること。
	 * 短所：JPAの意義であるデータ永続化装置が何かを気にしなくて良い、という精神をガン無視してること。DBをOracleからMySQLに変えよう（
	 * 予算的な理由で）、とかになった時に、死にます（工数的な理由で）。
	 */
	public  List<taskEntity>  searchAll() {
		
		List<taskEntity> taskEntity = (List<taskEntity>)em.createNativeQuery("select * from todo").getResultList();
		
		return  taskEntity;
	
	}
	
	
	//一覧表示
	public List<taskEntity> getAllTask(){
		
		//文字列の追加を行うインスタンスを生成、SQLが入る
		StringBuilder selectAll = new StringBuilder();
		
		//全データ取得　ユーザビリティを考えてに日時の並べ替えも同時に行う
		selectAll.append("SELECT TODO_ID,"
				+ "TODO_TITLE,"
				+ "CAST(TODO_DATE AS DATE) as TODO_DATE," //datetime型からDate型へ変換
				+ "TODO_PLACE,"
				+ "COMPLETE_FLAG,"
				+ "DELETE_FLAG,"
				+ "TODO_TIME"
				+ " FROM TODO"
				+ " ORDER BY CASE WHEN TODO_DATE IS NULL THEN '9999-99-99 00:00:00' END, TODO_DATE ASC,TODO_TIME ASC"); //日付>時間　NULLは後ろに来るように
		
		//SQL　文
		//SELECT * FROM todo Order by case when Todo_date is NULL then '9999-99-99 00:00:00' end, TODO_DATE ASC,TODO_TIME ASC;
		
		//EntityにgetResultList()で返ってきたデータをいれて、リスト型へ変換してresultListへ格納
		//emのメソッドgetResultList();型をなくして返す　名前のない箱
		
		List<taskEntity> resultList =
				(List<taskEntity>)em.createNativeQuery(selectAll.toString(), taskEntity.class).getResultList();
		
		//全データをサービスクラスへ返す<ホーム画面のデータ>
		return resultList;
	}
	
	//一件取得
	public taskEntity selectOneTask(int id) {
		
		//文字列の追加を行うインスタンスを生成、SQLが入る
		StringBuilder selectOne = new StringBuilder();
		
		//1件のデータ取得　?1部分はjavaの変数が文字列化しないためにセットパラメーターを使う
		selectOne.append("SELECT * FROM TODO WHERE TODO_ID =?1");
		
		//EntityにgetSingleResult()で返ってきたデータをいれて、エンティティへキャストして、oneTaskへ格納
		taskEntity oneTask =(taskEntity)em.createNativeQuery(selectOne.toString(),taskEntity.class).setParameter(1,id).getSingleResult();
		
		//1件のデータをサービスクラスへ返す<詳細画面のデータ>
		return oneTask;
	}
	
	//一件詳細更新
	public void updateTask(taskEntity taskEntity) {

		//文字列の追加を行うインスタンスを生成、SQLが入る
		StringBuilder updateTask = new StringBuilder();
		
		//1件のデータ更新　?1~5の部分はjavaの変数が文字列化しないためにセットパラメーターを使う
		updateTask.append("UPDATE TODO SET "
				+ "todo_title = ?1,"
				+ "todo_date  = ?2,"
				+ "todo_place = ?3,"
				+ "todo_time = ?4 "
				+ "WHERE "
				+ "todo_id = ?5");
		
		//SQLの?1~5の入る値をtaskEntitiyから取得し、ＳＱＬ文を更新する
		em.createNativeQuery(updateTask.toString(),taskEntity.class) 
		.setParameter(1, taskEntity.getTodo_title())
		.setParameter(2, taskEntity.getTodo_date())
		.setParameter(3, taskEntity.getTodo_place())
		.setParameter(4, taskEntity.getTodo_time())
		.setParameter(5, taskEntity.getTodo_id())
		
		//updateを終了させる
		.executeUpdate();
	}
	
	public void deleteTask(int id) {
		
		//文字列の追加を行うインスタンスを生成、SQLが入る
		StringBuilder changeDltFlag = new StringBuilder();
				
		//1件のデータ取得　?1部分はjavaの変数が文字列化しないためにセットパラメーターを使う
		changeDltFlag.append("UPDATE TODO SET DELETE_FLAG = 1  WHERE TODO_ID =?1");

		//EntityにgetSingleResult()で返ってきたデータをいれて、エンティティへキャストして、oneTaskへ格納
		em.createNativeQuery(changeDltFlag.toString(),taskEntity.class).setParameter(1,id)
		
		.executeUpdate();
				
	}
	
	public void completeTask(int id) {
		
		//文字列の追加を行うインスタンスを生成、SQLが入る
		StringBuilder changeCmpFlag = new StringBuilder();
				
		//1件のデータ取得　?1部分はjavaの変数が文字列化しないためにセットパラメーターを使う
		changeCmpFlag.append("UPDATE TODO SET COMPLETE_FLAG = 1  WHERE TODO_ID =?1");

		//EntityにgetSingleResult()で返ってきたデータをいれて、エンティティへキャストして、oneTaskへ格納
		em.createNativeQuery(changeCmpFlag.toString(),taskEntity.class).setParameter(1,id)
		
		.executeUpdate();
				
	}
	public void clearTask(int id) {
		
		//文字列の追加を行うインスタンスを生成、SQLが入る
		StringBuilder changeCmpFlag = new StringBuilder();
				
		//1件のデータ取得　?1部分はjavaの変数が文字列化しないためにセットパラメーターを使う
		changeCmpFlag.append("UPDATE TODO SET COMPLETE_FLAG = 0  WHERE TODO_ID =?1");

		//EntityにgetSingleResult()で返ってきたデータをいれて、エンティティへキャストして、oneTaskへ格納
		em.createNativeQuery(changeCmpFlag.toString(),taskEntity.class).setParameter(1,id)
		
		.executeUpdate();
				
	}
	

	public void taskAllDelete() {
	
		//文字列の追加を行うインスタンスを生成、SQLが入る
		StringBuilder changeCmpFlag = new StringBuilder();
				
		//1件のデータ取得　?1部分はjavaの変数が文字列化しないためにセットパラメーターを使う
		changeCmpFlag.append("UPDATE TODO SET COMPLETE_FLAG = 1 WHERE DELETE_FLAG = =0");

		//EntityにgetSingleResult()で返ってきたデータをいれて、エンティティへキャストして、oneTaskへ格納
		em.createNativeQuery(changeCmpFlag.toString(),taskEntity.class)
			
		.executeUpdate();
		
				
	}
	

}