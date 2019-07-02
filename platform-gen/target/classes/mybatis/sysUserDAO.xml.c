<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.xuhe.platform.dal.SysUserDAO">

    <resultMap type="com.xuhe.platform.dal.model.SysUserDO" id="BaseResultMap">
            <result column="user_id" jdbcType="INT" property="userId" />
            <result column="account" jdbcType="VARCHAR" property="account" />
            <result column="password" jdbcType="VARCHAR" property="password" />
            <result column="email" jdbcType="VARCHAR" property="email" />
            <result column="mobile" jdbcType="VARCHAR" property="mobile" />
            <result column="status" jdbcType="TINYINT" property="status" />
            <result column="create_user_id" jdbcType="INT" property="createUserId" />
            <result column="create_time" jdbcType="DATETIME" property="createTime" />
            <result column="update_time" jdbcType="DATETIME" property="updateTime" />
            <result column="is_delete" jdbcType="BIT" property="isDelete" />
    </resultMap>

    <sql id="Base_Column_List">
        user_id,account,password,email,mobile,status,create_user_id,create_time,update_time,is_delete
    </sql>

    <!--按主键查询-->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.Long">
        select
        <include refid="Base_Column_List"/>
        from sys_user
        where
            user_id=#{userId,jdbcType=INT}
    </select>


    <!--按主键删除-->
    <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
        delete from sys_user
        where
            user_id=#{userId,jdbcType=INT}
    </delete>

    <!--插入记录 全部字段-->
    <insert id="insert" parameterType="com.xuhe.platform.dal.model.SysUserDO" useGeneratedKeys="true">
        insert into sys_user
        (account,password,email,mobile,status,create_user_id,create_time,update_time,is_delete)
        values
        (<trim prefix="(" suffix=")" suffixOverrides=",">#{account,jdbcType=VARCHAR},#{password,jdbcType=VARCHAR},#{email,jdbcType=VARCHAR},#{mobile,jdbcType=VARCHAR},#{status,jdbcType=TINYINT},#{createUserId,jdbcType=INT},#{createTime,jdbcType=DATETIME},#{updateTime,jdbcType=DATETIME},#{isDelete,jdbcType=BIT},</trim>)
    </insert>

    <!--插入记录 全部字段忽略掉 字段值为null或者空 -->
    <insert id="insertSelective" parameterType="com.xuhe.platform.dal.model.SysUserDO" useGeneratedKeys="true">
        insert into sys_user
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="account != null" >
                account,
            </if>
            <if test="password != null" >
                password,
            </if>
            <if test="email != null" >
                email,
            </if>
            <if test="mobile != null" >
                mobile,
            </if>
            <if test="status != null" >
                status,
            </if>
            <if test="createUserId != null" >
                create_user_id,
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
            <if test="account != null" >
                #{account,jdbcType=VARCHAR},
            </if>
            <if test="password != null" >
                #{password,jdbcType=VARCHAR},
            </if>
            <if test="email != null" >
                #{email,jdbcType=VARCHAR},
            </if>
            <if test="mobile != null" >
                #{mobile,jdbcType=VARCHAR},
            </if>
            <if test="status != null" >
                #{status,jdbcType=TINYINT},
            </if>
            <if test="createUserId != null" >
                #{createUserId,jdbcType=INT},
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
    <update id="updateByPrimaryKeySelective" parameterType="com.xuhe.platform.dal.model.SysUserDO">
        update sys_user
        <set>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="account != null and account!= ''" >
                account=#{account},
            </if>
            <if test="password != null and password!= ''" >
                password=#{password},
            </if>
            <if test="email != null and email!= ''" >
                email=#{email},
            </if>
            <if test="mobile != null and mobile!= ''" >
                mobile=#{mobile},
            </if>
            <if test="status != null and status!= ''" >
                status=#{status},
            </if>
            <if test="createUserId != null and createUserId!= ''" >
                create_user_id=#{createUserId},
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
        where  user_id=#{userId}
    </update>

    <!-- 按主键更新全部字段-->
    <update id="updateByPrimaryKey" parameterType="com.xuhe.platform.dal.model.SysUserDO">
        update sys_user
        <set>
        <trim prefix="(" suffix=")" suffixOverrides=",">
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            account=#{account},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            password=#{password},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            email=#{email},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            mobile=#{mobile},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            status=#{status},
        </trim>
        <trim prefix="(" suffix=")" suffixOverrides=",">
            create_user_id=#{createUserId},
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