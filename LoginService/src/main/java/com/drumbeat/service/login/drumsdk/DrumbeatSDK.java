package com.drumbeat.service.login.drumsdk;

import com.drumbeat.service.login.drumsdk.helper.HttpHelper;

/**
 * Created by ZuoHailong on 2019/12/2.
 */
public class DrumbeatSDK {

    private DrumbeatSDK() {
    }

    public static DrumbeatSDK newInstance() {
        return InstaneceHelper.instance;
    }

    public void init() {
        HttpHelper.init();
    }

    private static class InstaneceHelper {

        private static DrumbeatSDK instance = new DrumbeatSDK();

    }

}
