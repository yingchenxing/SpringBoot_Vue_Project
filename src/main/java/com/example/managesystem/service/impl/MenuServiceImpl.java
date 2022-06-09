package com.example.managesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.managesystem.entity.Menu;
import com.example.managesystem.mapper.MenuMapper;
import com.example.managesystem.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YingChenxing
 * @since 2022-06-05
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {


    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!name.isEmpty())
            queryWrapper.like("name", name);
        List<Menu> list = list(queryWrapper);
        //parent node
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu : parentNode) {
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect((Collectors.toList())));
        }
        return parentNode;
    }
}
