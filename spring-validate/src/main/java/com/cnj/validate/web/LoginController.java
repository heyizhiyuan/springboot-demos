package com.cnj.validate.web;

import com.cnj.protocol.i18n.MessageUtils;
import com.cnj.validate.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Create by cnj on 2019-5-14
 */
@RestController
@Slf4j
public class LoginController {

    @PostMapping("/login")
    public void login(@Valid @RequestBody UserVO userVO){
        log.info("userVO:{} {}",userVO);
    }

}
