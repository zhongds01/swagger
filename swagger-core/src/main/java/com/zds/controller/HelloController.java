package com.zds.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * HelloController
 *
 * @author zhongdongsheng
 * @datetime 2021/9/7 22:47
 */
@Api(tags = "Hello控制层")
@RestController
public class HelloController {
    @ApiOperation(value = "处理hello请求")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
