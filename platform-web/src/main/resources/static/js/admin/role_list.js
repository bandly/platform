layui.config({
    base: '../../../js/'
    ,version: '101100'
}).use('admin');

layui.use(['table', 'carousel', 'element','util','admin'], function(){
    var layer = layui.layer //弹层
        ,table = layui.table //表格
        ,util = layui.util;

    var token = localStorage.getItem("X-Token");
    layui.$.ajaxSetup({
        headers : {
            "X-Token" : token
        }
    });

    //执行一个 table 实例
    var tab = table.render({
        elem: '#tableList'
        ,height: 500
        ,url: '/sys/role/list' //数据接口
        ,title: '角色列表'
        ,page: true //开启分页
        ,totalRow: false //开启合计行
        ,defaultToolbar: [] //去掉  ['filter', 'print', 'exports']
        ,request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'roleId', title: 'roleId',hide: true}
            ,{field: 'roleName', title: '角色名称', width:300, sort: true}
            ,{field: 'createTime', title: '创建时间', width: 200,templet: '<div>{{ layui.util.toDateString(d.createTime) }}  </div>'}
            ,{fixed: 'right', title: '操作',width: 165, align:'center', toolbar: '#operationBar'}
        ]]
    });


    //搜索
    layui.$("#searchBtn").click(function(event) {
        tab.reload({
            where: {
                roleName : layui.$("input[name='roleName']").val()
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });


    //监听行工具事件
    table.on('tool(layTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        console.info(obj);
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'edit'){
            WeAdminEdit('编辑角色','./edit.html',data.roleId)
        }else if(layEvent === 'del'){
            console.info(layEvent)
        }else if(layEvent === 'detail'){
            console.info(layEvent)
        }
    });



});
