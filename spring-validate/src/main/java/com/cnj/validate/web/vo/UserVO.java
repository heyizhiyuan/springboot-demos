package com.cnj.validate.web.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Create by cnj on 2019-5-14
 */
@Data
@ToString
public class UserVO {

    /**
     * 校验消息国际化，需要添加配置。即：I8nValidatorConfiguration
     */
    @NotBlank(message="{account.password.empty}")
    private String username;

    @NotBlank(message = "{account.username.empty}")
    private String password;

}
