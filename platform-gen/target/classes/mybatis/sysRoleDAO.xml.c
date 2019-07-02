<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.xuhe.platform.dal.SysRoleDAO">

    <resultMap type="com.xuhe.platform.dal.model.SysRoleDO" id="BaseResultMap">
            <result column="role_id" jdbcType="INT" property="roleId" />
            <result column="role_name" jdbcType="VARCHAR" property="roleName" />
            <result column="remark" jdbcType="VARCHAR" property="remark" />
            <result column="create_time" jdbcType="DATETIME" property="createTime" />
            <result column="update_time" jdbcType="DATETIME" property="updateTime" />
            <result column="is_delete" jdbcType="BIT" property="isDelete" />
    </resultMap>

    <sql id="Base_Column_List">
        role_id,role_name,remark,create_time,update_time,is_delete
    </sql>

    <!--按主键查询-->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Long">
        select
        <include refid="Base_Column_List"/>
        from sys_role
        where
            role_id=#{roleId,jdbcType=INT}
    </select>


    <!--按主键删除-->
    <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
        delete from sys_role
        where
            role_id=#{roleId,jdbcType=INT}
    </delete>

    <!--插入记录 全部字段-->
    <insert id="insert" parameterType="com.xuhe.platform.dal.model.SysRoleDO" useGeneratedKeys="true">
        insert into sys_role
        (role_name,remark,create_time,update_time,is_delete)
        values
        (<trim prefix="(" suffix=")" suffixOverrides=",">#{roleName,jdbcType=VARCHAR},#{remark,jdbcType=VARCHAR},#{createTime,jdbcType=DATETIME},#{updateTime,jdbcType=DATETIME},#{isDelete,jdbcType=BIT},</trim>)
    </insert>

    <!--插入记录 全部字段忽略掉 字段值为null或者空 -->
    <insert id="insertSelective" parameterType="com.xuhe.platform.dal.model.SysRoleDO" useGeneratedKeys="true">
        insert into sys_role
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="roleName != null" >
                role_name,
            </if>
            <if test="remark != null" >
                remark,
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
            <if test="roleName != null" >
                #{roleName,jdbcType=VARCHAR},
            </if>
            <if test="remark != null" >
                #{remark,jdbcType=VARCHAR},
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
    <update id="updateByPrimaryKeySelective" parameterType="com.xuhe.platform.dal.model.SysRoleDO">
        update sys_role
        <set>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="roleName != null and roleName!= ''" >
                role_name=#{roleName},
            </if>
            <if test="remark != null and remark!= ''" >
                remark=#{remark},
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
        where  role_id=#{roleId}
    </update>

    <!-- 按主键更新全部字段-->
    <update id="updateByPrimaryKey" parameterType="com.xuhe.platform.dal.model.SysRoleDO">
        update sys_role
        <set>
        <trim prefix="(" suffix=")" suffixOverrides=",">
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            role_name=#{roleName},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            remark=#{remark},
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