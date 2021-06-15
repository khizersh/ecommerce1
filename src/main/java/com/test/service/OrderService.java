package com.test.service;

import com.test.bean.User;
import com.test.bean.checkout.Checkout;
import com.test.bean.checkout.CheckoutDetail;
import com.test.bean.order.Order;
import com.test.bean.order.OrderResponse;
import com.test.repo.CheckoutRepo;
import com.test.repo.OrderRepo;
import com.test.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    private UserRepository userRepo;


    public List<OrderResponse> getOrderByUserId(Long id){
        User user = userRepo.getOne(id);

        List<Order> orderList = orderRepo.findByUserId(id);
        List<OrderResponse> responseList = new ArrayList<>();

        for (Order order : orderList) {
            OrderResponse rep = new OrderResponse();
            Checkout check = checkoutRepo.getOne(order.getCheckoutId());

            rep.setFullName(order.getFullName());
            rep.setEmail(order.getEmail());
            rep.setPhoneNo(order.getPhoneNo());
            rep.setCountry(order.getCountry());
            rep.setState(order.getState());
            rep.setCity(order.getCity());
            rep.setUserId(order.getUserId());
            rep.setAddressLine1(order.getAddressLine1());
            rep.setAddressLine2(order.getAddressLine2());
            rep.setPostalCode(order.getPostalCode());
            rep.setTotalAmount(check.getTotalAmount());
            rep.setCouponAmount(check.getCouponAmount());
            rep.setNetAmount(check.getNetAmount());
            responseList.add(rep);

        }
            return responseList;
    }
    public List<CheckoutDetail>  getOrderDetailByCheckoutId(int id){
        Checkout check = checkoutRepo.getOne(id);

        List<CheckoutDetail> list = new ArrayList<>();

        list = check.getProductList();

        return list;


    }
}
