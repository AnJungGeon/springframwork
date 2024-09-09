package com.mycompany.springframework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.springframework.dao.mybatis.Ch13BoardDao;
import com.mycompany.springframework.dto.Ch13Board;
import com.mycompany.springframework.dto.Ch13Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Ch13BoardService {
	@Autowired
	private Ch13BoardDao boardDao;

	
	public List<Ch13Board> getBoardList(Ch13Pager pager){
		log.info("실행");
		List<Ch13Board> list = boardDao.selectList(pager);
		return list;
	}
	
	public Ch13Board getBoard(int bno) {
		log.info("실행");
		Ch13Board board = boardDao.selectByBno(bno);
		return board;
	}
	
	public void writeBoard(Ch13Board board) {
		log.info("실행");
		log.info("bno: " + board.getBno());
		boardDao.insert(board);
		//bno를 이용해서 추가적인 비즈니스 로직 작성해야할 경우
		log.info("insert 후 bno: " + board.getBno());
		
		int bno = board.getBno(); 
		
	}
	
	public void updateBoard(Ch13Board board) {
		boardDao.update(board);
	}
	
	public void deleteBoard(int bno) {
		boardDao.delete(bno);
	}

	public int getTotalRows() {
		log.info("실행");
		int totalRows = boardDao.countRows();
		return totalRows;
	}

	public Ch13Board getBoardAttach(int bno) {
		log.info("실행");
		Ch13Board board = boardDao.selectAttachByBno(bno);
		return board;
	}

	public void addHitcount(int bno) {
		boardDao.updateHitcount(bno);
	}
	
	
}
