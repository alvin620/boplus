/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.modules.activity.service;

import com.boplus.common.service.BaseService;
import com.boplus.modules.activity.domain.YxStoreBargain;
import com.boplus.modules.activity.service.dto.YxStoreBargainDto;
import com.boplus.modules.activity.service.dto.YxStoreBargainQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author
* @date 2020-05-13
*/
public interface YxStoreBargainService  extends BaseService<YxStoreBargain>{

/**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YxStoreBargainQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YxStoreBargainDto>
    */
    List<YxStoreBargain> queryAll(YxStoreBargainQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YxStoreBargainDto> all, HttpServletResponse response) throws IOException;
}
