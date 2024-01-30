package com.inbyte.commons.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 四则运算工具
 * 杭州湃橙体育科技有限公司
 *
 * @author chenjw
 * @date 2016年06月30日
 */
public class ArithUtil {

    /**
     * 加法运算
     * 四舍五入，保留2位小数
     *
     * @param addend
     * @param summand
     * @return
     */
    public static BigDecimal add(BigDecimal addend, BigDecimal summand) {
        addend = addend == null ? BigDecimal.ZERO : addend;
        summand = summand == null ? BigDecimal.ZERO : summand;
        return addend.add(summand).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 减法运算
     * 四舍五入，保留2位小数
     *
     * @param addend
     * @param summand
     * @return
     */
    public static BigDecimal subtract(BigDecimal addend, BigDecimal summand) {
        addend = addend == null ? BigDecimal.ZERO : addend;
        summand = summand == null ? BigDecimal.ZERO : summand;
        return addend.subtract(summand).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 乘法运算
     * 四舍五入，保留2位小数
     *
     * @param addend
     * @param summand
     * @return
     */
    public static BigDecimal multiply(BigDecimal addend, BigDecimal summand) {
        addend = addend == null ? BigDecimal.ZERO : addend;
        summand = summand == null ? BigDecimal.ZERO : summand;
        return addend.multiply(summand).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 除法运算
     * 四舍五入，保留2位小数
     *
     * @param addend
     * @param summand
     * @return
     */
    public static BigDecimal divide(BigDecimal addend, BigDecimal summand) {
        addend = addend == null ? BigDecimal.ZERO : addend;
        summand = summand == null ? BigDecimal.ZERO : summand;
        return addend.divide(summand).setScale(2, RoundingMode.HALF_UP);
    }


    public static double add(Double addend, Double summand) {
        addend = addend == null ? 0 : addend;
        summand = summand == null ? 0 : summand;
        return BigDecimal.valueOf(addend).add(BigDecimal.valueOf(summand))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    public static double subtract(Double subtrahend, Double minuend) {
        subtrahend = subtrahend == null ? 0 : subtrahend;
        minuend = minuend == null ? 0 : minuend;
        return BigDecimal.valueOf(subtrahend).subtract(BigDecimal.valueOf(minuend))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }



    public static double multiply(Double multiplier, Integer multiplicand) {
        multiplier = multiplier == null ? 0 : multiplier;
        multiplicand = multiplicand == null ? 0 : multiplicand;
        return BigDecimal.valueOf(multiplier).multiply(BigDecimal.valueOf(multiplicand))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
    public static BigDecimal multiply(Integer multiplier, Integer multiplicand) {
        multiplier = multiplier == null ? 0 : multiplier;
        multiplicand = multiplicand == null ? 0 : multiplicand;
        return BigDecimal.valueOf(multiplier).multiply(BigDecimal.valueOf(multiplicand))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static double divide(Double divisor, Integer dividend) {
        divisor = divisor == null ? 0 : divisor;
        if (dividend == null || dividend == 0) {
            throw new IllegalArgumentException("divisor can not be null or zero");
        }
        return BigDecimal.valueOf(divisor)
                .divide(BigDecimal.valueOf(dividend),
                        2,
                        BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
    public static BigDecimal divide(Integer divisor, Integer dividend) {
        divisor = divisor == null ? 0 : divisor;
        if (dividend == null || dividend == 0) {
            throw new IllegalArgumentException("divisor can not be null or zero");
        }
        return BigDecimal.valueOf(divisor)
                .divide(BigDecimal.valueOf(dividend),
                        2,
                        BigDecimal.ROUND_HALF_UP);
    }
    public static BigDecimal divide(Long divisor, Integer dividend) {
        divisor = divisor == null ? 0 : divisor;
        if (dividend == null || dividend == 0) {
            throw new IllegalArgumentException("divisor can not be null or zero");
        }
        return BigDecimal.valueOf(divisor)
                .divide(BigDecimal.valueOf(dividend),
                        2,
                        BigDecimal.ROUND_HALF_UP);
    }
}
