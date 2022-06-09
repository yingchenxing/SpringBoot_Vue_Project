package com.example.managesystem.service;

import com.example.managesystem.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YingChenxing
 * @since 2022-06-05
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
