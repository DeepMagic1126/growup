package com.ziyuan.transcation.mysqlxa;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.mysql.cj.jdbc.MysqlXid;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class XaDataSource {

    public static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws SQLException {

        MysqlXADataSource xaDataSource1 = new MysqlXADataSource();

        xaDataSource1.setUrl("jdbc:mysql://localhost:3306/product?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        xaDataSource1.setUser("root");
        xaDataSource1.setPassword("123456");

        MysqlXADataSource xaDataSource2 = new MysqlXADataSource();

        xaDataSource2.setUrl("jdbc:mysql://localhost:3306/product?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        xaDataSource2.setUser("root");
        xaDataSource2.setPassword("123456");



//        runWithoutTX(xaConnection1,xaConnection2);
        // gtrid：全局事务id bqual：分支限定符 formatId：记录gtrid、bqual的格式
        executor.execute(() -> {
            XAConnection xaConnection1 = null;
            try {
                xaConnection1 = xaDataSource1.getXAConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            XAResource xaResource = null;
            Xid xid = new MysqlXid("1".getBytes(), "1".getBytes(), 0);

            try {
                xaResource = xaConnection1.getXAResource();
                xaResource.start(xid, XAResource.TMNOFLAGS);


                Statement statement1 = xaConnection1.getConnection().createStatement();
                statement1.execute("INSERT INTO orders (uname,quantity) VALUES ('MEEK', 1)");

                Thread.sleep(2000);


                xaResource.end(xid, XAResource.TMSUCCESS);
                int prepare = xaResource.prepare(xid);
                System.out.println(prepare);
                xaResource.commit(xid, true);

            } catch (XAException e) {
                e.printStackTrace();
                try {
                    xaResource.rollback(xid);
                } catch (XAException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });


        executor.execute(() -> {
            XAConnection xaConnection = null;
            try {
                xaConnection = xaDataSource2.getXAConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            XAResource xaResource = null;
            Xid xid = new MysqlXid("1".getBytes(), "2".getBytes(), 0);

            try {
                xaResource = xaConnection.getXAResource();
                xaResource.start(xid, XAResource.TMNOFLAGS);

                Statement statement = xaConnection.getConnection().createStatement();
                statement.execute("UPDATE goods SET remain_quantity = remain_quantity -1 WHERE  id =1");

                Thread.sleep(2000);

                int i = 1/0;
                xaResource.end(xid, XAResource.TMSUCCESS);
                int prepare = xaResource.prepare(xid);
                System.out.println(prepare);
                xaResource.commit(xid, false);

            } catch (XAException e) {
                e.printStackTrace();
                try {
                    xaResource.rollback(xid);
                } catch (XAException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

//        executor.execute(() -> {
//            try {
//                XAResource xaResource = xaConnection2.getXAResource();
//                xaResource.start(xid, XAResource.TMNOFLAGS);
//                xaResource.setTransactionTimeout(2);
//                Statement statement2 = xaConnection2.getConnection().createStatement();
//                statement2.execute("UPDATE goods SET remain_quantity = remain_quantity -1 WHERE  id =1");
//
//                xaResource.end(xid, XAResource.TMSUCCESS);
//                xaResource.prepare(xid);
//
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (XAException e) {
//            }
//
//        });
    }


    public static void runWithoutTX(XAConnection xaConnection1, XAConnection xaConnection2) {
        executor.execute(() -> {
            try {
                xaConnection1.getConnection().setAutoCommit(false);
                Statement statement1 = xaConnection1.getConnection().createStatement();
                statement1.execute("INSERT INTO orders (uname,quantity) VALUES ('MEEK', 1)");
                statement1.getConnection().commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        executor.execute(() -> {
            try {
                xaConnection2.getConnection().setAutoCommit(false);
                Statement statement2 = xaConnection2.getConnection().createStatement();
                statement2.execute("UPDATE goods SET remain_quantity = remain_quantity -1 WHERE  id =1");
                int i = 1 / 0;
                statement2.getConnection().commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
