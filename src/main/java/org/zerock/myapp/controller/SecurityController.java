package org.zerock.myapp.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.UserDTO;
import org.zerock.myapp.entity.User;
import org.zerock.myapp.persistence.UserRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@RequestMapping("/security/*") // Base URI
@RestController
public class SecurityController {  // 보안 관리 ( 로그인 & 로그아웃 )

   @Autowired UserRepository userRepo;
   
   @PostMapping("/login")
   public ResponseEntity<String> login(UserDTO dto) {
      
      String userId = dto.getUserId();
      String passwd = dto.getPasswd();
      
      if(userId == null || passwd == null || userId.isEmpty() || passwd.isEmpty()) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body("아이디 또는 비밀번호를 입력해주세요.");
      }
      
      Optional<User> user = this.userRepo.findByUserIdAndPasswd(userId, passwd);
      // 사용자의 id 가 db 에 저장이 되어 있는지 검색. 
      
      if(user.isEmpty()) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                         .body("아이디 또는 패스워드가 일치하지 않습니다.");
      } // db 에 사용자가 없을경우.
      
      log.info("login success: {}" , user);
      return ResponseEntity.status(HttpStatus.OK)
                      .body("로그인 성공");
   } // User login end
   
   @PostMapping("/logout")
   public ResponseEntity<String> logout() {
      log.info("로그아웃 성공");
       return ResponseEntity.status(HttpStatus.OK)
                         .body("{\"message\":\"로그아웃 성공\"}");
   } // 로그아웃.
   
} // end class
