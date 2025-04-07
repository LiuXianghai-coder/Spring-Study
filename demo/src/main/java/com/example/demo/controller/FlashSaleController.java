package com.example.demo.controller;

import com.example.demo.entity.GoodsStockInfo;
import com.example.demo.service.FlashSaleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *@author lxh
 */
@RestController
@RequestMapping(path = "/flashSale")
public class FlashSaleController
        implements CommandLineRunner {

    @Resource
    private FlashSaleService flashSaleService;

    @GetMapping(path = "/sale/{goodsId}")
    public Integer sale(@PathVariable(name = "goodsId") Long goodId) {
        return flashSaleService.flashSale(goodId);
    }

    @GetMapping(path = "/reload/{goodsId}")
    public Integer reload(@PathVariable(name = "goodsId") Long goodId) {
        return flashSaleService.loadStockInfo(goodId);
    }

    @Override
    public void run(String... args) throws Exception {
        flashSaleService.loadStockInfo(1L);
    }
}
