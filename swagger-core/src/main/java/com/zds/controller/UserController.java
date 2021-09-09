package com.zds.controller;

import com.zds.entity.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author zhongdongsheng
 * @datetime 2021/9/8 22:37
 */
@Api(tags = "用户请求处理控制层")
@RestController
public class UserController {

    @ApiOperation(value = "根据id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "Long", paramType = "query", example = "1")
    @GetMapping(value = "/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return new User().setId(id).setUserName("zhongdongsheng").setPassword("******");
    }

    @ApiOperation(value = "查询所有的用户信息")
    @GetMapping(value = "/user/all")
    public List<User> getAllUser() {
        User user1 = new User().setId(1000L).setUserName("zhongdongsheng").setPassword("******");
        User user2 = new User().setId(1001L).setUserName("zhouliang").setPassword("******");
        User user3 = new User().setId(1002L).setUserName("yongjian").setPassword("******");
        return Arrays.asList(user1, user2, user3);
    }

    @ApiOperation(value = "添加用户", notes = "返回：新增用户的id")
    @PostMapping(value = "/user/save")
    public Long saveUser(@RequestBody User user) {
        System.out.println(user);
        return user.getId();
    }

    @ApiOperation(value = "根据用户id删除用户")
    @ApiResponse(code = 200, message = "响应成功")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名称", dataType = "String", paramType = "query")
    })
    @DeleteMapping("/user/delete")
    public void deleteUser(
            @ApiParam(value = "用户id", required = true, example = "1")
            @RequestParam("id") Long id) {
        System.out.println(id);
    }

    @ApiIgnore(value = "忽略该请求")
    @GetMapping("/ignore")
    public void ignore() {
    }
}
