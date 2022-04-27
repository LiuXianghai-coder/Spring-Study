package org.xhliu.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.RoutePredicateFactory;
import org.springframework.cloud.gateway.support.NameUtils;

import java.util.Arrays;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * @author xhliu
 * @time 2022-03-07-下午10:25
 */
public class UtilTest {
    @Test
    public void test() {
        String text = "path=/gateway/**";
        int eqIdx = text.indexOf('=');
        String[] args = tokenizeToStringArray(text.substring(eqIdx + 1), ",");
        System.out.println(Arrays.toString(args));
        System.out.println(NameUtils.normalizeRoutePredicateName(PathRoutePredicateFactory.class));
//        System.out.println(NameUtils.normalizeRoutePredicateName(StripPrefixGatewayFilterFactory.class));
    }
}
