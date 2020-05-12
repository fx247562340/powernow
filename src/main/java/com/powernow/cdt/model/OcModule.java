package com.powernow.cdt.model;

import java.io.Serializable;

public class OcModule implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oc_module.module_id
     *
     * @mbggenerated
     */
    private Integer moduleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oc_module.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oc_module.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oc_module.setting
     *
     * @mbggenerated
     */
    private String setting;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table oc_module
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oc_module.module_id
     *
     * @return the value of oc_module.module_id
     *
     * @mbggenerated
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oc_module.module_id
     *
     * @param moduleId the value for oc_module.module_id
     *
     * @mbggenerated
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oc_module.name
     *
     * @return the value of oc_module.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oc_module.name
     *
     * @param name the value for oc_module.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oc_module.code
     *
     * @return the value of oc_module.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oc_module.code
     *
     * @param code the value for oc_module.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oc_module.setting
     *
     * @return the value of oc_module.setting
     *
     * @mbggenerated
     */
    public String getSetting() {
        return setting;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oc_module.setting
     *
     * @param setting the value for oc_module.setting
     *
     * @mbggenerated
     */
    public void setSetting(String setting) {
        this.setting = setting == null ? null : setting.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oc_module
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", moduleId=").append(moduleId);
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", setting=").append(setting);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}