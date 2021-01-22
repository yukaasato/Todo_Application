package com.todo.base.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.todo.base.entity.taskEntity;


@Repository
public class taskContentAccessDao {
	//変数em　の定義
	//エンティティマネージャーの変数名を付ける
	@Autowired
	EntityManager em;//Entity Control
	
	//SQLでいうINSERT 文
	public void taskCntInsert(taskEntity taskEntity) {
		//引き数にユーザが入力した情報が入っている
		em.persist(taskEntity);
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
		
		
		StringBuilder findAll = new StringBuilder();
		
		findAll.append("SELECT TODO_ID,"
				+ "TODO_TITLE,"
				+ "CAST(TODO_DATE AS DATE) as TODO_DATE,"
				+ "TODO_PLACE,"
				+ "COMPLETE_FLAG,"
				+ "DELETE_FLAG,"
				+ "TODO_TIME"
				+ " FROM TODO");
		
		List<taskEntity> resultList = (List<taskEntity>)em.createNativeQuery(findAll.toString(), taskEntity.class).getResultList();
		return resultList;
	}
	
	//一件取得
	public taskEntity selectOneTask(int id) {
		StringBuilder findTask = new StringBuilder();
		
		findTask.append("SELECT * FROM TODO WHERE TODO_ID =?1");
		taskEntity task =(taskEntity)em.createNativeQuery(findTask.toString(),taskEntity.class).
				setParameter(1,id).getSingleResult();
		return task;
	}
	
	//一件詳細更新
	public void updateTask(taskEntity taskEntity) {

		StringBuilder updateTask = new StringBuilder();
		
		updateTask.append("UPDATE TODO SET "
				+ "todo_title = ?1,"
				+ "todo_date  = ?2,"
				+ "todo_place = ?3,"
				+ "todo_time = ?4 "
				+ "WHERE "
				+ "todo_id = ?5");
		
		em.createNativeQuery(updateTask.toString(),taskEntity.class) //ここで落ちる
		.setParameter(1, taskEntity.getTodo_title())
		.setParameter(2, taskEntity.getTodo_date())
		.setParameter(3, taskEntity.getTodo_place())
		.setParameter(4, taskEntity.getTodo_time())
		.setParameter(5, taskEntity.getTodo_id())
		.executeUpdate();

	}

}