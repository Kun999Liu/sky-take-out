package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * @Author {liukun}
 * @e-mail:liukunjsj@163.com
 * @Date: 2026/01/18/ 11:13
 * @description
 */
public interface ReportService {

    /**
     * 营业额统计指定时间区间
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 用户统计指定时间区间
     * @param begin
     * @param end
     * @return
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * 订单统计指定时间区间
     * @param begin
     * @param end
     * @return
     */
    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);


    /**
     * 销量前十统计指定时间区间
     * @param begin
     * @param end
     * @return
     */
    SalesTop10ReportVO getSalesTop10Statistics(LocalDate begin, LocalDate end);

    /**
     *  导出运营数据报表
     * @param response
     */
    void exportBusinessData(HttpServletResponse response);
}
