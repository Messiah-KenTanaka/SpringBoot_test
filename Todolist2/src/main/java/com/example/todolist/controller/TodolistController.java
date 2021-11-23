package com.example.todolist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.todolist.entity.Todo;
import com.example.todolist.form.TodoData;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class TodolistController {

	private final TodoRepository todoRepository;
	private final TodoService todoService;

	@GetMapping({ "/", "/todo" })
	public ModelAndView showTodoList(ModelAndView mv) {
		// 一覧を検索して表示する
		mv.setViewName("todoList");
		List<Todo> todoList = todoRepository.findAll();
		mv.addObject("todoList", todoList);
		return mv;
	}

	// Todo一覧画面(todoList.html)で新規追加がクリックされたとき
	@GetMapping("/todo/create")
	public ModelAndView createTodo(ModelAndView mv) {
		mv.setViewName("todoForm");
		mv.addObject("todoData", new TodoData());
		return mv;
	}

	// Todo入力画面(todoForm.html)で登録ボタンがクリックされたとき
	@PostMapping("/todo/create")
	public ModelAndView createTodo(@ModelAttribute @Validated TodoData todoData, BindingResult result,
			ModelAndView mv) {
		// エラーチェック
		boolean isValid = todoService.isValid(todoData, result);

		if (!result.hasErrors() && isValid) {
			// エラーなし
			Todo todo = todoData.toEntity();
			todoRepository.saveAndFlush(todo);
			return showTodoList(mv);
		} else {
			// エラーあり
			mv.setViewName("todoForm");
			return mv;
		}
	}

	// Todo入力画面でキャンセル登録がクリックされたとき
	@PostMapping("/todo/cancel")
	public String cancel() {
		return "redirect:/todo";
	}
}
