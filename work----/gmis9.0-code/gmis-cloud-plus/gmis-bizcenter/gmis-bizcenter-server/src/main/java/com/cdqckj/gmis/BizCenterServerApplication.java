package com.cdqckj.gmis;
import com.alibaba.cloud.seata.feign.SeataFeignClientAutoConfiguration;
import com.cdqckj.gmis.bizcenter.charges.sslService.SSLServer;
import com.cdqckj.gmis.bizcenter.charges.sslService.Server;
import com.cdqckj.gmis.security.annotation.EnableLoginArgResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 启动类
 *
 * @author gmis
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SeataFeignClientAutoConfiguration.class})
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients(value = {
        "com.cdqckj.gmis",
})
@Slf4j
@EnableLoginArgResolver
@ServletComponentScan  //注册过滤器注解
public class BizCenterServerApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(BizCenterServerApplication.class, args);
        Environment env = application.getEnvironment();

        //SSL双认证服务端
        SSLServer server = new SSLServer();
        server.createSSLServerSocket();
        server.start();
        //普通Socket服务
        /*ServerSocket server = null;
        try {
            server = new ServerSocket(8003);
            while (true) {
                System.out.println("listening...");

                Socket socket = server.accept();
                System.out.println("连接客户端地址：" + socket.getRemoteSocketAddress());
                System.out.println("connected...");
                Server.ClientHandler handler = new Server.ClientHandler(socket);
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

        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! 访问连接:\n\t" +
                        "Swagger文档: \t\thttp://{}:{}/doc.html\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }
}
