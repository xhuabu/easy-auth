package com.xhuabu.source.model.po;

import java.util.Date;

public class GroupAuth {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_auth.id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_auth.group_id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    private Integer groupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_auth.uri
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    private String uri;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_auth.create_admin_id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    private Integer createAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_auth.create_time
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_auth.update_time
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column group_auth.comment
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    private String comment;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_auth.id
     *
     * @return the value of group_auth.id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_auth.id
     *
     * @param id the value for group_auth.id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_auth.group_id
     *
     * @return the value of group_auth.group_id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_auth.group_id
     *
     * @param groupId the value for group_auth.group_id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_auth.uri
     *
     * @return the value of group_auth.uri
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public String getUri() {
        return uri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_auth.uri
     *
     * @param uri the value for group_auth.uri
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_auth.create_admin_id
     *
     * @return the value of group_auth.create_admin_id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public Integer getCreateAdminId() {
        return createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_auth.create_admin_id
     *
     * @param createAdminId the value for group_auth.create_admin_id
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public void setCreateAdminId(Integer createAdminId) {
        this.createAdminId = createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_auth.create_time
     *
     * @return the value of group_auth.create_time
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_auth.create_time
     *
     * @param createTime the value for group_auth.create_time
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_auth.update_time
     *
     * @return the value of group_auth.update_time
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_auth.update_time
     *
     * @param updateTime the value for group_auth.update_time
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column group_auth.comment
     *
     * @return the value of group_auth.comment
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column group_auth.comment
     *
     * @param comment the value for group_auth.comment
     *
     * @mbg.generated Tue Jun 05 22:56:52 CST 2018
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}