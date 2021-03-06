//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.paylibliqpay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class LiqPayUtil {
    protected static String LIQPAY_API_URL_CHECKOUT = "https://www.liqpay.ua/api/3/checkout";
    protected static String LIQPAY_API_URL_REQUEST = "https://www.liqpay.ua/api/request/";
    protected static final String APP_PREFERENCES_FILE_NAME = "paylibliqpay";
    protected static final String APP_PREFERENCES_HASH_DEVICE = "ua.privatbank.paylibliqpay.hash_device";
    protected static final String BROADCAST_RECEIVER_ACTION = "ua.privatbank.paylibliqpay.broadcast";

    public LiqPayUtil() {
    }

    protected static byte[] sha1(String param) {
        try {
            MessageDigest SHA = MessageDigest.getInstance("SHA-1");
            SHA.reset();
            SHA.update(param.getBytes(StandardCharsets.UTF_8));
            return SHA.digest();
        } catch (Exception var2) {
            throw new RuntimeException("Can't calc SHA-1 hash", var2);
        }
    }

    protected static String base64Encode(byte[] bytes) {
        return Base64.encodeToString(bytes, 2);
    }

    protected static String base64Encode(String data) {
        return base64Encode(data.getBytes());
    }

    public static Map<String, String> generateData(Map<String, String> params, String privateKey) {
        HashMap<String, String> apiData = new HashMap<>();
        String data = base64Encode((new JSONObject(params)).toString());
        apiData.put("data", data);
        apiData.put("signature", createSignature(data, privateKey));
        return apiData;
    }

    protected static String strToSign(String str) {
        return base64Encode(sha1(str));
    }

    protected static String createSignature(String base64EncodedData, String privateKey) {
        return strToSign(privateKey + base64EncodedData + privateKey);
    }

    protected static boolean isOnline(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo i = conMgr.getActiveNetworkInfo();
            if (i == null) {
                return true;
            } else {
                return !i.isConnected();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return true;
        }
    }

    @SuppressLint("HardwareIds")
    protected static String getHashDevice(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("paylibliqpay", 0);
        String hashDevice = preferences.getString("ua.privatbank.paylibliqpay.hash_device", null);
        if (hashDevice == null) {
            TelephonyManager tm = (TelephonyManager)context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            String tmDevice = "";
            if (tm.getDeviceId() != null) {
                tmDevice = tm.getDeviceId();
            }

            String androidId = Secure.getString(context.getContentResolver(), "android_id");
            if (androidId == null) {
                androidId = "";
            }

            UUID deviceUuid = new UUID(androidId.hashCode(), (long)tmDevice.hashCode() << 32);
            hashDevice = deviceUuid.toString();
            preferences.edit().putString("ua.privatbank.paylibliqpay.hash_device", hashDevice).apply();
        }

        return hashDevice;
    }

    protected static boolean checkPermissions(Context contexts, String[] permissions) {
        int var3 = permissions.length;

        for (String permission : permissions) {
            int res = contexts.checkCallingOrSelfPermission(permission);
            if (res != 0) {
                return true;
            }
        }

        return false;
    }

    public static JSONObject addAll(JSONObject object1, JSONObject object2) {
        Iterator<String> iter = object2.keys();

        while(iter.hasNext()) {
            String key = iter.next();

            try {
                object1.put(key, object2.get(key));
            } catch (JSONException var5) {
                var5.printStackTrace();
            }
        }

        return object1;
    }
}
