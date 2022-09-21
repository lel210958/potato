package com.lel.potato.admin.service.system;

import com.lel.potato.admin.intf.system.ITestService;
import org.springframework.stereotype.Service;

/**
 * <p>Description: </p>
 *
 * @author liuenlu
 * @version v1.0.0
 * @since 2022/9/21 17:08
 **/
@Service
public class TestServiceImpl implements ITestService {

    @Override
    public String toMain() {
        return "hello admin";
    }
}
