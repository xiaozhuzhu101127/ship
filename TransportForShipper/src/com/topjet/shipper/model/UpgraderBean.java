package com.topjet.shipper.model;

public class UpgraderBean {

    /**
     * 升级信息
     */
    public String upgradeMessage = "暂无更新信息";
    /**
     * 是否强制升级
     */
    public boolean isMustUpgrade = false;
    /**
     * 升级目标版本号
     */
    public String upgradeVersion;
    
    /**
     * 客户端升级地址
     */
    public String upgradeUrl;
}
