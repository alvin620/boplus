/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.modules.shop.service.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName FromatDetailDTO
 * @author
 * @Date 2019/10/12
 **/

@Data
public class FromatDetailDto {
    private  boolean attrHidden;

    private  String detailValue;

    private List<String> detail;

    private String value;

}
