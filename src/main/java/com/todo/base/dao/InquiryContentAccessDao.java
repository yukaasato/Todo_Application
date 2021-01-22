package com.todo.base.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.todo.base.entity.InquiryEntity;

@Repository
public class InquiryContentAccessDao {

	//変数em　の定義
	//エンティティマネージャーの変数名を付ける
	@Autowired
	EntityManager EntityCtl;//Entity Control
	
	//SQLでいうINSERT 文
	public void inquiryCntInsert(InquiryEntity inquiryEntity) {
		//引き数にユーザが入力した情報が入っている
		EntityCtl.persist(inquiryEntity);
	}
}