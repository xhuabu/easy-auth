package com.xhuabu.source.model.po;

import java.util.Date;

public class Auth {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.id
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.name
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.uri
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    private String uri;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.create_admin_id
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    private Integer createAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.create_time
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.update_time
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column auth.comment
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    private String comment;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.id
     *
     * @return the value of auth.id
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.id
     *
     * @param id the value for auth.id
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.name
     *
     * @return the value of auth.name
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.name
     *
     * @param name the value for auth.name
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.uri
     *
     * @return the value of auth.uri
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public String getUri() {
        return uri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.uri
     *
     * @param uri the value for auth.uri
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.create_admin_id
     *
     * @return the value of auth.create_admin_id
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public Integer getCreateAdminId() {
        return createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.create_admin_id
     *
     * @param createAdminId the value for auth.create_admin_id
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public void setCreateAdminId(Integer createAdminId) {
        this.createAdminId = createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.create_time
     *
     * @return the value of auth.create_time
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.create_time
     *
     * @param createTime the value for auth.create_time
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.update_time
     *
     * @return the value of auth.update_time
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.update_time
     *
     * @param updateTime the value for auth.update_time
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column auth.comment
     *
     * @return the value of auth.comment
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column auth.comment
     *
     * @param comment the value for auth.comment
     *
     * @mbg.generated Sat Jul 07 23:56:18 CST 2018
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}