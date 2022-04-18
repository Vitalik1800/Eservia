//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.paylibliqpay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class LiqPayRequest {
    LiqPayRequest() {
    }

    static String post(String url, Map<String, String> list) throws IOException {
        String postData = "";

        Entry entry;
        for(Iterator var3 = list.entrySet().iterator(); var3.hasNext(); postData = postData + entry.getKey() + "=" + URLEncoder.encode((String)entry.getValue(), "UTF-8") + "&") {
            entry = (Entry)var3.next();
        }

        URL obj = new URL(url);
        BufferedReader in = null;
        HttpURLConnection conn = null;

        try {
            LogUtil.log("==== req:" + url + "   data: " + postData);
            conn = (HttpURLConnection)obj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();

            String inputLine;
            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            LogUtil.log("==== resp:" + response);
            return response.toString();
        } finally {
            if (in != null) {
                in.close();
            }

            if (conn != null) {
                conn.disconnect();
            }

        }
    }
}
