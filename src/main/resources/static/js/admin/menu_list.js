layui.config({
    version: '1554901098009' //为了更新 js 缓存，可忽略
});

layui.use(['table', 'carousel', 'upload', 'element', 'slider'], function(){
    var laydate = layui.laydate //日期
        ,laypage = layui.laypage //分页
        ,layer = layui.layer //弹层
        ,table = layui.table //表格
        ,carousel = layui.carousel //轮播
        ,upload = layui.upload //上传
        ,element = layui.element //元素操作
        ,slider = layui.slider //滑块
        ,treetable = layui.treetable

    //向世界问个好
    layer.msg('Hello World');

    //监听Tab切换
    element.on('tab(demo)', function(data){
        layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
            tips: 1
        });
    });

    //执行一个 table 实例
    table.render({
        elem: '#menuList'
        ,height: 420
        ,url: '/sys/menu/list' //数据接口
        ,title: '菜单列表'
        ,page: true //开启分页
        ,toolbar: '#addMenuBar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        ,totalRow: false //开启合计行
        ,defaultToolbar: [] //去掉  ['filter', 'print', 'exports']
        ,response: {
            //statusName: 'status' //规定数据状态的字段名称，默认：code
            statusCode: 1 //规定成功的状态码，默认：0
            //,msgName: 'hint' //规定状态信息的字段名称，默认：msg
            //,countName: 'total' //规定数据总数的字段名称，默认：count
        }
        ,cols: [[ //表头
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'menuId', title: '菜单ID', width:100, sort: true}
            ,{field: 'menuName', title: '菜单名称', width:100}
            ,{field: 'url', title: '菜单url', width: 200, sort: true, totalRow: true}
            ,{field: 'type', title: '类型', width:80, sort: true}
            ,{field: 'icon', title: '图标', width: 80, sort: true, totalRow: true}
            ,{field: 'order_num', title: '排序', width:80}
            ,{field: 'status', title: '状态', width: 80}
            ,{field: 'create_time', title: '创建时间', width: 100}
            ,{fixed: 'right', title: '操作',width: 165, align:'center', toolbar: '#menuBar'}
        ]]
    });

    //监听头工具栏事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id)
            ,data = checkStatus.data; //获取选中的数据
        switch(obj.event){
            case 'add':
                layer.msg('添加');
                break;
            case 'update':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else if(data.length > 1){
                    layer.msg('只能同时编辑一个');
                } else {
                    layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
                }
                break;
            case 'delete':
                if(data.length === 0){
                    layer.msg('请选择一行');
                } else {
                    layer.msg('删除');
                }
                break;
        };
    });

    //监听行工具事件
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            ,layEvent = obj.event; //获得 lay-event 对应的值
        if(layEvent === 'detail'){
            layer.msg('查看操作');
        } else if(layEvent === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'edit'){
            layer.msg('编辑操作');
        }
    });

    //执行一个轮播实例
    carousel.render({
        elem: '#test1'
        ,width: '100%' //设置容器宽度
        ,height: 200
        ,arrow: 'none' //不显示箭头
        ,anim: 'fade' //切换动画方式
    });


});