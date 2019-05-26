package com.joker.homepage.board.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.joker.homepage.board.db.BoardDTO;
import com.joker.homepage.board.service.BoardServiceImp;

@Controller // ���� Ŭ������ ��Ʈ�ѷ� ������ ���
@RequestMapping("/board/*")
public class BoardController {
	
	// �������� ���� = BoardService����
	@Inject
	private BoardServiceImp service;
	
	// �Խ��� ��� ������ �̵�(����¡ ���� ������ ���� ����)
	@RequestMapping(value="/board_list.do")
	public String board_list() throws Exception{
		return "/board/board_list";
	}
	
	// �Խ��� �� �ۼ� �� �̵�
	@RequestMapping(value="/board_write_form.do")
	public String board_write_form() throws Exception{
		return "/board/board_write_form";
	}
	
	// �Խ��� �� �ۼ�
	@RequestMapping(value="/board_write.do", method = RequestMethod.POST)
	public String board_write(@ModelAttribute BoardDTO board) throws Exception{
		service.board_write(board);
		return "redirect:/board/board_list.do";
	}
}
