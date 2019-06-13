package com.xuhe.platform.dal.model;

import java.util.Date;

public class SysRoleMenu {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_menu.id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_menu.role_id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_menu.menu_id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    private Integer menuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_menu.remark
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_menu.create_time
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_menu.update_time
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role_menu.is_delete
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    private Boolean isDelete;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_menu.id
     *
     * @return the value of sys_role_menu.id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_menu.id
     *
     * @param id the value for sys_role_menu.id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_menu.role_id
     *
     * @return the value of sys_role_menu.role_id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_menu.role_id
     *
     * @param roleId the value for sys_role_menu.role_id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_menu.menu_id
     *
     * @return the value of sys_role_menu.menu_id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_menu.menu_id
     *
     * @param menuId the value for sys_role_menu.menu_id
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_menu.remark
     *
     * @return the value of sys_role_menu.remark
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_menu.remark
     *
     * @param remark the value for sys_role_menu.remark
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_menu.create_time
     *
     * @return the value of sys_role_menu.create_time
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_menu.create_time
     *
     * @param createTime the value for sys_role_menu.create_time
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_menu.update_time
     *
     * @return the value of sys_role_menu.update_time
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_menu.update_time
     *
     * @param updateTime the value for sys_role_menu.update_time
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role_menu.is_delete
     *
     * @return the value of sys_role_menu.is_delete
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role_menu.is_delete
     *
     * @param isDelete the value for sys_role_menu.is_delete
     *
     * @mbg.generated Mon Jun 10 11:28:45 CST 2019
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}