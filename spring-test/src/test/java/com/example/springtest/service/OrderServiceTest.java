package com.example.springtest.service;

import com.example.springtest.SpringTestApplication;
import com.example.springtest.entity.OrderInfo;
import com.example.springtest.mapper.OrderMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/*
    所有对于数据库操作在测试类中都不生效，使得能够多次执行测试用例
 */
@Rollback
/*
    在高版本的 Spring Test 中，已经集成了 Junit，因此可以跳过 @RunWith 和 @ContextConfiguration 的配置
 */
@SpringBootTest(classes = SpringTestApplication.class)
class OrderServiceTest {

    /*
        @Mock 注解表示将创建一个 GoodsService 的方法代理对象，对于具体的方法调用
        的返回结果可以参考 Mockito#when
     */
    @Mock
    GoodsService goodsService;

    @Resource
    /*
        表示这是一个需要注入 Mock 的对象，在 Mockito 初始化时会扫描
        这些注解并完成 @Mock 依赖的注入处理
     */
    @InjectMocks
    OrderService orderService;

    @Resource
    OrderMapper orderMapper;

    @BeforeEach
    public void initMock() {
        try {
            /*
                初始化 Mock 的测试环境，完成 Mock 相关的实例构造以及依赖注入
             */
            MockitoAnnotations.openMocks(this).close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createOrderSuccess() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(1);
        orderInfo.setGoodsId(2);
        orderInfo.setGoodsCnt(10);

        /*
            Mockito.when 会代理当前的方法调用，并返回当前假定的返回结果，
            当然也可以抛出异常等其它操作。这里我们假设每次对于库存服务的处理都是成功的，
            那么我们的订单应当是每次调用都会成功
         */
        Mockito.when(goodsService.updateGoodsStoreInfo(2, 10))
                .thenReturn(Boolean.TRUE);

        Mockito.when(goodsService.sendNotify(orderInfo.getUserId()))
                        .thenReturn(Boolean.TRUE);

        Assertions.assertTrue(orderService.createOrder(orderInfo));
        // 订单创建成功，数据库中应当存在订单信息
        Assertions.assertNotNull(orderMapper.queryById(orderInfo.getId()));

        // 验证预订的 GoodsService 方法调用已经发生
        Mockito.verify(goodsService, Mockito.atLeast(1))
                .updateGoodsStoreInfo(orderInfo.getGoodsId(), orderInfo.getGoodsCnt());


        // 异步接口调用的验证
        Mockito.verify(goodsService, Mockito.timeout(100))
                .sendNotify(orderInfo.getUserId());
    }

    @Test
    void createOrderFailed() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(2);
        orderInfo.setGoodsCnt(10);

        // 这里我们假设当前的库存更新是失败的，因此订单的创建应当也是失败的
        Mockito.when(goodsService.updateGoodsStoreInfo(2, 10))
                .thenReturn(Boolean.FALSE);

        Assertions.assertThrows(RuntimeException.class,
                () -> orderService.createOrder(orderInfo), "库存信息更新失败"
        );
        // 订单创建失败，数据库中应当不存在订单信息
        Assertions.assertNull(orderMapper.queryById(orderInfo.getId()));
    }

    @Test
    void goodsUpdateException() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(2);
        orderInfo.setGoodsCnt(10);

        // 这里我们假设当前的库存更新是失败的，因此订单的创建应当也是失败的
        Mockito.when(goodsService.updateGoodsStoreInfo(2, 10))
                        .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> orderService.createOrder(orderInfo)
        );
        Assertions.assertNull(orderMapper.queryById(orderInfo.getId()));
    }
}