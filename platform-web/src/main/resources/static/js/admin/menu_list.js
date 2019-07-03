var editObj=null,ptable=null,treeGrid=null,tableId='treeTable',layer=null;


layui.config({
    base: '../../../js/extends/'
    ,version: '101100'
}).use('treeGrid');

layui.config({
    base: '../../../js/'
    ,version: '101100'
}).use('admin');

layui.use(['jquery','treeGrid','layer'], function(){
    var $=layui.jquery;
    treeGrid = layui.treeGrid;//很重要
    layer=layui.layer;


    var token = localStorage.getItem("X-Token");
    layui.$.ajaxSetup({
        headers : {
            "X-Token" : token
        }
    });


    ptable=treeGrid.render({
        id:tableId
        ,elem: '#'+tableId
        ,url:'/sys/menu/list'
        ,cellMinWidth: 100
        ,idField:'menuId'//必須字段
        ,treeId:'menuId'//树形id字段名称
        ,treeUpId:'parentId'//树形父id字段名称
        ,treeShowName:'menuName'//以树形式显示的字段
        ,heightRemove:[".dHead",10]//不计算的高度,表格设定的是固定高度，此项不生效
        ,height:'100%'
        ,isFilter:false
        ,iconOpen:false//是否显示图标【默认显示】
        ,isOpenDefault:false//节点默认是展开还是折叠【默认展开】
        ,loading:true
        ,method:'get'
        ,isPage:false
        ,cols: [[
            //{type:'numbers'}
            {type:'radio'}
            //,{type:'checkbox',sort:true}
            ,{field:'menuId',width:100, title: '编号',sort:true}
            ,{field:'menuName', width:300, title: '名称'}
            ,{field:'parentId', title: '父Id'}
            //,{field:'type', hide:true}
            ,{field:'typeName', width:80, title: '类型'}
            ,{field:'orderNum', width:80, title: '排序'}
            ,{field:'url', width:150, title: '菜单URL'}
            ,{field:'perms', width:200, title: '授权标识'}
            ,{width:300,title: '操作', align:'center'/*toolbar: '#barDemo'*/
                ,templet: function(d){
                    var html='';
                    var addBtn='<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="add">添加</a>';
                    var editBtn='<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit">编辑</a>';
                    var delBtn='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
                    return addBtn+editBtn+delBtn;
                }
            }
        ]]
        ,parseData:function (res) {//数据加载后回调
            return res;
        }
        ,onClickRow:function (index, o) {
            console.log(index,o,"单击！");
            //msg("单击！,按F12，在控制台查看详细参数！");
        }
        ,onDblClickRow:function (index, o) {
            console.log(index,o,"双击");
            msg("双击！,按F12，在控制台查看详细参数！");
        }
        ,onCheck:function (obj,checked,isAll) {//复选事件
            console.log(obj,checked,isAll,"复选");
            msg("复选,按F12，在控制台查看详细参数！");
        }
        ,onRadio:function (obj) {//单选事件
            console.log(obj,"单选");
            msg("单选,按F12，在控制台查看详细参数！");
        }
    });

    treeGrid.on('tool('+tableId+')',function (obj) {
        if(obj.event === 'del'){//删除行
            del(obj);
        }else if(obj.event==="add"){//添加行
            add(obj);
        }else if(obj.event==="edit"){//编辑行
            edit(obj);
        }
    });
});

function del(obj) {
    layer.confirm("你确定删除数据吗？如果存在下级节点则一并删除，此操作不能撤销！", {icon: 3, title:'提示'},
        function(index){//确定回调
            //obj.del();
            var pdata=obj?obj.data:null;
            var menuId = null;
            if(null == pdata){
               return;
            }
            menuId = pdata.menuId;
            var param={};
            param.menuId=menuId;
            layui.$.ajax({
                url: '/sys/menu/delete',
                type: 'post',
                data: param,
                beforeSend: function () {
                    this.layerIndex = layer.load(0, {shade: [0.5, '#393D49']});
                },
                success: function (data) {
                    if (data.code == 1) {
                        layer.alert("删除成功", {icon: 6}, function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                            //重新加载
                            reload();

                        });


                    } else {
                        layer.alert("删除失败[" + data.msg + "]", {icon: 6}, function () {
                            // 获得frame索引
                            var index = parent.layer.getFrameIndex(window.name);
                            //关闭当前frame
                            parent.layer.close(index);
                        });
                    }
                },
                complete: function () {
                    layer.close(this.layerIndex);
                },
            });


            layer.close(index);
        },function (index) {//取消回调
            layer.close(index);
        }
    );
}


var i=1000000;
//添加
function add(pObj) {
   var pdata=pObj?pObj.data:null;
 /*   var param={};
    param.name='水果'+Math.random();
    param.id=++i;
    param.pId=pdata?pdata.id:null;
    treeGrid.addRow(tableId,pdata?pdata[treeGrid.config.indexName]+1:0,param);*/
    var menuId = null;
    if(null != pdata){
       menuId = pdata.menuId;
    }
    WeAdminEdit('添加菜单','./add.html',menuId);

}

//编辑
function edit(pObj) {
    var pdata=pObj?pObj.data:null;
    /*   var param={};
       param.name='水果'+Math.random();
       param.id=++i;
       param.pId=pdata?pdata.id:null;
       treeGrid.addRow(tableId,pdata?pdata[treeGrid.config.indexName]+1:0,param);*/
    var menuId = null;
    if(null != pdata){
        menuId = pdata.menuId;
    }
    WeAdminEdit('编辑菜单','./edit.html',menuId);

}

function print() {
    console.log(treeGrid.cache[tableId]);
    msg("对象已打印，按F12，在控制台查看！");
}

function msg(msg) {
    var loadIndex=layer.msg(msg, {
        time:3000
        ,offset: 'b'//顶部
        ,shade: 0
    });
}

function openorclose() {
    var map=treeGrid.getDataMap(tableId);
    var o= map['102'];
    treeGrid.treeNodeOpen(tableId,o,!o[treeGrid.config.cols.isOpen]);
}


function openAll() {
    var treedata=treeGrid.getDataTreeList(tableId);
    treeGrid.treeOpenAll(tableId,!treedata[0][treeGrid.config.cols.isOpen]);
}

function getCheckData() {
    var checkStatus = treeGrid.checkStatus(tableId)
        ,data = checkStatus.data;
    layer.alert(JSON.stringify(data));
}
function radioStatus() {
    var data = treeGrid.radioStatus(tableId)
    layer.alert(JSON.stringify(data));
}
function getCheckLength() {
    var checkStatus = treeGrid.checkStatus(tableId)
        ,data = checkStatus.data;
    layer.msg('选中了：'+ data.length + ' 个');
}

function reload() {
    treeGrid.reload(tableId,{
        page:{
            curr:1
        }
    });
}
function query() {
    treeGrid.query(tableId,{
        where:{
            name:'sdfsdfsdf'
        }
    });
}

function test() {
    console.log(treeGrid.cache[tableId],treeGrid.getClass(tableId));


    /*var map=treeGrid.getDataMap(tableId);
    var o= map['102'];
    o.name="更新";
    treeGrid.updateRow(tableId,o);*/
}