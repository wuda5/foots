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

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;
import java.util.*;

@Slf4j
public class SSLServer {
    private static final int DEFAULT_PORT = 1224;

    private static final String SERVER_KEY_STORE_PASSWORD = "longshine";
    private static final String SERVER_TRUST_KEY_STORE_PASSWORD = "longshine";

    private SSLServerSocket serverSocket;
    private ThirdChargeService thirdChargeService;
    /**
     * 启动程序
     *
     * @param args
     */
    public static void main(String[] args) {
        SSLServer server = new SSLServer();
        server.createSSLServerSocket();
        server.start();
    }

    /**
     * <ul>
     * <li>听SSL Server Socket</li>
     * <li> 由于该程序不是演示Socket监听，所以简单采用单线程形式，并且仅仅接受客户端的消息，并且返回客户端指定消息</li>
     * </ul>
     */
    public void start() {
        if (serverSocket == null) {
            System.out.println("ERROR");
            return;
        }
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                InputStream input = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(input);

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
                                    alipayBody = thirdChargeService.alipayCharge(alipayParam).getData();
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
                            if("200001".equals(code)){
                                alipayBody.setRtnCode(AlipayRequestCodeEnum.UNABLE_TO_PAY.getCode());
                                alipayBody.setRtnMsg(AlipayRequestCodeEnum.UNABLE_TO_PAY.getDesc());
                            }else{
                                alipayBody.setRtnCode(AlipayRequestCodeEnum.ABNORMAL_BUSINESS_STATUS.getCode());
                                alipayBody.setRtnMsg(AlipayRequestCodeEnum.ABNORMAL_BUSINESS_STATUS.getDesc());
                            }
                            System.out.println(e.getMessage());
                        }
                        map.put("body",alipayBody);
                        String aa = JSON.toJSONString(map);
                        System.out.println(aa);
                        String len = reverseHex(addZeroForNum(Integer.toHexString(aa.getBytes("UTF-8").length),8));
                        returnStr.append(len);
                        returnStr.append(codeResource);
                        returnStr.append(strToHexStr(aa));
                        System.out.println("请求服务号："+code+",客户缴费编码："+chargeNo+",清算单位："+acctOrgNo);
                        returnStr.append("16");
                        returnBytes = hexStringToByteArray(returnStr.toString());
                        ret.delete(0,ret.length());
                        os.write(returnBytes);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }finally {
                System.out.println("serviceClient is close");
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException x) {
                        x.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * <ul>
     * <li>ssl连接的重点:</li>
     * <li>初始化SSLServerSocket</li>
     * <li>导入服务端私钥KeyStore，导入服务端受信任的KeyStore(客户端的证书)</li>
     * </ul>
     */
    public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream("alipay/kserver.keystore"), SERVER_KEY_STORE_PASSWORD.toCharArray());
            tks.load(new FileInputStream("alipay/kserver.keystore"), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());

            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(DEFAULT_PORT);
            serverSocket.setNeedClientAuth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createSSLServerSocket(){
        try {
            int port = 1224;  //要监听的端口
            String serverPrivateKey = "alipay/kserver.keystore"; //服务端自身私钥
            String serverKeyPassword = "longshine";  //私钥密码
            String trustKey= "alipay/tserver.keystore"; //信任的证书列表,即客户端证书
            String trusttKeyPassword= "longshine";       //信任的证书列表访问密码

            SSLContext ctx = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            /*ks.load(new FileInputStream("G:\\alipay\\kserver.keystore"), serverKeyPassword.toCharArray());
            tks.load(new FileInputStream("G:\\alipay\\tserver.keystore"), trusttKeyPassword.toCharArray());*/
            ks.load(getClass().getClassLoader().getResourceAsStream(serverPrivateKey), serverKeyPassword.toCharArray());
            tks.load(getClass().getClassLoader().getResourceAsStream(trustKey), trusttKeyPassword.toCharArray());

            kmf.init(ks, serverKeyPassword.toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            serverSocket =  (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(port);
            serverSocket.setNeedClientAuth(true);
            //return serverSocket;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return null;
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
}
