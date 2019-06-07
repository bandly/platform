layui.config({
    base: '../../../js/'
    ,version: '101100'
}).use('admin');

layui.use(['table', 'carousel','laydate', 'element','util','admin'], function(){
    var laydate = layui.laydate //日期
        ,layer = layui.layer //弹层
        ,table = layui.table //表格
        ,util = layui.util;

    //执行一个 table 实例
    var tab = table.render({
        elem: '#tableList'
        ,height: 500
        ,url: '/sys/user/list' //数据接口
        ,title: '管理员列表'
        ,page: true //开启分页
        ,totalRow: false //开启合计行
        ,defaultToolbar: [] //去掉  ['filter', 'print', 'exports']
        ,request: {
            pageName: 'pageNum' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'userId', title: 'userId',hide: true}
            ,{field: 'username', title: '账户名', width:300, sort: true}
            ,{field: 'email', title: '邮箱', width:200}
            ,{field: 'mobile', title: '手机号', width:200}
            ,{field: 'createTime', title: '创建时间', width: 200,templet: '<div>{{ layui.util.toDateString(d.createTime) }}  </div>'}
            ,{fixed: 'right', title: '操作',width: 165, align:'center', toolbar: '#operationBar'}
        ]]
    });


    //搜索
    layui.$("#searchBtn").click(function(event) {
        tab.reload({
            where: {
                username : layui.$("input[name='username']").val(),
                startCreateTime : layui.$("input[name='startCreateTime']").val(),
                endCreateTime : layui.$("input[name='endCreateTime']").val(),
                status : layui.$("#userStatus").val()
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
            WeAdminEdit('编辑用户','./edit.html',data.userId)
        }else if(layEvent === 'del'){
            console.info(layEvent)
        }else if(layEvent === 'detail'){
            console.info(layEvent)
        }
    });

    //创建时间
    laydate.render({
        elem: '#startCreateTime'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#endCreateTime'
        ,type: 'datetime'
    });


});
