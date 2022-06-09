package com.example.managesystem.mapper;

import com.example.managesystem.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author YingChenxing
 * @since 2022-06-04
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select id from sys_role where flag = #{flag}")
    Integer selectByFlag(@Param("flag") String flag);
}
