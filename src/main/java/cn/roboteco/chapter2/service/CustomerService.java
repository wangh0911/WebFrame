package cn.roboteco.chapter2.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.roboteco.chapter2.helper.DatabaseHelper;
import cn.roboteco.chapter2.model.Customer;
import cn.roboteco.chapter2.utils.PropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomerService {
    private static  final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    public List<Customer> getCustomerList(){
        Connection connection = DatabaseHelper.getConnection();
        try {
            String sql = "select * from customer";
            return DatabaseHelper.queryEntityList(Customer.class, connection,sql);
        }finally {
            DatabaseHelper.closeConnection(connection);
        }
    }

//    public List<Customer> getCustomerList(){
//        Connection connection  =null;
//        try {
//            List<Customer> customerList = new ArrayList<>();
//            String sql = "select * from customer";
//            connection = DatabaseHelper.getConnection();
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet rs  = statement.executeQuery();
//            while (rs.next()){
//                Customer customer = new Customer();
//                customer.setId(rs.getLong("id"));
//                customer.setName(rs.getString("name"));
//                customer.setContact(rs.getString("contact"));
//                customer.setTelephone(rs.getString("email"));
//                customer.setEmail(rs.getString("email"));
//                customer.setTelephone(rs.getString("telephone"));
//                customerList.add(customer);
//            }
//            return customerList;
//        }catch (Exception e){
//            logger.error(ExceptionUtil.stacktraceToString(e));
//            return null;
//        }finally {
//            DatabaseHelper.closeConnection(connection);
//        }
//    }

    public List<Customer> getCustomerList(String keyword){
        return null;
    }
    public Customer getCustomer(Long id){
        return null;
    }

    public boolean createCustomer(Map<String,Object> fieldMap){
        return false;
    }
    public boolean updateCustomer(Long id, Map<String,Object> fieldMap){
        return false;
    }
    public boolean deleteCustomer(Long id){
        return false;
    }
}
