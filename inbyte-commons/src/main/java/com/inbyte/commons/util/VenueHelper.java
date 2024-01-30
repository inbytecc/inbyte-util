package com.inbyte.commons.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆信息获取计算类
 * 杭州湃橙体育科技有限公司
 * @author chenjw
 * @date 2016年12月13日
 */
public class VenueHelper {

    /**
     * 获取场地时间,  从当前时间算起
     * @param startHour
     * @param endHour
     * @param dateIndex
     * @return
     */
    public static List<Integer> getAvailableSiteHours(int startHour, int endHour, int dateIndex) {
        List<Integer> siteHours = new ArrayList();
//        int currentHour = DateUtil.getCurrentHour() + 1;
        int currentHour = 0 + 1;
        // 预定时间当天以后的时间全部显示
        if (dateIndex == 0) {
            startHour = startHour < currentHour ? currentHour : startHour;
        }
        // 时间超过24点的场合,分两段
        if (endHour < startHour) {
            for (int i = 0; i < endHour; i++) {
                siteHours.add(i);
            }
            for (int i = startHour; i <= 23; i++) {
                siteHours.add(i);
            }
        } else {
            for (int i = startHour; i < endHour; i++) {
                siteHours.add(i);
            }
        }
        return siteHours;
    }

    /**
     * 获取场地时间
     * @param startHour
     * @param endHour
     * @return
     */
    public static  List<Integer> getAllSiteHours(int startHour, int endHour) {
        List<Integer> siteHours = new ArrayList();
        // 时间超过24点的场合,分两段
        if (endHour < startHour) {
            for (int i = 0; i < endHour; i++) {
                siteHours.add(i);
            }
            for (int i = startHour; i <= 23; i++) {
                siteHours.add(i);
            }
        } else {
            for (int i = startHour; i < endHour; i++) {
                siteHours.add(i);
            }
        }
        return siteHours;
    }
}
