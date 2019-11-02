package com.example.order.service;

import com.example.order.data_transfer_object.OrderDTO;

public interface OrderService {

    OrderDTO create(OrderDTO orderDTO);
}
