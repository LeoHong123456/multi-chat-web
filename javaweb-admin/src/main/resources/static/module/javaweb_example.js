/**
 * 演示案例一
 * @auth 鲲鹏
 * @date 2021-10-25
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
            , {field: 'avatar', width: 100, title: '头像', align: 'center', templet: function (d) {
                var avatarStr = "";
                if (d.avatarUrl) {
                    avatarStr = '<a href="' + d.avatarUrl + '" target="_blank"><img src="' + d.avatarUrl + '" height="26" /></a>';
                }
                return avatarStr;
              }
            }
            , {field: 'name', width: 100, title: '案例名称', align: 'center'}
            , {field: 'type', width: 100, title: '类型', align: 'center', templet(d) {
                var cls = "";
                if (d.type == 1) {
                    // 京东
                    cls = "layui-btn-normal";
                } else if (d.type == 2) {
                    // 淘宝
                    cls = "layui-btn-danger";
                } else if (d.type == 3) {
                    // 拼多多
                    cls = "layui-btn-warm";
                } else if (d.type == 4) {
                    // 唯品会
                    cls = "layui-btn-primary";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.typeName+'</span>';
            }}
            , {field: 'isVip', width: 100, title: '是否VIP', align: 'center', templet(d) {
                var cls = "";
                if (d.isVip == 1) {
                    // 是
                    cls = "layui-btn-normal";
                } else if (d.isVip == 2) {
                    // 否
                    cls = "layui-btn-danger";
                } 
				return '<span class="layui-btn ' + cls + ' layui-btn-xs">'+d.isVipName+'</span>';
            }}
            , {field: 'planTime', width: 180, title: '计划时间', align: 'center'}
            , {field: 'status', width: 100, title: '状态', align: 'center', templet: '#statusTpl'}
            , {field: 'sort', width: 100, title: '显示顺序', align: 'center'}
            , {field: 'note', width: 100, title: '备注', align: 'center'}
            , {field: 'createUserName', width: 100, title: '添加人', align: 'center'}
            , {field: 'createTime', width: 180, title: '添加时间', align: 'center'}
            , {field: 'updateUserName', width: 100, title: '更新人', align: 'center'}
            , {field: 'updateTime', width: 180, title: '更新时间', align: 'center'}
            , {fixed: 'right', width: 150, title: '功能操作', align: 'center', toolbar: '#toolBar'}
        ];

        //【渲染TABLE】
        func.tableIns(cols, "tableList");

        //【设置弹框】
        func.setWin("演示案例一");

        //【设置状态】
        func.formSwitch('status', null, function (data, res) {
            console.log("开关回调成功");
        });
    }
});
