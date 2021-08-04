package com.ssafy.api.controller;

import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Api(value="채팅 API", tags = {"Chat."})
@Controller
public class ChatController {

    @GetMapping("/chat")
    public void getChat(){
        log.info("@ChatController, chat GET()");
    }
}
