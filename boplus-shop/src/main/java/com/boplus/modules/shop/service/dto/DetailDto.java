/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.modules.shop.service.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DetailDTO
 * @author
 * @Date 2019/10/12
 **/
@Data
public class DetailDto {
    private List<String> data;

    //private List<Map<String,List<Map<String,String>>>> res;

    private List<Map<String,Map<String,String>>> res;
}
