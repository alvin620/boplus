/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.modules.shop.service.dto;

import com.boplus.annotation.Query;
import lombok.Data;

/**
* @author
* @date 2020-05-12
*/
@Data
public class YxStoreProductQueryCriteria{

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String storeName;

    // 精确
    @Query
    private Integer isDel;

    @Query
    private Integer isShow;
}
