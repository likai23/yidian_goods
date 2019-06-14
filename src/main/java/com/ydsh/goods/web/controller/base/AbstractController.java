/**
 * @filename:GoodsAttributeAddController 2019-06-12 10:08:37
 * @project ydsh-saas-service-demo  V1.0
 * Copyright(c) 2020 戴艺辉 Co. Ltd. 
 * All right reserved. 
 */
package com.ydsh.goods.web.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydsh.generator.common.JsonResult;
import com.ydsh.generator.common.PageParam;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;

/**   
 * <p>代码自动生成，请勿修改</p>
 * 
 * <p>说明： 商品销售属性管理新增属性API接口层</P>
 * @version: V1.0
 * @author: 戴艺辉
 *
 */
public class AbstractController<S extends IService<T>,T>{
	
	@Autowired
    protected S baseService;

	protected JsonResult<T> result = new JsonResult<T>();
	/**
	 * @explain 查询对象  <swagger GET请求>
	 * @param   id
	 * @return  JsonResult
	 * @author  戴艺辉
	 * @time    2019-06-12 10:08:37
	 */
    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
	@ApiOperation(value = "获取对象", notes = "作者：戴艺辉")
	@ApiImplicitParam(paramType="path", name = "id", value = "对象id", required = true, dataType = "Long")
	public JsonResult<T> getById(@PathVariable("id")Long id){
		T obj=baseService.getById(id);
		if (null!=obj ) {
			 result.success(obj);
		}else {
			 result.error("查询对象不存在！");
		}
		return result;
	}
	
	/**
	 * @explain 添加
	 * @param   entity
	 * @return  JsonResult
	 * @author  戴艺辉
	 * @time    2019-06-12 10:08:37
	 */
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
	@ApiOperation(value = "添加", notes = "作者：戴艺辉")
	public JsonResult<T> insert(@RequestBody T entity){
		JsonResult<T> result=new JsonResult<T>();
		if (null!=entity) {
			boolean rsg = baseService.save(entity);
			if (rsg) {
				  result.success("添加成功");
			  }else {
				  result.error("添加失败！");
			  }
		}else {
			result.error("请传入正确参数！");
		}
		return result;
	}

	/**
	* @explain 批量添加
	* @param   entityList
	* @return  Boolean
	* @author  戴艺辉
	* @time    2019-06-12 10:08:37
	*/
	@RequestMapping(value = "/insertBatch",method = RequestMethod.POST)
	@ApiOperation(value = "批量添加", notes = "作者：戴艺辉")
	public JsonResult<T> insertBatch(@RequestBody List<T> entityList){
		JsonResult<T> result=new JsonResult<T>();
		List<T> list=entityList;
		if (null!=entityList) {
			boolean rsg = baseService.saveBatch(entityList);
			if (rsg) {
				result.success("添加成功");
			}else {
				result.error("添加失败！");
			}
		}else {
			result.error("请传入正确参数！");
		}
		return result;
	}
	
	/**
	 * @explain 修改
	 * @param   entity
	 * @return  JsonResult
	 * @author  戴艺辉
	 * @time    2019-06-12 10:08:37
	 */
	@RequestMapping(value = "/updateById",method = RequestMethod.POST)
	@ApiOperation(value = "根据id修改", notes = "作者：戴艺辉")
	public JsonResult<T> updateById(@RequestBody T entity){
		JsonResult<T> result=new JsonResult<T>();
		if (null!=entity) {
			boolean rsg = baseService.updateById(entity);
			if (rsg) {
				  result.success("修改成功");
			  }else {
				  result.error("修改失败！");
			  }
		}else {
			result.error("请传入正确参数！");
		}
		return result;
	}
	/**
	* @explain 批量修改
	* @param   entityList
	* @return  JsonResult
	* @author  戴艺辉
	* @time    2019-06-12 10:08:37
	*/
    @RequestMapping(value = "/updateBatchById",method = RequestMethod.POST)
	@ApiOperation(value = "批量根据id修改", notes = "作者：戴艺辉")
	public JsonResult<T> updateBatchById(@RequestBody List<T> entityList) {
		JsonResult<T> result = new JsonResult<T>();
		if (null != entityList) {
			boolean rsg = baseService.updateBatchById(entityList);
			if (rsg) {
				result.success("修改成功");
			} else {
				result.error("修改失败！");
			}
		} else {
			result.error("请传入正确参数！");
		}
		return result;
	}

	/**
	 * @explain 分页条件查询用户   
	 * @param   pageParam
	 * @return  JsonResult
	 * @author  戴艺辉
	 * @time    2019-06-12 10:08:37
	 */
    @RequestMapping(value = "/getPages",method = RequestMethod.GET)
	@ApiOperation(value = "分页查询", notes = "分页查询返回[IPage<T>],作者：戴艺辉")
	public JsonResult<IPage<T>> getPages(PageParam<T> pageParam){
		JsonResult<IPage<T>> returnPage=new JsonResult<IPage<T>>();
		Page<T> page=new Page<T>(pageParam.getPageNum(),pageParam.getPageSize());
		QueryWrapper<T> queryWrapper =new QueryWrapper<T>();
		queryWrapper.setEntity(pageParam.getParam());
		//分页数据
		IPage<T> pageData=baseService.page(page, queryWrapper);
		returnPage.success(pageData);
		
		return returnPage;
	}
}
