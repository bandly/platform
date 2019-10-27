<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${daoPackage}.${className}DAO">

    <resultMap type="${doPackage}.${className}DO" id="BaseResultMap">
        <#list list as item>
            <result column="${item.columnName}" jdbcType="${item.jdbcType}" property="${item.propName}" />
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list list as l><#if l_index == 0>${l.columnName}</#if></#list>,${colsWithoutCommColumns}
    </sql>

    <!--按主键查询-->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Long">
        select
        <include refid="Base_Column_List"/>
        from ${tableNamePrefix}${tableName}
        where
    <#list list as l>
        <#if l_index == 0>
            ${l.columnName}=${r'#{'}${l.propName},jdbcType=${l.jdbcType}${r'}'}
        </#if>
    </#list>
    </select>


    <!--按主键删除-->
    <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
        delete from ${tableNamePrefix}${tableName}
        where
    <#list list as l>
        <#if l_index == 0>
            ${l.columnName}=${r'#{'}${l.propName},jdbcType=${l.jdbcType}${r'}'}
        </#if>
    </#list>
    </delete>

    <!--插入记录 全部字段-->
    <insert id="insert" parameterType="${doPackage}.${className}DO" useGeneratedKeys="true">
        insert into ${tableNamePrefix}${tableName}
        (${colsWithoutCommColumns})
        values
        <trim prefix="(" suffix=")" suffixOverrides=","><#list list as item><#if item_index gt 0>${r'#{'}${item.propName},jdbcType=${item.jdbcType}${r'}'},</#if></#list></trim>
    </insert>

    <!--插入记录 全部字段忽略掉 字段值为null或者空 -->
    <insert id="insertSelective" parameterType="${doPackage}.${className}DO" useGeneratedKeys="true">
        insert into ${tableNamePrefix}${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list list as item >
        <#if item_index gt 0>
            <if test="${item.propName} != null" >
                ${item.columnName},
            </if>
        </#if>
        </#list>
        </trim>
        values
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list list as item >
        <#if item_index gt 0>
            <if test="${item.propName} != null" >
                ${r'#{'}${item.propName},jdbcType=${item.jdbcType}${r'}'},
            </if>
        </#if>
        </#list>
        </trim>
    </insert>

    <!-- 按主键更新字段 忽略掉字段值为空或者为null -->
    <update id="updateByPrimaryKeySelective" parameterType="${doPackage}.${className}DO">
        update ${tableNamePrefix}${tableName}
        <set>
        <trim prefix="" suffix="" suffixOverrides=",">
        <#list list as item >
            <#if item_index gt 0>
            <if test="${item.propName} != null and ${item.propName}!= ''" >
                ${item.columnName}=${r'#{'}${item.propName}${r'}'},
            </if>
            </#if>
        </#list>
        </trim>
        </set>
        where  <#list list as l><#if l_index == 0>${l.columnName}=${r'#{'}${l.propName}${r'}'}</#if></#list>
    </update>

    <!-- 按主键更新全部字段-->
    <update id="updateByPrimaryKey" parameterType="${doPackage}.${className}DO">
        update ${tableNamePrefix}${tableName}
        <set>
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list list as item >
        <#if item_index gt 0>
            ${item.columnName}=${r'#{'}${item.propName}${r'}'},
        </#if>
        </#list>
        </trim>
        </set>
        where
        <#list list as l>
            <#if l_index == 0>
                ${l.columnName}=${r'#{'}${l.propName},jdbcType=${l.jdbcType}${r'}'}
            </#if>
        </#list>
    </update>


</mapper>