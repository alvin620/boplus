/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.tools.service.mapper;

import com.boplus.common.mapper.CoreMapper;
import com.boplus.tools.domain.LocalStorage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author
* @date 2020-05-13
*/
@Repository
@Mapper
public interface LocalStorageMapper extends CoreMapper<LocalStorage> {

}
