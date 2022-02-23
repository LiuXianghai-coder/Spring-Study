package org.xhliu.springflowable.socket;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author xhliu
 * @time 2022-02-23-下午8:42
 */
public class EchoServer {
    private final static Logger log = LoggerFactory.getLogger(EchoServer.class);

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    private void server() throws IOException {
        ServerSocket socket = new ServerSocket();
        Executor executor = Executors.newCachedThreadPool(new ThreadFactoryBuilder().build());
        socket.bind(new InetSocketAddress(port));

        while (true) {
            Socket accept = socket.accept();

            executor.execute(() -> {
                try (
                        InputStream in = accept.getInputStream();
                        OutputStream out = accept.getOutputStream();
                ) {
                    StringBuilder sb = new StringBuilder();
                    int len;
                    byte[] buffer = new byte[4 * 1024];
                    while ((len = in.read(buffer)) > 0) {
                        sb.append(new String(buffer, 0, len));
                    }

                    log.info("get from: " + accept.getInetAddress().getHostAddress() + "\t content=" + sb);
                    out.write(sb.toString().getBytes(UTF_8));
                    out.flush();
                    accept.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer(5000);
        server.server();
    }
}
