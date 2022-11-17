package edu.kh.project.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.kh.project.board.model.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService serivce;
}
