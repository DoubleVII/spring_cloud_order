package com.example.order.controller;

import com.example.product.client.ProductClient;
import com.example.product.common.DecreaseStockInput;
import com.example.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        //调用微服务接口的方法
        //1.第一种方法
//        RestTemplate restTemplate = new RestTemplate();
//        String res = restTemplate.getForObject("http://localhost:8080/msg", String.class);
//        log.info("response={}", res);

        //2.第二种方法
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//        String url = String.format("http://%s:%s/msg", serviceInstance.getHost(), serviceInstance.getPort());
//        RestTemplate restTemplate = new RestTemplate();
//        String res = restTemplate.getForObject(url, String.class);
//        log.info("response={}", res);

        //3.第三种方法(RestTemplate config)
//        String res = restTemplate.getForObject("http://PRODUCT/msg", String.class);
//        log.info("response={}", res);
//
//        return res;
        return null;
    }

    @GetMapping("/getProductList")
    public String getProductList() {
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(Arrays.asList("123447872120"));
        log.info("response={}", productInfoList);
        return "OK";
    }

    @GetMapping("/decreaseStock")
    public String productDecreaseStock(){
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput();
        decreaseStockInput.setProductId("987247293123");
        decreaseStockInput.setProductQuantity(5);
        productClient.decreaseStock(Arrays.asList(decreaseStockInput));
        return "OK";
    }





}
