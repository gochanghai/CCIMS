package com.gochanghai.messagecenter.controller;

import com.gochanghai.messagecenter.dto.OrderParam;
import com.gochanghai.messagecenter.service.OmsPortalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OmsPortalOrderController {

    @Autowired
    private OmsPortalOrderService portalOrderService;

    @PostMapping(value = "/generateOrder")
    @ResponseBody
    public Object generateOrder(@RequestBody OrderParam orderParam) {
        return portalOrderService.generateOrder(orderParam);
    }
}
