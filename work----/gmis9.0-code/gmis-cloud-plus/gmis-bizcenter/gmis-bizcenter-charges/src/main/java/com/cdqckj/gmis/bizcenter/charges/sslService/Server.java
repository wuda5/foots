package com.cdqckj.gmis.bizcenter.charges.sslService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.bizcenter.charges.dto.AlipayBody;
import com.cdqckj.gmis.bizcenter.charges.dto.AlipayHead;
import com.cdqckj.gmis.bizcenter.charges.dto.AlipayParam;
import com.cdqckj.gmis.bizcenter.charges.dto.RcvblDet;
import com.cdqckj.gmis.bizcenter.charges.enumeration.AlipayRequestCodeEnum;
import com.cdqckj.gmis.bizcenter.charges.service.ThirdChargeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Server {


    public static class ClientHandler implements Runnable {

        private Socket socket;
        private ThirdChargeService thirdChargeService;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
                //封装输入流（接收客户端的流）
                BufferedInputStream bis = new BufferedInputStream(
                        socket.getInputStream());
                DataInputStream dis = new DataInputStream(bis);
                byte[] bytes = new byte[1]; // 一次读取一个byte
                StringBuffer ret = new StringBuffer();
                while (dis.read(bytes) != -1) {
                    ret.append(bytesToHexString(bytes));
                    if (dis.available() == 0) { //一个请求
                        String codeResource = ret.toString().substring(10,22);
                        String str = ret.toString().substring(10,ret.length()-2);
                        String resourceData = toStringHex(str);
                        String code = resourceData.substring(0,6);
                        String data = resourceData.substring(6,resourceData.length());
                        System.out.println("来自"+socket.getRemoteSocketAddress() + "的请求，服务编号:" + code+",请求参数："+data);
                        JSONObject jsonObject = JSONObject.parseObject(data);
                        Map<String,Object> requestMap = (Map<String,Object>)jsonObject;//    //json对象转Map
                        ObjectMapper objectMapper = new ObjectMapper();
                        AlipayParam alipayParam = objectMapper.convertValue(requestMap.get("body"), AlipayParam.class);
                        OutputStream os = socket.getOutputStream();
                        StringBuffer returnStr = new StringBuffer("68");
                        String acctOrgNo = alipayParam.getAcctOrgNo();
                        String chargeNo = null;
                        byte[] returnBytes = null;
                        ApplicationContext act = ApplicationContextRegister.getApplicationContext();
                        thirdChargeService = act.getBean(ThirdChargeService.class);
                        AlipayHead head = new AlipayHead();
                        AlipayBody alipayBody = new AlipayBody();
                        Map map = new HashMap();
                        map.put("head",head);
                        //调用接口
                        try{
                            switch (code){
                                //欠费查询接口
                                case "200001":
                                    chargeNo = alipayParam.getQueryValue();
                                    alipayBody = thirdChargeService.alipayChargeLoad(chargeNo, acctOrgNo).getData();
                                    break;
                                case "200002":
                                    String recDet = alipayParam.getRcvDet();
                                    String[] res = recDet.split( "\\|\\$");
                                    List<RcvblDet> rcvDetList = new ArrayList<>(10);
                                    if(res.length>0){
                                        for(String s:res){
                                            //chargeNo = res[0].split("\\|")[0];
                                            String[] str1 = s.split("\\|");
                                            if(str1.length==3){
                                                RcvblDet det = new RcvblDet(str1[0],str1[1],str1[2]);
                                                rcvDetList.add(det);
                                            }else{
                                                alipayBody.setRtnCode(AlipayRequestCodeEnum.MESSAGE_FORMAT_ERROR.getCode());
                                                alipayBody.setRtnMsg(AlipayRequestCodeEnum.MESSAGE_FORMAT_ERROR.getDesc());
                                            }
                                        }
                                        alipayParam.setRcvDetList(rcvDetList);
                                    }
                                    alipayBody = thirdChargeService.alipayChargeLoad(chargeNo, acctOrgNo).getData();
                                    break;
                                case "200011":
                                    break;
                                case "200012":
                                    break;
                                default:
                                    break;
                            }
                        }catch (Exception e){
                            log.error(e.getMessage());
                            alipayBody.setRtnCode(AlipayRequestCodeEnum.SYSTEM_EXCEPTION.getCode());
                            alipayBody.setRtnMsg(AlipayRequestCodeEnum.SYSTEM_EXCEPTION.getDesc());
                            System.out.println(e.getMessage());
                        }
                        map.put("body",alipayBody);
                        String aa = JSON.toJSONString(map);
                        System.out.println(aa);
                        String len = reverseHex(addZeroForNum(Integer.toHexString(aa.length()),8));
                        returnStr.append(len);
                        returnStr.append(codeResource);
                        returnStr.append(strToHexStr(aa));
                        System.out.println("请求服务号："+code+",客户缴费编码："+chargeNo+",清算单位："+acctOrgNo);
                        returnStr.append("16");
                        returnBytes = hexStringToByteArray(returnStr.toString());
                        ret.delete(0,ret.length());
                        os.write(returnBytes);
                        //os.close();
                    }
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                System.out.println("client is over");
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }

        }

    }

    public static void main(String[] args) {
        String a = "7B22626F6479223A7B227265636F7264436F756E74223A2230222C2272746E4D7367223A22E4BAA4E69893E68890E58A9F222C22746F74616C5263766564416D74223A2230222C226361706974616C4E6F223A22222C226F72674E6F223A223336323339222C22726376626C446574223A5B7B22726376626C50656E616C7479223A2230222C22657874656E64223A22222C226F72674E6F223A223336323339222C22636F6E734E616D65223A22222C226F72674E616D65223A22E995BFE6B299E4BE9BE6B0B4222C22745071223A2230222C22726376626C416D74223A2230222C22726376626C416D744964223A22222C226F7765416D74223A2230222C22636F6E734E6F223A22303030303134353634222C22726376626C596D223A22323031373035222C227263766564416D74223A2230222C22616363744F72674E6F223A223336323339227D5D2C22636F6E7354797065223A22222C22707265706179416D74223A223434222C2261646472223A22E7BAA2E69797E58CBA34E789873134E6A08B34E997A8222C226F72674E616D65223A22E995BFE6B299E4BE9BE6B0B4222C22746F74616C4F7765416D74223A2230222C22746F74616C50656E616C7479223A2230222C22636F6E734E6F223A22303030303134353634222C22746F74616C526376626C416D74223A2230222C2272746E436F6465223A2239393939222C22616363744F72674E6F223A223336323339227D2C2268656164223A7B22657874656E64223A22222C226D73674964223A22435359433230313732323232343435222C2273657276436F6465223A22323030303031222C2276657273696F6E223A22312E302E31222C226D736754696D65223A223230313730333038313031323232222C22736F75726365223A225754484E4348414E47534841222C2264657349666E6F223A224C4F4E475348494E45227D7D";
        String a1 = toStringHex(a);
        String b1 = strToHexStr(a1);
        System.out.println(a.equals(b1)?"16进制编码后相等":"16进制编码后不相等");
        System.out.println("16进制编码后："+b1+",再解码："+toStringHex(b1));
        /*ServerSocket server = null;
        try {
            server = new ServerSocket(8003);
            while (true) {
                System.out.println("listening...");

                Socket socket = server.accept();
                System.out.println("连接客户端地址：" + socket.getRemoteSocketAddress());
                System.out.println("connected...");
                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }*/
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * @param: [hex] 十六进制转字符串
     * @return: java.lang.String String串
     * @author: zhuoli
     * @date: 2018/6/4 12:48
     */
    @Deprecated
    public static String fromHex(String hex) {
        /*兼容带有\x的十六进制串*/
        hex = hex.replace("\\x","");
        char[] data = hex.toCharArray();
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("字符个数应该为偶数");
        }
        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f |= toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return new String(out);
    }

    /**
     * 字符串转化成为16进制字符串
     * @param str
     * @return
     */
    public static String strToHexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();//toCharArray() 方法将字符串转换为字符数组。
        StringBuilder sb = new StringBuilder(""); //StringBuilder是一个类，可以用来处理字符串,sb.append()字符串相加效率高
        byte[] bs = str.getBytes();//String的getBytes()方法是得到一个操作系统默认的编码格式的字节数组
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4; // 高4位, 与操作 1111 0000
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;  // 低四位, 与操作 0000 1111
            sb.append(chars[bit]);
            //sb.append(' ');//每个Byte之间空格分隔
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转字符串
     * @param s
     * @return
     */
    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    public static byte[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }

    /**
     * 十六进制字符串高位补0
     * @param str
     * @param strLength
     * @return
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }

        return str;
    }

    /**
     * 高低位转换后
     * @param hex
     * @return
     */
    private static String reverseHex(final String hex) {
        final char[] charArray = hex.toCharArray();
        final int length = charArray.length;
        final int times = length / 2;
        for (int c1i = 0; c1i < times; c1i += 2) {
            final int c2i = c1i + 1;
            final char c1 = charArray[c1i];
            final char c2 = charArray[c2i];
            final int c3i = length - c1i - 2;
            final int c4i = length - c1i - 1;
            charArray[c1i] = charArray[c3i];
            charArray[c2i] = charArray[c4i];
            charArray[c3i] = c1;
            charArray[c4i] = c2;
        }
        return new String(charArray);
    }

    /**
     * 将json转化为实体POJO
     * @param jsonStr
     * @param obj
     * @return
     */
    public static<T> Object JSONToObj(String jsonStr,Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr,
                    obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
