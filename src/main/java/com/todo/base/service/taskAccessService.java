package com.todo.base.service;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.base.dao.taskContentAccessDao;
import com.todo.base.entity.taskEntity;

@Service
@Transactional

public class taskAccessService {

	@Autowired
	taskContentAccessDao taskAcsDao;

	//Task作成
	public void taskInsert(taskEntity taskEntity) {

		taskAcsDao.taskCntInsert(taskEntity);
	}


	//Task一覧表示
	public List<taskEntity>  searchAll() {

		//List<taskEntity> taskEntity=taskAcsDao.searchAll();
		return taskAcsDao.getAllTask();
		

	}		

	//Task一件表示
	public taskEntity findTask(int id) {
		taskEntity task = taskAcsDao.selectOneTask(id);
		return task;
	}
	
	//Task更新
	public void taskUpdatek(taskEntity updateEntity) {
		taskAcsDao.updateTask(updateEntity);
		
	}

}

