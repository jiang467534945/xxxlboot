package com.xxxlboot.common.base;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xxxlboot.common.vo.UserVO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @auther: Easy
 * @date: 18-11-21 18:01
 * @description:
 */
public class BaseServiceImpl < M extends BaseMapper<T>,T extends BaseDTO> extends ServiceImpl<M,T> implements BaseService <T>{

    @Override
    public boolean save(T entity, UserVO userVO) {
        entity.setCreateBy(userVO.getId());
        entity.setCreator(userVO.getNickName());
        entity.setCreateDate(DateTime.now());
        entity.setVersionNumber(0);
        entity.setDelFlag("0");
        return retBool(this.baseMapper.insert(entity));
    }

    public boolean saveBatch(Collection<T> var1, UserVO userVO) {
        var1.forEach((entity ->{
            entity.setCreateBy(userVO.getId());
            entity.setCreator(userVO.getNickName());
            entity.setCreateDate(DateTime.now());
            entity.setVersionNumber(0);
            entity.setDelFlag("0");
        }));
        return super.saveBatch(var1);
    }

    //    @Cacheable(value = "getById",keyGenerator = "caChekeyGenerator")
    @Override
    public T getById(Serializable id) {
        return this.baseMapper.selectById(id);
    }
    //    @Cacheable(value = "getOne",keyGenerator = "caChekeyGenerator")
    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(this.baseMapper.selectList(queryWrapper));
    }
    //    @Caching(
//            put = {
//                    @CachePut(value = "getById",keyGenerator = "caChekeyGenerator"),
//                    @CachePut(value = "getOne",keyGenerator = "caChekeyGenerator"),
//                    @CachePut(value = "list",keyGenerator = "caChekeyGenerator"),
//                    @CachePut(value = "page",keyGenerator = "caChekeyGenerator")
//
//            })
    @Override
    public boolean saveOrUpdate(T entity,UserVO userVO) {
        entity.setCreateBy(userVO.getId());
        entity.setCreator(userVO.getNickName());
        entity.setCreateDate(DateTime.now());
        entity.setVersionNumber(0);
        entity.setDelFlag("0");
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(T entity,UserVO userVO) {
        entity.setUpdateBy(userVO.getId());
        entity.setUpdater(userVO.getNickName());
        entity.setUpdateDate(DateTime.now());
        entity.setDelFlag("0");
        return super.updateById(entity);
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper, UserVO userVO) {
        entity.setUpdateBy(userVO.getId());
        entity.setUpdater(userVO.getNickName());
        entity.setUpdateDate(DateTime.now());
        entity.setDelFlag("0");
        return super.update(entity, updateWrapper);
    }


    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        return super.pageMaps(page, queryWrapper);
    }



}
