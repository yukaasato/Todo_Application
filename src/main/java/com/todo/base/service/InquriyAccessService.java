package com.todo.base.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.base.dao.InquiryContentAccessDao;
import com.todo.base.entity.InquiryEntity;

//ビジネスロジック　をかく（Appが何をするか
@Service
//DBへの処理を制御するもの、一連のながれ　登録に必要なので、サービスクラスにはつけておいてね
@Transactional
public class InquriyAccessService {

	//オートワイヤードをつけてあげると、1つのインスタンスを作成してくれる
	//どこで書くかっていうと、最初
	@Autowired
	InquiryContentAccessDao inquiryAcsDao;

	
	//メソッドの中でDaoを呼び出してあげる、呼び出し型がめんどくさい
	//引き数はエンティティ
	public void inquiryInsert(InquiryEntity inquiryEntity) {
		
		inquiryAcsDao.inquiryCntInsert(inquiryEntity);
	}
}

/*
 * Daoつくる
 * Insertのメソッドつくる、引数はEntitiy
 * 中の処理はDaoの中のinsertを呼び出す
 *
 * 
 * 
 * */
