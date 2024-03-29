package com.drumbeat.service.login;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.drumbeat.service.login.bean.LoginResultBean;
import com.drumbeat.service.login.bean.ResultBean;
import com.drumbeat.service.login.bean.TenantBean;
import com.drumbeat.service.login.bean.UserInfoBean;
import com.drumbeat.service.login.config.ServiceConfig;
import com.drumbeat.service.login.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ZuoHailong on 2019/10/17.
 */
public class LoginService {

    private static final String SP_TENANT_ID = "sp_tenant_id";
    private static ServiceConfig sConfig;

    public static void setConfig(ServiceConfig config) {
        if (sConfig == null) {
            synchronized (ServiceConfig.class) {
                if (sConfig == null) {
                    sConfig = config == null ? ServiceConfig.newBuilder().build() : config;
                }
            }
        }
    }

    static ServiceConfig getConfig() {
        // 保证sConfig不是null
        setConfig(null);
        return sConfig;
    }

    /**
     * 设置租户ID
     *
     * @param tenantId 租户ID
     */
    public static void setTenantId(String tenantId) {
        SharedPreferencesUtil.getInstance(Utils.getApp()).put(SP_TENANT_ID, tenantId);
    }

    /**
     * 获取已设置的租户ID
     *
     * @return
     */
    public static String getTenantId() {
        return SharedPreferencesUtil.getInstance(Utils.getApp()).getString(SP_TENANT_ID);
    }

    /**
     * 查询账户所在的租户集合
     *
     * @param account  手机号/账号/邮箱号/身份证号
     * @param callback
     */
    public static void getTenantList(String account, ResultCallback<List<TenantBean.ResultBean>> callback) {
        ProcessControl.getTenantList(account, callback);
    }

    /**
     * 登录中台
     *
     * @param account
     * @param password
     * @param callback
     */
    public static void login(String account, String password, ResultCallback<LoginResultBean> callback) {
        String centralizerToken = getCentralizerToken();
        // 已有token，直接返回，不再登录
        if (!TextUtils.isEmpty(centralizerToken)) {
            callback.onSuccess(new LoginResultBean().setToken(centralizerToken));
            return;
        }
        ProcessControl.login(LoginService.getConfig(), account, password, callback);
    }

    /**
     * 登录中台，供宿主APP使用
     *
     * @param serviceConfig 可选，一次性参数
     * @param account
     * @param password
     * @param callback
     */
    public static void login(ServiceConfig serviceConfig, String account, String password, ResultCallback<LoginResultBean> callback) {
        ProcessControl.login(serviceConfig, account, password, callback);
    }

    /**
     * 修改密码
     */
    public static void modifyPassword(@NonNull String oldPwd, @NonNull String newPwd, @NonNull String centralizerToken, ResultCallback<ResultBean> callback) {
        ProcessControl.modifyPwd(oldPwd, newPwd, centralizerToken, callback);
    }

    /**
     * 查询用户信息
     */
    public static void getUserInfo(@NonNull String centralizerToken, ResultCallback<UserInfoBean.ResultBean> callback) {
        ProcessControl.getUserInfo(centralizerToken, callback);
    }

    /**
     * 扫码登录，目前用于web页的登录
     */
    public static void scan(Activity activity, ResultCallback callback) {
        ProcessControl.scan(activity, callback);
    }

    /**
     * 获取centralizerToken（中台appToken），供ghostAPP使用，独立APP调用此方法无法获取到centralizerToken
     */
    public static String getCentralizerToken() {
        return getCentralizerToken(ActivityUtils.getTopActivity());
    }

    /**
     * 获取centralizerToken（中台appToken），供ghostAPP使用，独立APP调用此方法无法获取到centralizerToken
     */
    public static String getCentralizerToken(Context context) {
        return ProcessControl.getTokenFromCP(context);
    }
}
