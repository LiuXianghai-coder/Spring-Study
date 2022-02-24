package org.xhliu.springflowable.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author xhliu
 * @time 2022-02-23-下午8:58
 */
public class EchoClient {
    private final static Logger log = LoggerFactory.getLogger(EchoClient.class);

    private final String content;

    public EchoClient(String content) {
        this.content = content;
    }

    public void access(String host, int port){
        try (
                Socket client = new Socket(host, port);
                OutputStream out = client.getOutputStream();
                InputStream in = client.getInputStream();
        ) {
            out.write(this.content.getBytes(UTF_8));
            out.flush();
            client.shutdownOutput();

            StringBuilder sb = new StringBuilder();
            int len;
            byte[] buffer = new byte[4 * 1024];
            while ((len = in.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, len));
            }

            log.info("client get response={}", sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EchoClient client = new EchoClient("just some content");
        client.access("127.0.0.1", 5000);
    }
}
