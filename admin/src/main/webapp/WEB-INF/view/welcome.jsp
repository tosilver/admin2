<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>欢迎页面</title>
    <%@ include file="include/head.jsp" %>

    <script type="text/javascript" src="${ctxAssets}/3rd-lib/echarts/echarts.min.js"></script>
</head>
<body>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding am-padding-bottom-0">
            <div class="am-fl am-cf">
                <strong class="am-text-primary am-text-lg">首页</strong> /
                <small>欢迎页</small>
            </div>
        </div>
        <hr>
        <div style="width:1050px;height: 860px;clear: both;">
            <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
            <div id="main" style="width:1020px;height:420px;padding: 8px 15px;float:left;"></div>
            <div id="main2" style="width:1020px;height:420px;padding: 8px 15px;float:left;"/>
            <script type="text/javascript">
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                option = {
                    title: {
                        text: '交易情况'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c}'
                    },
                    legend: {
                        data: ['交易金额']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis:
                        {
                            type: 'category',
                            boundaryGap: false,
                            splitLine: {show: false},
                            data: ['${map.days[6]}(交易${map.tradeCounts[6]}笔)',
                                '${map.days[5]}(交易${map.tradeCounts[5]}笔)',
                                '${map.days[4]}(交易${map.tradeCounts[4]}笔)',
                                '${map.days[3]}(交易${map.tradeCounts[3]}笔)',
                                '${map.days[2]}(交易${map.tradeCounts[2]}笔)',
                                '${map.days[1]}(交易${map.tradeCounts[1]}笔)',
                                '${map.days[0]}(交易${map.tradeCounts[0]}笔)']
                        },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '交易金额',
                            type: 'line',
                            stack: '交易金额',
                            label: {
                                normal: {
                                    show: true
                                }
                            },
                            data: [${empty map.tradeMoneys[6]?0:map.tradeMoneys[6]},
                                ${empty map.tradeMoneys[5]?0:map.tradeMoneys[5]},
                                ${empty map.tradeMoneys[4]?0:map.tradeMoneys[4]},
                                ${empty map.tradeMoneys[3]?0:map.tradeMoneys[3]},
                                ${empty map.tradeMoneys[2]?0:map.tradeMoneys[2]},
                                ${empty map.tradeMoneys[1]?0:map.tradeMoneys[1]},
                                ${empty map.tradeMoneys[0]?0:map.tradeMoneys[0]}]
                        },
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);


                // 基于准备好的dom，初始化echarts实例
                var myChart2 = echarts.init(document.getElementById('main2'));

                option2 = {
                    title: {
                        text: '商户新增趋势'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c}'
                    },
                    legend: {
                        data: ['商户新增']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis:
                        {
                            type: 'category',
                            boundaryGap: false,
                            splitLine: {show: false},
                            data: ['${map.days[6]}',
                                '${map.days[5]}',
                                '${map.days[4]}',
                                '${map.days[3]}',
                                '${map.days[2]}',
                                '${map.days[1]}',
                                '${map.days[0]}']
                        },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '商户新增',
                            type: 'line',
                            stack: '商户新增',
                            label: {
                                normal: {
                                    show: true
                                }
                            },
                            data: [${map.merchantIncrease[6]},
                                ${map.merchantIncrease[5]},
                                ${map.merchantIncrease[4]},
                                ${map.merchantIncrease[3]},
                                ${map.merchantIncrease[2]},
                                ${map.merchantIncrease[1]},
                                ${map.merchantIncrease[0]}]
                        },
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart2.setOption(option2);

            </script>
        </div>
    </div>
    <%@ include file="include/footer.jsp" %>
</div>
</body>
</html>