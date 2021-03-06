/**
 * Copyright (C) 2018-2020
 * All rights reserved, Designed By www.yixiang.co

 */
package com.boplus.modules.shop.rest;

import cn.hutool.core.util.ObjectUtil;
import com.boplus.constant.ShopConstants;
import com.boplus.constant.SystemConfigConstants;
import com.boplus.logging.aop.log.Log;
import com.boplus.modules.shop.domain.YxSystemConfig;
import com.boplus.modules.shop.service.YxSystemConfigService;
import com.boplus.modules.shop.service.dto.YxSystemConfigQueryCriteria;
import com.boplus.mp.config.WxMpConfiguration;
import com.boplus.mp.config.WxPayConfiguration;
import com.boplus.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author
* @date 2019-10-10
*/
@Api(tags = "商城:配置管理")
@RestController
@RequestMapping("api")
public class SystemConfigController {

    private final YxSystemConfigService yxSystemConfigService;

    public SystemConfigController(YxSystemConfigService yxSystemConfigService) {
        this.yxSystemConfigService = yxSystemConfigService;
    }

    @Log("查询")
    @ApiOperation(value = "查询")
    @GetMapping(value = "/yxSystemConfig")
    @PreAuthorize("@el.check('admin','YXSYSTEMCONFIG_ALL','YXSYSTEMCONFIG_SELECT')")
    public ResponseEntity getYxSystemConfigs(YxSystemConfigQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(yxSystemConfigService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增或修改")
    @ApiOperation(value = "新增或修改")
    @PostMapping(value = "/yxSystemConfig")
    @CacheEvict(cacheNames = ShopConstants.BOPLUS_REDIS_INDEX_KEY,allEntries = true)
    @PreAuthorize("@el.check('admin','YXSYSTEMCONFIG_ALL','YXSYSTEMCONFIG_CREATE')")
    public ResponseEntity create(@RequestBody String jsonStr){
        //if(StrUtil.isNotEmpty("22")) throw new BadRequestException("演示环境禁止操作");
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        jsonObject.forEach(
                (key,value)->{
                    YxSystemConfig yxSystemConfig = yxSystemConfigService.getOne(new LambdaQueryWrapper<YxSystemConfig>()
                            .eq(YxSystemConfig::getMenuName,key));
                    YxSystemConfig yxSystemConfigModel = new YxSystemConfig();
                    yxSystemConfigModel.setMenuName(key);
                    yxSystemConfigModel.setValue(value.toString());
                    //重新配置微信相关
                    if(SystemConfigConstants.WECHAT_APPID.equals(key)){
                        WxMpConfiguration.removeWxMpService();
                        WxPayConfiguration.removeWxPayService();
                    }
                    if(SystemConfigConstants.WXPAY_MCHID.equals(key) || SystemConfigConstants.WXAPP_APPID.equals(key)){
                        WxPayConfiguration.removeWxPayService();
                    }
                    RedisUtil.set(key,value.toString(),0);
                    if(ObjectUtil.isNull(yxSystemConfig)){
                        yxSystemConfigService.save(yxSystemConfigModel);
                    }else{
                        yxSystemConfigModel.setId(yxSystemConfig.getId());
                        yxSystemConfigService.saveOrUpdate(yxSystemConfigModel);
                    }
                }
        );

        return new ResponseEntity(HttpStatus.CREATED);
    }



}
