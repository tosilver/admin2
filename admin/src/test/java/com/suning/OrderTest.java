package com.suning;


import co.b4pay.admin.dao.OrderDao;
import co.b4pay.admin.dao.TUserDao;
import co.b4pay.admin.entity.DetailDataCard;
import co.b4pay.admin.entity.Order;
import co.b4pay.admin.entity.TUser;
import co.b4pay.admin.entity.base.Page;
import co.b4pay.admin.entity.base.Params;
import co.b4pay.admin.service.OrderService;
import co.b4pay.admin.service.TUserService;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.tags.Param;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/spring-*.xml")
public class OrderTest {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TUserDao tUserDao;

    @Autowired
    private TUserService tUserService;

    @Test
    public void test() {
        /*Order order = new Order();
        order.setOrderId("1");
        Params params = Params.create(order);
        List<Order> orders = orderDao.findList(params);
        System.out.println(orders);*//*
        TUser user = new TUser();
        user.setAccount("18672679757");
        Params params = Params.create(user);
        List<TUser> list = tUserDao.findList(params);
        //System.out.println(list);

        List<TUser> list1 = tUserService.findList();
        System.out.println(list1);*/
        /*Page<Order> page = new Page<>();
        Order order = new Order();
        order.setOrderId("1");
        Params params = Params.create(order);
        page.setParams(params);
        Page<Order> orderPage = orderService.findPage(page);
        System.out.println(orderPage.getParams());*/

        List<TUser> list = tUserDao.findList();
        System.out.println(list);
    }
}
