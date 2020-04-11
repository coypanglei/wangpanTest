package com.gianlu.commonutils.analytics;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.gianlu.commonutils.FossUtils;
import com.gianlu.commonutils.preferences.CommonPK;
import com.gianlu.commonutils.preferences.Prefs;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.UUID;

public abstract class AnalyticsApplication extends BaseCommonApplication {
    private static FirebaseAnalytics ANALYTICS;
    private static boolean CRASHLYTICS_ENABLED = false;

    public static void sendAnalytics(@NonNull String eventName) {
        sendAnalytics(eventName, null);
    }

    public static void sendAnalytics(@NonNull String eventName, @Nullable Bundle bundle) {
        if (ANALYTICS != null) ANALYTICS.logEvent(eventName, bundle);
    }

    public static void setCrashlyticsString(@NonNull String key, @NonNull String val) {
        if (CRASHLYTICS_ENABLED) Crashlytics.setString(key, val);
    }

    public static void setCrashlyticsInt(@NonNull String key, int val) {
        if (CRASHLYTICS_ENABLED) Crashlytics.setInt(key, val);
    }

    public static void setCrashlyticsLong(@NonNull String key, long val) {
        if (CRASHLYTICS_ENABLED) Crashlytics.setLong(key, val);
    }

    public static void setCrashlyticsBool(@NonNull String key, boolean val) {
        if (CRASHLYTICS_ENABLED) Crashlytics.setBool(key, val);
    }

    public static void crashlyticsLog(@NonNull String msg) {
        if (CRASHLYTICS_ENABLED) Crashlytics.log(msg);
    }

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();

        String uuid = Prefs.getString(CommonPK.ANALYTICS_USER_ID, null);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            Prefs.putString(CommonPK.ANALYTICS_USER_ID, uuid);
        }

        if (FossUtils.hasCrashlytics()) {
            if (Prefs.getBoolean(CommonPK.CRASH_REPORT_ENABLED)) {
                Crashlytics.setUserIdentifier(uuid);
                CRASHLYTICS_ENABLED = true;
            } else {
                CRASHLYTICS_ENABLED = false;
            }
        } else {
            Prefs.putBoolean(CommonPK.CRASH_REPORT_ENABLED, false);
        }

        if (FossUtils.hasFirebaseAnalytics()) {
            ANALYTICS = FirebaseAnalytics.getInstance(this);
            if (Prefs.getBoolean(CommonPK.TRACKING_ENABLED)) {
                ANALYTICS.setUserId(uuid);
                ANALYTICS.setAnalyticsCollectionEnabled(true);
            } else {
                ANALYTICS.setAnalyticsCollectionEnabled(false);
                ANALYTICS = null;
            }
        } else {
            Prefs.putBoolean(CommonPK.TRACKING_ENABLED, false);
        }
    }
}
