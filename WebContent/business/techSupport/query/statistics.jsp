<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script type="text/javascript">
    var statisticsWidth = 800;
    var regionDiv = 'byRegion__Div';
    var regionTableId = 'byRegion__table';
    var regionTable;
    var regionURL = BUSNEISS_PATH + '/querylistStatisticsByRegion_statistics.action';
    var departmentDiv = 'byDepartment__Div';
    var departmentTableId = 'byDepartment__table';
    var departmentTable;
    var departmentURL = BUSNEISS_PATH + '/querylistStatisticsByDepartment_statistics.action';
    var supportLeaderDiv = 'bySupportLeader__Div';
    var supportLeaderTableId = 'bySupportLeader__table';
    var supportLeaderTable;
    var supportLeaderURL = BUSNEISS_PATH + '/querylistStatisticsBySupportLeader_statistics.action';
    var sessionStatisticsDataURL = BUSNEISS_PATH + '/querySessionStatisticsData_statistics.action';
    //statisticsTypeAction:key = 1 表示按区域统计,key = 2表示按部门统计, key = 3 表示按负责人统计
    var statisticsTypeAction = {'1': {container: 'byRegion', queryMethod: StatisticsByRegionQuery, chartTitle: '按区域统计'},
        '2': {container: 'byDepartment', queryMethod: StatisticsByDepartmentQuery, chartTitle: '按部门统计'},
        '3': {container: 'bySupportLeader', queryMethod: StatisticsBySuppportLeaderQuery, chartTile: '按负责人统计'}};

    $(function () {
        $('#byRegion').hide();
        $('#byDepartment').hide();
        $('#bySupportLeader').hide();

        initStatisticsByRegionQuery(regionDiv);
        initStatisticsByDepartmentQuery(departmentDiv);
        initStatisticsBySuppportLeaderQuery(supportLeaderDiv);

        $('.dataresult').hide();
        $('input:radio[name="displayType"]').each(function () {
            var _radio = $(this);
            _radio.click(function () {
                $('.dataresult').hide();
                $('.' + _radio.attr('id')).show();
            });
        });
        $('#' + statisticsTypeAction[$('#statisticsType2').val()]['container']).show();
        statisticsTypeAction[$('#statisticsType2').val()]['queryMethod'](1);
        $('#' + statisticsTypeAction[$('#statisticsType2').val()]['container'] + ' input:radio').eq(0).click();
    });
    function buildChart(container, tabledata,flag) {
        var chartContainers = container.find('.chart');
            chartContainers.eq(0).fusionChart({
                prefix: 'ddd',
                type: 'pie',
                columns: [1],
                data: tabledata,
                title: statisticsTypeAction[$('#statisticsType2').val()]['chartTitle'],
                width: statisticsWidth / 2 - 10,
                height: pageHeight - 270,
                isTotal: true
            });
            chartContainers.eq(1).fusionChart({
                prefix: 'ddd',
                type: 'bar',
                columns: [1],
                data: tabledata,
                title: statisticsTypeAction[$('#statisticsType2').val()]['chartTitle'],
                width: statisticsWidth / 2 - 10,
                height: pageHeight - 270,
                isTotal: true
            });
    }
    function initStatisticsByRegionQuery(divpageid) {
        regionTable = $("#" + divpageid).html();
        StatisticsByRegionQuery(1, '#');
    }
    function setStatisticsByRegionQuery(pageno, url) {
        $("#" + regionDiv).html(regionTable);
        createXML("p_");
        if (url == null || url == "undefined") {
            url = regionURL;
        }
        return url;
    }

    function StatisticsByRegionQuery(pageno, url) {

        if (true) {
            url = setStatisticsByRegionQuery(pageno, url);
            // create the grid
            // returns a jQ object with a 'g' property - that's ingrid
            var mygrid1 = $("#" + regionTableId).ingrid({
                url: url,
                height: ingridHeight,
                ingridPageWidth: statisticsWidth,
                ingridPageParams: sXML,
                noSortColIndex: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
                onRowSelect: null,
                pageNumber: pageno,
                colWidths: ["8.3%", "8.3%", "8.3%", "8.3%", "8.3%", "8.3%", "8.3%", "8.3%", "8.3%", "8.3%", "8.3%", "8.3%"],
                changeHref: function (table) {
                    var $chart_table = $(table).clone();
                    $chart_table.find('tr:last').remove();
                    buildChart($('#' + statisticsTypeAction[$('#statisticsType2').val()]['container']),
                            $(regionTable).clone().append($chart_table.find('tbody').html()),
                    false);
                },
                paging: false
            });
        }
    }

    function initStatisticsByDepartmentQuery(divpageid) {
        departmentTable = $("#" + divpageid).html();
        StatisticsByDepartmentQuery(1, '#');
    }
    function setStatisticsByDepartmentQuery(pageno, url) {
        $("#" + departmentDiv).html(departmentTable);
        createXML("p_");
        if (url == null || url == "undefined") {
            url = departmentURL;
        }
        return url;
    }

    function StatisticsByDepartmentQuery(pageno, url) {

        if (true) {
            url = setStatisticsByDepartmentQuery(pageno, url);
            // create the grid
            // returns a jQ object with a 'g' property - that's ingrid
            var mygrid1 = $("#" + departmentTableId).ingrid({
                url: url,
                height: ingridHeight,
                ingridPageWidth: statisticsWidth,
                ingridPageParams: sXML,
                noSortColIndex: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
                onRowSelect: null,
                pageNumber: pageno,
                colWidths: ["10%", "10%", "10%", "10%", "10%", "10%", "10%", "10%", "10%", "10%"],
                changeHref: function (table) {
                    var $chart_table = $(table).clone();
                    $chart_table.find('tr:last').remove();
                    buildChart($('#' + statisticsTypeAction[$('#statisticsType2').val()]['container']),
                            $(departmentTable).clone().append($chart_table.find('tbody').html()),
                    false);
                },
                paging: false
            });
        }
    }

    function initStatisticsBySuppportLeaderQuery(divpageid) {
        supportLeaderTable = $("#" + divpageid).html();
        StatisticsBySuppportLeaderQuery(1, '#');
    }
    function setStatisticsBySuppportLeaderQuery(pageno, url) {
        $("#" + supportLeaderDiv).html(supportLeaderTable);
        createXML("p_");
        if (url == null || url == "undefined") {
            url = supportLeaderURL;
        }
        return url;
    }

    function StatisticsBySuppportLeaderQuery(pageno, url) {

        if (validate()) {
            //总工查询时间控制
            if ($('#p_trackingDateFrom').val() || $('#p_trackingDateTo').val()) {
                $('#p_type').val(TRACKING_TYPE_CEREPLY);
            }
            else
                $('#p_type').val(null);
            url = setStatisticsBySuppportLeaderQuery(pageno, url);
            // create the grid
            // returns a jQ object with a 'g' property - that's ingrid
            var mygrid1 = $("#" + supportLeaderTableId).ingrid({
                url: url,
                height: ingridHeight,
                ingridPageWidth: statisticsWidth,
                ingridPageParams: sXML,
                noSortColIndex: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
                onRowSelect: null,
                pageNumber: pageno,
                ingridComplete: function () {
                    var $table = $('#' + supportLeaderDiv + ' table');
                    $table.find('tr').each(function (idx) {
                        var _tr = $(this);
                        if (_tr.find('td:nth(0)').html() == _tr.find('td:nth(1)').html()) {
                            _tr.find('td:nth(1)').remove();
                            _tr.find('td:nth(0)').attr('colSpan', 2);
                        }

                    });
                },
                changeHref: function (table) {
                    var $chart_table = $(table).clone();
                    $chart_table.find('tr:last').remove();
                    $chart_table.find('tr').each(function(){
                       var _tr=$(this);
                        var  _tds = _tr.find('td');
                        _tds.eq(0).remove();
                        _tds.eq(1).remove();
                    });
                    var _table = $(supportLeaderTable).clone();
                    _table.find('th:nth(0)').remove();
                    buildChart($('#' + statisticsTypeAction[$('#statisticsType2').val()]['container']),
                            _table.append($chart_table.find('tbody').html()),
                            true);
                },
                paging: false,
                colWidths: ["9%", "9%", "9%", "9%", "9%", "9%", "9%", "9%", "9%", "9%", "9%"]
            });
        }
    }
</script>
<%
    String statisticsType = request.getParameter("statisticsType");
    if (statisticsType == null)
        statisticsType = "";
%>

<style>
    .chart {
        float: left;
        margin: 2px 5px 2px 5px;
    }

    .bar {
        display: block;
    }
</style>
<input type="hidden" id="statisticsType2" value="<%=statisticsType%>">

<div id="byRegion">
    <div class="bar">
        <label><input type="radio" name="displayType" id="list__byRegion">列表</label>
        <label><input type="radio" name="displayType" id="chart__byRegion">图形</label>
    </div>

    <div id="byRegion__Div" class="list__byRegion dataresult">
        <table id="byRegion__table">
            <thead>
            <tr>
                <th name="l_region" datatype="String">大区</th>
                <th name="l_stNum" datatype="int">支持单数量</th>
                <th name="l_waitCompanyAppr" datatype="int">待公司审批</th>
                <th name="l_companyApprNoPass" datatype="int">公司审批不通过</th>
                <th name="l_waitDeptAppr" datatype="int">待部门审批</th>
                <th name="l_deptApprNoPass" datatype="int">部门审批不通过</th>
                <th name="l_gone" datatype="int">进行中</th>
                <th name="l_waitFeedback" datatype="int">待反馈</th>
                <th name="l_feedbacked" datatype="int">已反馈</th>
                <th name="l_gone" datatype="int">已完成</th>
                <th name="l_pause" datatype="int">已暂停</th>
                <th name="l_stop" datatype="int">已中止</th>
            </tr>
            </thead>
        </table>
    </div>

    <div id="byRegion__chart" class="chart__byRegion dataresult">
        <div id="byRegion__pie" class="chart"></div>
        <div id="byRegion__line" class="chart"></div>
    </div>
</div>

<div id="byDepartment">
    <div class="bar">
        <label><input type="radio" name="displayType" id="list__byDepartment">列表</label>
        <label><input type="radio" name="displayType" id="chart__byDepartment">图形</label>
    </div>

    <div id="byDepartment__Div" class="list__byDepartment dataresult">
        <table id="byDepartment__table">
            <thead>
            <tr>
                <th>部门</th>
                <th>支持单数量</th>
                <th>部门审批不通过</th>
                <th>待部门审批</th>
                <th>进行中</th>
                <th>待反馈</th>
                <th>已反馈</th>
                <th>已完成</th>
                <th>已暂停</th>
                <th>已中止</th>
            </tr>
            </thead>
        </table>
    </div>

    <div id="byDepartment__chart" class="chart__byDepartment dataresult">
        <div id="byDepartment__pie" class="chart"></div>
        <div id="byDepartment__line" class="chart"></div>
    </div>
</div>

<div id="bySupportLeader">
    <div class="bar">
        <label><input type="radio" name="displayType" id="list__bySupportLeader">列表</label>
        <label><input type="radio" name="displayType" id="chart__bySupportLeader">图形</label>
    </div>

    <div id="bySupportLeader__Div" class="list__bySupportLeader dataresult">
        <table id="bySupportLeader__table">
            <thead>
            <tr>
                <th colspan="2">部门</th>
                <th>负责人</th>
                <th>支持单数量</th>
                <th>待部门审批</th>
                <th>进行中</th>
                <th>待反馈</th>
                <th>已反馈</th>
                <th>已完成</th>
                <th>已暂停</th>
                <th>已中止</th>
            </tr>
            </thead>
        </table>
    </div>

    <div id="bySupportLeader__chart" class="chart__bySupportLeader dataresult">
        <div id="bySupportLeader__pie" class="chart"></div>
        <div id="bySupportLeader__line" class="chart"></div>
    </div>
</div>