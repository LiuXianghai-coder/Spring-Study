package com.example.demo;

import com.example.demo.entity.ImgHref;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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

    int mul(int y, int z) {
        int res = 0;
        for (int i = 0; i < 32; ++i) {
            if ((z & 1) > 0) res += y;
            if (res > 0) return 1;

            y <<= 1;
            z >>= 1;
        }

        return res;
    }

    public static void main(String[] args) {
        int a = 1234567;
        byte[] arr = new byte[4];
        for (int i = 0; i < 32; i += 8) {
            arr[i / 8] = (byte) ((a >> i));
        }

        System.out.println(ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN).getInt());
        System.out.println(ByteBuffer.wrap(arr).order());
        System.out.println(ByteBuffer.wrap(arr).getInt());
    }
}
