<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.xuhe.platform.dal.SysResourceDAO">

    <resultMap type="com.xuhe.platform.dal.model.SysResourceDO" id="BaseResultMap">
            <result column="resource_id" jdbcType="INT" property="resourceId" />
            <result column="parent_id" jdbcType="INT" property="parentId" />
            <result column="resource_name" jdbcType="VARCHAR" property="resourceName" />
            <result column="url" jdbcType="VARCHAR" property="url" />
            <result column="perms" jdbcType="VARCHAR" property="perms" />
            <result column="type" jdbcType="INT" property="type" />
            <result column="icon" jdbcType="VARCHAR" property="icon" />
            <result column="order_num" jdbcType="INT" property="orderNum" />
            <result column="status" jdbcType="BIT" property="status" />
            <result column="create_time" jdbcType="DATETIME" property="createTime" />
            <result column="update_time" jdbcType="DATETIME" property="updateTime" />
            <result column="is_delete" jdbcType="BIT" property="isDelete" />
    </resultMap>

    <sql id="Base_Column_List">
        resource_id,parent_id,resource_name,url,perms,type,icon,order_num,status,create_time,update_time,is_delete
    </sql>

    <!--按主键查询-->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Long">
        select
        <include refid="Base_Column_List"/>
        from sys_resource
        where
            resource_id=#{resourceId,jdbcType=INT}
    </select>


    <!--按主键删除-->
    <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
        delete from sys_resource
        where
            resource_id=#{resourceId,jdbcType=INT}
    </delete>

    <!--插入记录 全部字段-->
    <insert id="insert" parameterType="com.xuhe.platform.dal.model.SysResourceDO" useGeneratedKeys="true">
        insert into sys_resource
        (parent_id,resource_name,url,perms,type,icon,order_num,status,create_time,update_time,is_delete)
        values
        (<trim prefix="(" suffix=")" suffixOverrides=",">#{parentId,jdbcType=INT},#{resourceName,jdbcType=VARCHAR},#{url,jdbcType=VARCHAR},#{perms,jdbcType=VARCHAR},#{type,jdbcType=INT},#{icon,jdbcType=VARCHAR},#{orderNum,jdbcType=INT},#{status,jdbcType=BIT},#{createTime,jdbcType=DATETIME},#{updateTime,jdbcType=DATETIME},#{isDelete,jdbcType=BIT},</trim>)
    </insert>

    <!--插入记录 全部字段忽略掉 字段值为null或者空 -->
    <insert id="insertSelective" parameterType="com.xuhe.platform.dal.model.SysResourceDO" useGeneratedKeys="true">
        insert into sys_resource
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="parentId != null" >
                parent_id,
            </if>
            <if test="resourceName != null" >
                resource_name,
            </if>
            <if test="url != null" >
                url,
            </if>
            <if test="perms != null" >
                perms,
            </if>
            <if test="type != null" >
                type,
            </if>
            <if test="icon != null" >
                icon,
            </if>
            <if test="orderNum != null" >
                order_num,
            </if>
            <if test="status != null" >
                status,
            </if>
            <if test="createTime != null" >
                create_time,
            </if>
            <if test="updateTime != null" >
                update_time,
            </if>
            <if test="isDelete != null" >
                is_delete,
            </if>
        </trim>
        values
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="parentId != null" >
                #{parentId,jdbcType=INT},
            </if>
            <if test="resourceName != null" >
                #{resourceName,jdbcType=VARCHAR},
            </if>
            <if test="url != null" >
                #{url,jdbcType=VARCHAR},
            </if>
            <if test="perms != null" >
                #{perms,jdbcType=VARCHAR},
            </if>
            <if test="type != null" >
                #{type,jdbcType=INT},
            </if>
            <if test="icon != null" >
                #{icon,jdbcType=VARCHAR},
            </if>
            <if test="orderNum != null" >
                #{orderNum,jdbcType=INT},
            </if>
            <if test="status != null" >
                #{status,jdbcType=BIT},
            </if>
            <if test="createTime != null" >
                #{createTime,jdbcType=DATETIME},
            </if>
            <if test="updateTime != null" >
                #{updateTime,jdbcType=DATETIME},
            </if>
            <if test="isDelete != null" >
                #{isDelete,jdbcType=BIT},
            </if>
        </trim>
    </insert>

    <!-- 按主键更新字段 忽略掉字段值为空或者为null -->
    <update id="updateByPrimaryKeySelective" parameterType="com.xuhe.platform.dal.model.SysResourceDO">
        update sys_resource
        <set>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="parentId != null and parentId!= ''" >
                parent_id=#{parentId},
            </if>
            <if test="resourceName != null and resourceName!= ''" >
                resource_name=#{resourceName},
            </if>
            <if test="url != null and url!= ''" >
                url=#{url},
            </if>
            <if test="perms != null and perms!= ''" >
                perms=#{perms},
            </if>
            <if test="type != null and type!= ''" >
                type=#{type},
            </if>
            <if test="icon != null and icon!= ''" >
                icon=#{icon},
            </if>
            <if test="orderNum != null and orderNum!= ''" >
                order_num=#{orderNum},
            </if>
            <if test="status != null and status!= ''" >
                status=#{status},
            </if>
            <if test="createTime != null and createTime!= ''" >
                create_time=#{createTime},
            </if>
            <if test="updateTime != null and updateTime!= ''" >
                update_time=#{updateTime},
            </if>
            <if test="isDelete != null and isDelete!= ''" >
                is_delete=#{isDelete},
            </if>
        </trim>
        </set>
        where  resource_id=#{resourceId}
    </update>

    <!-- 按主键更新全部字段-->
    <update id="updateByPrimaryKey" parameterType="com.xuhe.platform.dal.model.SysResourceDO">
        update sys_resource
        <set>
        <trim prefix="(" suffix=")" suffixOverrides=",">
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            parent_id=#{parentId},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            resource_name=#{resourceName},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            url=#{url},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            perms=#{perms},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            type=#{type},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            icon=#{icon},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            order_num=#{orderNum},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            status=#{status},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            create_time=#{createTime},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            update_time=#{updateTime},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            is_delete=#{isDelete},
        </trim>
        </set>
    </update>


</mapper>