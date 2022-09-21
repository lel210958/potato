package com.lel.potato.admin.system;

import com.lel.potato.admin.intf.system.ITestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>Description: </p>
 *
 * @author liuenlu
 * @version v1.0.0
 * @since 2022/9/21 17:09
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ITestService testService;

    @GetMapping("/toMain")
    public String toMain(){
        return testService.toMain();
    }
}
