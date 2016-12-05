package com.drink.controller;

import com.alibaba.fastjson.JSONObject;
import com.drink.cornerstone.constant.BeanNames;
import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.constant.ControllerNames;
import com.drink.cornerstone.service.ServiceException;
import com.drink.model.ThreeGroup;
import com.drink.module.Message;
import com.drink.module.Page;
import com.drink.module.ThreeGroupVo;
import com.drink.module.TreeNode;
import com.drink.service.ThreeGroupService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by stq on 16-11-16.
 */
@Controller
@RequestMapping(ControllerNames.threeGroupController)
public class ThreeGroupController {
    Log logger = LogFactory.getLog(ThreeGroupController.class);
    @Resource(name= BeanNames.threeGroupService)
    ThreeGroupService threeGroupService;

    /**
     * 创建组
     * @param group
     * @return
     */
    @RequestMapping(value = ControllerNames.threeGroupController_saveThreeGroup ,method = RequestMethod.POST)
    public @ResponseBody
    JSONObject saveThreeGroup(@ModelAttribute ThreeGroup group){
        Message msg=new Message();
        try {
            threeGroupService.saveThreeGroup(group);
            return msg.getResult(true,false, ConstantElement.commonSuccess,null,null);
        }catch (ServiceException e) {
            logger.error("添加抱团组时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("添加抱团组时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

    /**
     * 根据条件分页获取组
     * @param page
     * @param vo
     * @return
     */
    @RequestMapping(value = ControllerNames.threeGroupController_findPageThreeGroupByCondition ,method = RequestMethod.POST)
    public @ResponseBody JSONObject findPageThreeGroupByCondition(@ModelAttribute Page<ThreeGroupVo> page, @ModelAttribute ThreeGroupVo vo){
        Message msg=new Message();
        try {
            page.setObj(vo);
            page=threeGroupService.findPageThreeGroupByCondition(page);
            return msg.getResult(true,false,page,null,null);
        }catch (ServiceException e) {
            logger.error("获取抱团组列表时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("获取抱团组列表时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }
    /**
     * 获取组的结构图
     * @return
     */
    @RequestMapping(value = ControllerNames.threeGroupController_findStructurlByGroupId ,method = RequestMethod.POST)
    public @ResponseBody JSONObject findStructurlByGroupId(@RequestParam("groupId") Long groupId){
        Message msg=new Message();
        try {
            List<TreeNode> list = threeGroupService.findStructurlByGroupId(groupId);
            return msg.getResult(true,false,list,null,null);
        }catch (ServiceException e) {
            logger.error("获取抱团组列表时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }catch (Exception e){
            logger.error("获取抱团组列表时失败："+e.getMessage());
            return msg.getResult(false,true,null,ConstantElement.commonError,ConstantElement.errorForbidCode);
        }
    }

}
