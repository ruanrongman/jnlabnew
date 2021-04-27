package utility.database;


//import java.sql.*;

import java.sql.*;

public class Mysql {
        public Mysql(){

        }
        public void text() throws ClassNotFoundException, SQLException {
            //1.注册数据库的驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取数据库连接（里面内容依次是："jdbc:mysql://主机名:端口号/数据库名","用户名","登录密码"）
            Connection connection = DriverManager.getConnection("jdbc:mysql://123.56.247.19:3306/labservice?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai","labservice","3SRRitSTaYdRWCna");
            //3.需要执行的sql语句（?是占位符，代表一个参数）
            String sql = "insert into stu(id,name,age) values(?,?,?)";
            //4.获取预处理对象，并依次给参数赋值
            PreparedStatement statement = connection.prepareCall(sql);
            statement.setInt(1,12); //数据库字段类型是int，就是setInt；1代表第一个参数
            statement.setString(2,"小明");    //数据库字段类型是String，就是setString；2代表第二个参数
            statement.setInt(3,16); //数据库字段类型是int，就是setInt；3代表第三个参数
            //5.执行sql语句（执行了几条记录，就返回几）
            int i = statement.executeUpdate();
            System.out.println(i);
            //6.关闭jdbc连接
            statement.close();
            connection.close();
        }
        public void text2() {
            Statement stmt = null;
            Connection connection = null;
            try{
            //1.注册数据库的驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取数据库连接（里面内容依次是："jdbc:mysql://主机名:端口号/数据库名","用户名","登录密码"）
             connection = DriverManager.getConnection("jdbc:mysql://123.56.247.19:3306/labservice?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai","labservice","3SRRitSTaYdRWCna");
            //3.需要执行的sql语句（?是占位符，代表一个参数）
            String sql = "SELECT * FROM `stu`";
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("age");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(",name: " + name);
                System.out.print(",age: " + url);
                System.out.print("\n");
            }
                // 完成后关闭
                rs.close();
                stmt.close();
                connection.close();
            }catch(SQLException se){
                // 处理 JDBC 错误
                se.printStackTrace();
            }catch(Exception e){
                // 处理 Class.forName 错误
                e.printStackTrace();
            }finally{
                // 关闭资源
                try{
                    if(stmt!=null) stmt.close();
                }catch(SQLException se2){
                }// 什么都不做
                try{
                    if(connection!=null) connection.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }
            }

        }
    }

