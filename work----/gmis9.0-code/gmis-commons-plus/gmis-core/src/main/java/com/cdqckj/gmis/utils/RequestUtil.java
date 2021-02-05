package com.cdqckj.gmis.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestUtil {

    public static String requestToString(HttpServletRequest request) throws IOException {
        InputStream inStream = request.getInputStream();
        BufferedReader in = null;
        String result = "";
        in = new BufferedReader(
                new InputStreamReader(inStream));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }
}
