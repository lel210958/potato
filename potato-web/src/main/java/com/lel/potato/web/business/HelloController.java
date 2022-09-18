package com.lel.potato.web.business;

import com.lel.potato.common.utils.ApplicationContextUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("toMain")
    public String toMain(){

        return ApplicationContextUtils.getBean(Environment.class).toString();
    }
}
