package com.impl;

import com.entitty.User;
import com.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private TransactionTemplate transactionTemplate;
    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveUser(User user) {
        jdbcTemplate.execute("insert into user (userName,passWord,realName) " +
                "values ('"+user.getUserName()+"','"+user.getPassWord()+"','"+user.getUserName()+"')");
    }

    @Override
    public User getById(Integer id) {
        String sql = "select id,userName,passWord,realName,amount from user where id= "+id ;
        List<User> query = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                   User user = new User();
                   user.setId(rs.getInt(1));
                   user.setUserName(rs.getString(2));
                   user.setPassWord(rs.getString(3));
                   user.setRealName(rs.getString(4));
                   user.setAmount(rs.getBigDecimal(5));
                return user;
            }
        });
        if(query.size()>0) return query.get(0);
        return  null;

    }

    @Override
    public void updateUserAmount(User user) {
        jdbcTemplate.execute("update user  set amount = " +user.getAmount() + " where  id = " + user.getId());
    }

    @Override
    public void transfer(Integer from, Integer to, BigDecimal amount) {

        TransactionSynchronizationManager.initSynchronization();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            connection.setAutoCommit(false);
            User pay = this.getById(from);
            User rec = this.getById(to);
            pay.setAmount( pay.getAmount().subtract(amount).setScale(2));
            rec.setAmount(rec.getAmount().add(amount).setScale(2));
            updateUserAmount(pay);

            int i = 1 / 0 ;
            updateUserAmount(rec);
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }finally {

//       ?????? ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
        }



    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * ???????????????????????????
     * @param from
     * @param to
     * @param amount
     */
    @Override
    public void transfer2(Integer from, Integer to, BigDecimal amount) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
       try{
           User pay = this.getById(from);
           User rec = this.getById(to);
           pay.setAmount( pay.getAmount().subtract(amount).setScale(2));
           rec.setAmount(rec.getAmount().add(amount).setScale(2));
           updateUserAmount(pay);

           //int i = 1 / 0 ;
           updateUserAmount(rec);
           transactionManager.commit(transaction);
       }catch (Exception e){
           transactionManager.rollback(transaction);
       }
    }

    /**
     * ????????????????????????????????????
     * ?????????????????????????????????????????????????????????????????????
     * @param from
     * @param to
     * @param amount
     */
    @Override
    public void transfer3(Integer from, Integer to, BigDecimal amount) {
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                User pay = getById(from);
                User rec = getById(to);
                pay.setAmount( pay.getAmount().subtract(amount).setScale(2));
                rec.setAmount(rec.getAmount().add(amount).setScale(2));
                updateUserAmount(pay);

                int i = 1 / 0 ;
                updateUserAmount(rec);
                return null;
            }
        });
    }
}

