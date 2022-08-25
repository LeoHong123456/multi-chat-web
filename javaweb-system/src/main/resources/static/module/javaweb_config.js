/**
 * 配置分组
 * @auth admin
 * @date 2020-04-20
 */
layui.use(['func'], function () {

    //声明变量
    var func = layui.func
        , $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', align: 'center', sort: true, fixed: 'left'}
            , {field: 'name', width: 150, title: '配置名称', align: 'center'}
            , {field: 'code', width: 150, title: '配置编码', align: 'center'}
            , {field: 'sort', width: 100, title: '排序', align: 'center'}
            , {field: 'note', width: 200, title: '排序', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("系统配置", 500, 450);

    }
});
