package com.example.order.service.impl;

import com.example.order.data_transfer_object.OrderDTO;
import com.example.order.dataobject.OrderDetail;
import com.example.order.dataobject.OrderMaster;
import com.example.order.enums.OrderStatusEnum;
import com.example.order.enums.PayStatusEnum;
import com.example.order.repository.OrderDetailRepository;
import com.example.order.repository.OrderMasterRepository;
import com.example.order.service.OrderService;
import com.example.order.utils.KeyUtil;
import com.example.product.client.ProductClient;
import com.example.product.common.DecreaseStockInput;
import com.example.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfoOutput> productInfoOutputList = productClient.listForOrder(productIdList);


        BigDecimal orderAmount = new BigDecimal(0);
        for(OrderDetail orderDetail: orderDTO.getOrderDetailList()){
            for(ProductInfoOutput productInfoOutput : productInfoOutputList){
                if(productInfoOutput.getProductId().equals(orderDetail.getProductId())){
                    orderAmount = productInfoOutput.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfoOutput, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());

                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> {
                    DecreaseStockInput decreaseStockInput = new DecreaseStockInput();
                    decreaseStockInput.setProductId(e.getProductId());
                    decreaseStockInput.setProductQuantity(e.getProductQuantity());
                    return decreaseStockInput;
        }).collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);

        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());



        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
