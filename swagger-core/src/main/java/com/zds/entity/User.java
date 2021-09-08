package com.zds.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * description
 *
 * @author zhongdongsheng
 * @datetime 2021/9/8 22:33
 */
@ApiModel(value = "用户实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户名称",required = true,example = "zhongdongsheng")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String password;
}
