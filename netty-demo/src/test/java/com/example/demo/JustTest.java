package com.example.demo;

import com.example.demo.entity.ImgHref;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author xhliu2
 * @create 2021-08-19 14:05
 **/
public class JustTest {
    private final static String url = "jdbc:postgresql://39.99.129.90:5432/lxh_db";
    private final static String userName = "postgres";
    private final static String password = "17358870357yi";

    private final static String path = "D:/data/img/";

    private final static String chapter = "chapter-";

    private final static int bufferSizeMB = 10;

    private final static int bufferSize = bufferSizeMB * 1024 * 1024;

    private final static String USER_AGENT = "User-Agent";

    private final static String USER_AGENT_VAL = "User-Agent";

    private final static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10809));

    private final static String getAllSQL = "SELECT * FROM manhua_img LIMIT 1";

    static  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getData() throws Throwable {
        try (
                Connection connection = DriverManager.getConnection(url, userName, password);
                PreparedStatement preparedStatement = connection.prepareStatement(getAllSQL);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                ImgHref imgHref = new ImgHref();
                imgHref.setImgId(resultSet.getLong("img_id"));
                imgHref.setChapterId(resultSet.getInt("chapter_id"));
                imgHref.setChapterImgId(resultSet.getInt("chapter_img_id"));
                imgHref.setImgHref(resultSet.getString("img_href"));

                getImg(imgHref);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getImg(ImgHref imgHref) throws Throwable {
        File file = new File(path + chapter + imgHref.getChapterId());
        if (!file.exists()) {
            if (file.mkdirs())
                System.out.println("mkdir success");
        }

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bufferSize);
        String fileName = file.getPath() + "/" + imgHref.getChapterImgId() + ".png";

        file = new File(fileName);
        if (!file.exists()) {
            if (file.createNewFile())
                System.out.println("create file success.");;
        }

        HttpURLConnection connection = (HttpURLConnection) new URL(imgHref.getImgHref()).openConnection(proxy);
        connection.setRequestProperty(USER_AGENT, USER_AGENT_VAL);
        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(false);
        try (
                ReadableByteChannel in = Channels.newChannel(connection.getInputStream());
                FileChannel out = new FileOutputStream(fileName).getChannel();
        ) {
            out.transferFrom(in, byteBuffer.position(), byteBuffer.limit());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Throwable {
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10809));
//
//        URL url = new URL("https://cda11.mangadna.com/online/264/94/1--813.jpg");
//
//        String puny = IDN.toASCII(url.getHost());
//        url = new URL(url.getProtocol(), puny, url.getPort(), url.getFile());
//
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
//        connection.setRequestMethod("GET");
//        connection.setInstanceFollowRedirects(false);
//        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
//                " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
//
//        connection.connect();
//        FileOutputStream outputStream = new FileOutputStream("a.png");
//        InputStream inputStream = connection.getInputStream();
//        int val;
//        byte[] buffer = new byte[1024*4];
//        while ((val = inputStream.read(buffer)) > 0)
//            outputStream.write(buffer, 0, val);
//
//        System.out.println(connection.getResponseMessage());
//        System.out.println(connection.getContentLength());

        JustTest justTest = new JustTest();

        List[] lists = new List[9];
        justTest.getData();
    }
}
