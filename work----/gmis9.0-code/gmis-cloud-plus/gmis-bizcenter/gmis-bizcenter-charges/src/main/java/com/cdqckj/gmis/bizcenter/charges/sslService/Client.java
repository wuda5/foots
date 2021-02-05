package com.cdqckj.gmis.bizcenter.charges.sslService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        //200001
        String hex = "68220100003230303030317b22626f6479223a7b22657874656e64223a22222c226275736954797065223a22222c22656e64596d223a22323031373037222c2262676e596d223a22323031373037222c22717565727956616c7565223a223230323031323236222c22616363744f72674e6f223a22313233343536222c22717565727954797065223a2230227d2c2268656164223a7b22657874656e64223a22222c226d73674964223a22435343587875616e6368656e67307373303034222c2273657276436f6465223a22323030303031222c22736f75726365223a22414c49504159222c2276657273696f6e223a22312e302e31222c2264657349666e6f223a22575441485855414e4348454e47222c226d736754696d65223a223230313730373035313030303039227d7d16";
        //String hex = "68220100003230303030327b22626f6479223a7b22636861726765436e74223a2233222c2262616e6b4461746554696d65223a22313132333030222c22657874656e64223a22222c2262616e6b53657269616c223a22323031373035303334343030303037222c227061794d6f6465223a22222c2262616e6b44617465223a223230313730353033222c22616363744f72674e6f223a22313233343536222c22726376446574223a22313534323038303730387c3535373534373537327c3230313730337c7c24313534323038303730387c3535373534373537337c3230313730337c7c24313534323038303730387c3535373534373537347c3230313730337c7c24222c22726376416d74223a2238373231222c226361706974616c4e6f223a22227d2c2268656164223a7b22657874656e64223a22222c226d73674964223a22435843537368656e7a68656e30307330303232222c2273657276436f6465223a22323030303032222c22736f75726365223a22414c49504159222c2276657273696f6e223a22312e302e31222c2264657349666e6f223a22575447445348454e5a48454e222c226d736754696d65223a223230313730353033313131323133227d7d16";
        //System.out.println("原字符串:" + s);
        System.out.println("十六进制字符串:" + hex);
        String head = hex.substring(0,2);
        String length = hex.substring(2,10);
        String code = hex.substring(10,22);
        String data = hex.substring(22,hex.length()-2);
        String end = hex.substring(hex.length()-2,hex.length());
        Integer leng = "{\"body\":{\"extend\":\"\",\"busiType\":\"\",\"endYm\":\"201707\",\"bgnYm\":\"201707\",\"queryValue\":\"10125334\",\"acctOrgNo\":\"36745\",\"queryType\":\"0\"},\"head\":{\"extend\":\"\",\"msgId\":\"CSCXxuancheng0ss004\",\"servCode\":\"200001\",\"source\":\"ALIPAY\",\"version\":\"1.0.1\",\"desIfno\":\"WTAHXUANCHENG\",\"msgTime\":\"20170705100009\"}}".length();
        String len = reverseHex(addZeroForNum(Integer.toHexString(leng),8));
        String decode = fromHex(data);
        System.out.println("解码后头:" + fromHex(head));
        System.out.println("解码后数据长度:" + fromHex(length));
        System.out.println("解码后接口编码:" + fromHex(code));
        System.out.println("解码后结尾:" + fromHex(end));
        System.out.println("解码后数据域:" + decode);

        Socket socket = null;
        try {
            System.out.println("connecting...");
            socket = new Socket("127.0.0.1", 8003);
            System.out.println("connection success");

            // 输入任意字符发送，输入q退出
            Scanner in = new Scanner(System.in);
            String str = hex;//"01 10 00 00 00 02 04 00 01 00 00 a2 6f"; //发送的16进制字符串
            byte[] bytes = hexStringToByteArray(str);
            OutputStream os = socket.getOutputStream();
            while (!(in.nextLine()).equals("q")) { //输入q退出
                os.write(bytes);
            }
            os.flush();
            //os.close();

            //输入流
            InputStream is=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            socket.shutdownOutput();
            //接收服务器的相应
            String replyInfo=null;
            while(!((replyInfo=br.readLine())==null)){
                System.out.println("接收服务器的数据信息："+replyInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {

                }
            }
        }
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
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
     * @param: [hex] 十六进制字符串
     * @return: java.lang.String String串
     * @author: zhuoli
     * @date: 2018/6/4 12:48
     */
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

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

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

}
