package org.xhliu.springflowable.socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.cj.jdbc.Driver;

/**
 * @author xhliu
 * @time 2022-02-23-下午9:25
 */
public class ConnectMySQL {
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "12345678";
    private final static String URL =
            "jdbc:mysql://127.0.0.1:3306/customer";

    {
        System.out.println("simple code block");
    }

    public void connect() throws SQLException, ClassNotFoundException {
//        Driver driver = new Driver();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        loader.loadClass("com.mysql.cj.jdbc.Driver");

        Properties properties = new Properties();
        properties.put("user", USER_NAME);
        properties.put("password", PASSWORD);

//        Connection connect = driver.connect(URL, properties);
        Connection connect = DriverManager.getConnection(URL, properties);
        System.out.println(connect.getClientInfo());
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectMySQL conn = new ConnectMySQL();
        conn.connect();

        String str = "北京市(朝阳区)(西城区)(海淀区)";
        Pattern pattern = Pattern.compile("([^(]+)\\(.*");
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
