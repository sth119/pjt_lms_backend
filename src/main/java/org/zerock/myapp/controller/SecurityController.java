package org.zerock.myapp.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/security/*") // Base URI

@RestController
public class SecurityController {  // 보안 관리 ( 로그인 & 로그아웃 )

	
} // end class
