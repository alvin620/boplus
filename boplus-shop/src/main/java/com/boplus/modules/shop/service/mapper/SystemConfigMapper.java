/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.modules.shop.service.mapper;

import com.boplus.common.mapper.CoreMapper;
import com.boplus.modules.shop.domain.YxSystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author
* @date 2020-05-12
*/
@Repository
@Mapper
public interface SystemConfigMapper extends CoreMapper<YxSystemConfig> {

}
