package com.zagvladimir.configuration;

import com.zagvladimir.controller.mappers.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {

    @Bean
    public UserMapper userMapper(){
        return Mappers.getMapper(UserMapper.class);
    }

     @Bean
    public SubItemTypeMapper subItemTypeMapper(){
        return Mappers.getMapper(SubItemTypeMapper.class);
    }

     @Bean
    public RoleMapper roleMapper(){
        return Mappers.getMapper(RoleMapper.class);
    }

     @Bean
    public ItemMapper itemMapper(){
        return Mappers.getMapper(ItemMapper.class);
    }

     @Bean
    public ItemLeasedMapper itemLeasedMapper(){
        return Mappers.getMapper(ItemLeasedMapper.class);
    }

     @Bean
    public ItemCategoryMapper itemCategoryMapper(){
        return Mappers.getMapper(ItemCategoryMapper.class);
    }

     @Bean
    public GradeMapper gradeMapper(){
        return Mappers.getMapper(GradeMapper.class);
    }
}
