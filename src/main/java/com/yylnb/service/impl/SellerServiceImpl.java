package com.yylnb.service.impl;

import com.yylnb.entity.Commodity;
import com.yylnb.mapper.SellerMapper;
import com.yylnb.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RainLin
 * @date 2020/1/28 - 13:46
 */
@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerMapper sellerMapper;

    @Override
    public void insertCommodity(Commodity commodity) {
        sellerMapper.insertCommodity(commodity);
    }
}
