/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.modules.activity.service.impl;

import com.boplus.common.service.impl.BaseServiceImpl;
import com.boplus.common.utils.QueryHelpPlus;
import com.boplus.dozer.service.IGenerator;
import com.boplus.modules.activity.domain.YxStoreCoupon;
import com.boplus.modules.activity.service.YxStoreCouponService;
import com.boplus.modules.activity.service.dto.YxStoreCouponDto;
import com.boplus.modules.activity.service.dto.YxStoreCouponQueryCriteria;
import com.boplus.modules.activity.service.mapper.YxStoreCouponMapper;
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
* @date 2020-05-13
*/
@Service
@AllArgsConstructor
//@CacheConfig(cacheNames = "yxStoreCoupon")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YxStoreCouponServiceImpl extends BaseServiceImpl<YxStoreCouponMapper, YxStoreCoupon> implements YxStoreCouponService {

    private final IGenerator generator;

    @Override
    //@Cacheable
    public Map<String, Object> queryAll(YxStoreCouponQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<YxStoreCoupon> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), YxStoreCouponDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    //@Cacheable
    public List<YxStoreCoupon> queryAll(YxStoreCouponQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(YxStoreCoupon.class, criteria));
    }


    @Override
    public void download(List<YxStoreCouponDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YxStoreCouponDto yxStoreCoupon : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("优惠券名称", yxStoreCoupon.getTitle());
            map.put("兑换消耗积分值", yxStoreCoupon.getIntegral());
            map.put("兑换的优惠券面值", yxStoreCoupon.getCouponPrice());
            map.put("最低消费多少金额可用优惠券", yxStoreCoupon.getUseMinPrice());
            map.put("优惠券有效期限（单位：天）", yxStoreCoupon.getCouponTime());
            map.put("排序", yxStoreCoupon.getSort());
            map.put("状态（0：关闭，1：开启）", yxStoreCoupon.getStatus());
            map.put("兑换项目添加时间", yxStoreCoupon.getAddTime());
            map.put("是否删除", yxStoreCoupon.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}