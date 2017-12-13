package com.huigege.pig.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("test")
@RestController
public class TestController {
    protected static Logger logger= LoggerFactory.getLogger(TestController.class);
    @PostMapping("test1")
    public void  TesTTesTTesTTesT(){
        int a =0;
        logger.info("你猜我进来了吗？？？？？");
    }
}
