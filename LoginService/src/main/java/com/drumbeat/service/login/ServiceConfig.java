package com.drumbeat.service.login;

import android.content.Context;
import android.text.TextUtils;

import com.drumbeat.sdk.helper.HttpHelper;

/**
 * Created by ZuoHailong on 2019/12/3.
 */
public final class ServiceConfig {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final String appId;
    private final String tenant;
    private final String baseUrl;

    private ServiceConfig(Builder builder) {
        this.appId = builder.appId;
        this.tenant = builder.tenant;
        this.baseUrl = builder.baseUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getTenant() {
        return TextUtils.isEmpty(tenant) ? "zt" : tenant;
    }

    public String getBaseUrl() {
        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    public final static class Builder {

        private String appId;
        private String tenant;
        private String baseUrl;

        private Builder() {
        }

        /**
         * 设置应用ID
         *
         * @param appId 应用ID
         */
        public Builder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        /**
         * 设置租户
         *
         * @param tenant 租户
         */
        public Builder setTenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        /**
         * 设置要连接的中台服务器URL，形如：http://192.168.20.233:30060/
         *
         * @param baseUrl url
         */
        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 构建ServiceConfig实例
         *
         * @return ServiceConfig实例
         */
        public ServiceConfig build() {
//            HttpHelper.init();
            return new ServiceConfig(this);
        }
    }
}
