package com.gochanghai.messagecenter.service;

import com.gochanghai.messagecenter.dto.OrderParam;

public interface OmsPortalOrderService {

    void cancelOrder(Long orderId);

    Object generateOrder(OrderParam orderParam);
}
