/**
 * 商品属性
 * @auth 鲲鹏
 * @date 2020-06-08
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
            , {field: 'name', width: 150, title: '属性名称', align: 'center'}
            , {field: 'productAttributeCategoryId', width: 100, title: '所属类型', align: 'center', templet(d) {
                    return d.productAttributeCategoryName + "|" + d.productAttributeCategoryId;
                }}
            , {field: 'type', width: 100, title: '类别', align: 'center', templet(d) {
                var cls = "";
                if (d.type == 1) {
                    // 规格
                    cls = "layui-btn-normal";
                } else if (d.type == 2) {
                    // 属性
                    cls = "layui-btn-danger";
                }
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.typeName+'</span>';
            }}
            , {field: 'sort', width: 100, title: '排序号', align: 'center'}
            , {field: 'createUserName', width: 100, title: '添加人', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("商品规格", 450, 350);

    }
});
