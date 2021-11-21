package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// テキスト用のアノテーション
@RestController
public class HelloController {

	@GetMapping("/hello")
	public String sayHello() {
		return "みなさん、こんにちは";
	}
}
