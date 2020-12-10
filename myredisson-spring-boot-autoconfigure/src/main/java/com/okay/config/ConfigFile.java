package com.okay.config;

/**
 * <h2>文件配置<h2>
 * @author okay
 * @create 2020-07-08 11:14
 */
public class ConfigFile {
    /**json配置文件方式*/
    private String json;
    /**yaml诶之*/
    private String yaml;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getYaml() {
        return yaml;
    }

    public void setYaml(String yaml) {
        this.yaml = yaml;
    }
}
