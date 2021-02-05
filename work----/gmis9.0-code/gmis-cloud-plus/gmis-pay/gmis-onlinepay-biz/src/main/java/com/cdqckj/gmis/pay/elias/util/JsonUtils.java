package com.cdqckj.gmis.pay.elias.util;

//import com.elias.cert.CertificateList;
import com.alibaba.fastjson.JSON;
import com.cdqckj.gmis.pay.elias.cert.CertificateList;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: chen
 * @date: 2019/7/24
 **/
public class JsonUtils {

    private static GsonBuilder builder = new GsonBuilder();
    private static Gson gson;

    static {
        gson = builder
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeAdapter())
                .create();
    }

    public static CertificateList convertJsonToCertList(String target) {
        return gson.fromJson(target, CertificateList.class);
    }

    /**
     * Gson TypeAdapter for JSR310 OffsetDateTime type
     */
    public static class OffsetDateTimeTypeAdapter extends TypeAdapter<OffsetDateTime> {

        private DateTimeFormatter formatter;

        public OffsetDateTimeTypeAdapter() {
            this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        public OffsetDateTimeTypeAdapter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        public void setFormat(DateTimeFormatter dateFormat) {
            this.formatter = dateFormat;
        }

        @Override
        public void write(JsonWriter out, OffsetDateTime date) throws IOException {
            if (date == null) out.nullValue();
            else out.value(formatter.format(date));
        }

        @Override
        public OffsetDateTime read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String date = in.nextString();
            if (date.endsWith("+0000")) {
                date = date.substring(0, date.length() - 5) + "Z";
            }
            return OffsetDateTime.parse(date, formatter);
        }
    }

    public static Map<String, String> stringToMap(String str) {
        //Map<String, Object> res = gson.fromJson(str, new TypeToken<Map<String, Object>>() {}.getType());
        Map<String,String> map = JSON.parseObject(str, HashMap.class);
        return map;
    }

    public static String getSecondTimestampTwo(Date date){
        if (null == date) {
            return "";
        }
        return String.valueOf(date.getTime()/1000);
    }

}
