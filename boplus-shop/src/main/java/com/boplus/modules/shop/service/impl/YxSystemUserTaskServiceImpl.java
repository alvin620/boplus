/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.modules.shop.service.impl;

import com.boplus.common.service.impl.BaseServiceImpl;
import com.boplus.common.utils.QueryHelpPlus;
import com.boplus.dozer.service.IGenerator;
import com.boplus.modules.shop.domain.YxSystemUserLevel;
import com.boplus.modules.shop.domain.YxSystemUserTask;
import com.boplus.modules.shop.service.YxSystemUserLevelService;
import com.boplus.modules.shop.service.YxSystemUserTaskService;
import com.boplus.modules.shop.service.dto.YxSystemUserTaskDto;
import com.boplus.modules.shop.service.dto.YxSystemUserTaskQueryCriteria;
import com.boplus.modules.shop.service.mapper.SystemUserTaskMapper;
import com.boplus.utils.FileUtil;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author
* @date 2020-05-12
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxSystemUserTask")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxSystemUserTaskServiceImpl extends BaseServiceImpl<SystemUserTaskMapper, YxSystemUserTask> implements YxSystemUserTaskService {

    private final IGenerator generator;
    private final YxSystemUserLevelService systemUserLevelService;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxSystemUserTaskQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxSystemUserTask> page = new PageInfo<>(queryAll(criteria));
        List<YxSystemUserTaskDto> systemUserTaskDTOS = generator.convert(page.getList(),YxSystemUserTaskDto.class);
        for (YxSystemUserTaskDto systemUserTaskDTO : systemUserTaskDTOS) {
            YxSystemUserLevel userLevel=systemUserLevelService.getById(systemUserTaskDTO.getLevelId());
            systemUserTaskDTO.setLevalName(userLevel.getName());
        }
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", systemUserTaskDTOS);
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxSystemUserTask> queryAll(YxSystemUserTaskQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxSystemUserTask.class, criteria));
    }


    @Override
    public void download(List<YxSystemUserTaskDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxSystemUserTaskDto yxSystemUserTask : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务名称", yxSystemUserTask.getName());
            map.put("配置原名", yxSystemUserTask.getRealName());
            map.put("任务类型", yxSystemUserTask.getTaskType());
            map.put("限定数", yxSystemUserTask.getNumber());
            map.put("等级id", yxSystemUserTask.getLevelId());
            map.put("排序", yxSystemUserTask.getSort());
            map.put("是否显示", yxSystemUserTask.getIsShow());
            map.put("是否务必达成任务,1务必达成,0=满足其一", yxSystemUserTask.getIsMust());
            map.put("任务说明", yxSystemUserTask.getIllustrate());
            map.put("新增时间", yxSystemUserTask.getAddTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
