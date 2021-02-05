package com.cdqckj.gmis.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LatitudeUtils {
    public static final String KEY_1 = "6CGY4AsSlhc1DPaOX1Kedlm3f9EobkAP";

    public static Map<String,String> getGeocoderLatitude(String address){
        BufferedReader in = null;
        try {
            //将地址转换成utf-8的16进制
            address = URLEncoder.encode(address, "UTF-8");
//       如果有代理，要设置代理，没代理可注释
//      System.setProperty("http.proxyHost","192.168.1.188");
//      System.setProperty("http.proxyPort","3128");
            URL tirc = new URL("http://api.map.baidu.com/geocoder?address="+ address +"&output=json&key="+ KEY_1);

            in = new BufferedReader(new InputStreamReader(tirc.openStream(),"UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while((res = in.readLine())!=null){
                sb.append(res.trim());
            }
            String str = sb.toString();
            Map<String,String> map = null;
            if(StringUtils.isNotEmpty(str)){
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if(lngStart > 0 && lngEnd > 0 && latEnd > 0){
                    String lng = str.substring(lngStart+5, lngEnd);
                    String lat = str.substring(lngEnd+7, latEnd);
                    map = new HashMap<String,String>();
                    map.put("lng", lng);
                    map.put("lat", lat);
                    return map;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static void main(String args[]){
        Map<String, String> json = LatitudeUtils.getGeocoderLatitude("成都天府广场");
        System.out.println("lng :经度 "+json.get("lng"));
        System.out.println("lat :纬度 "+json.get("lat"));
    }

}
