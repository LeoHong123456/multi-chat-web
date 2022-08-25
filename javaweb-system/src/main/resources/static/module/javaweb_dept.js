/**
 * 部门
 * @auth admin
 * @date 2020-05-03
 */
layui.use(['func'], function () {

    //声明变量
    var func = layui.func
        , $ = layui.$;

    if (A == 'index') {
        //【TABLE列数组】
        var cols = [
              {field: 'id', width: 80, title: 'ID', align: 'center', sort: true}
            , {field: 'name', width: 230, title: '部门名称', align: 'left'}
            , {field: 'code', width: 100, title: '部门编码', align: 'center'}
            , {field: 'fullname', width: 150, title: '部门全称', align: 'center'}
            , {field: 'type', width: 100, title: '类型', align: 'center', templet(d) {
                var cls = "";
                if (d.type == 1) {
                    // 公司
                    cls = "layui-btn-normal";
                } else if (d.type == 2) {
                    // 子公司
                    cls = "layui-btn-danger";
                } else if (d.type == 3) {
                    // 部门
                    cls = "layui-btn-warm";
                } else if (d.type == 3) {
                    // 小组
                    cls = "layui-btn-primary";
                }
                    return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.typeName+'</span>';
            }}
            , {field: 'sort', width: 80, title: '排序', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {width: 220, title: '功能操作', align: 'left', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.treetable(cols, "tableList");

        //【设置弹框】
        func.setWin("部门", 750, 480);

    }
});
