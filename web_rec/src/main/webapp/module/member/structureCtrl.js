member.controller('StructureCtrl', ['$scope','$window','$state','safeApply','localStorageService','TreeDataService','MemberControllerService',function($scope,$window,$state,safeApply,localStorageService,TreeDataService,MemberControllerService) {
'use strict';
    $scope.id = $state.params.id;
    $scope.init=function(){
        var onSuccess=function (data, status) {
            $scope.mems = data.result.returnObject;
            TreeDataService.createTree("gsTree",$scope.mems,null,1,null,null,null,null,null,false,null);
            safeApply($scope,function(){
                showDeptImage( $.fn.zTree.getZTreeObj("gsTree").getNodes()[0]);
            });
        };

        MemberControllerService.findStructurlAllMemberById({id:$scope.id}).then(onSuccess,null);
    }
    $scope.init();



    $scope.class_one="desigh_xuanze";
    $scope.imageurl = appConfig.imageURL;
    $scope.htOrgs = [];
    $scope.aj= {};
    $scope.yy= [];
    $scope.treeId=-1;
    $scope.orientation=null;
    if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
        var msViewportStyle = document.createElement('style')
        msViewportStyle.appendChild(
            document.createTextNode(
                '@-ms-viewport{width:auto!important}'
            )
        )
        document.querySelector('head').appendChild(msViewportStyle)
    }
    var __config = {
        _note_info : "默认配置信息,这堆配置信息,可以通过后台配置来覆盖",
        width_dept : 90, // 宽
        height_dept : 100,
        padding_dept : 12,
        padding_dept_top : 20,
        radius_dept : 10,
        margin_parent : 45, // 间距
        margin_partner : 28,
        exp_radius : 8, // 展开按钮的半径大小
        color : '#0099cb', // 默认颜色
        downposition : null,
        prevposition : null,
        offset : {x: 0, y:0},
        //
        direction : 0, //拓扑方向. 0为从左到右, 1为从上到下
        zoom_num : 10, // 缩放倍数,小数. 数字越小则距离屏幕前的你越近,显示越大
        zoom_num_dept : 14, //不显示部门和负责人的阀值(含)
        zoom_num_emp : 17, // 不显示经理和职员的阀值(含)
        expand_level : 2, // 展开级别,展开全部,则设置为100即可
        expand_all : 0,	  // 展开所有
        show_mgr_title : 1, // 显示部门经理title
        show_mgr_name : 1, // 显示部门经理 name
        mgr_src : "./images/m_36.png",
        emp_src : "./images/e_36.png",
        exp_c_src : "./images/expand_c.png",
        exp_x_src : "./images/expand_x.png",
        mgr_emp_size : 18,
        min_paper_width : 800,
        min_paper_height : 600,
        old_width : 0,
        old_height : 0,
        convert_fullscreen: 0,
        left_paper : 100, // 最左上角的 paper
        top_paper : 50,
        line_color : "#3333ff", // 连线的颜色
        prevNodePositionInfo : {
            //展开某个节点时的位置信息
            enable : false
            , bbox : null
            , prevoffset : null
        },
        //
        orginfo_json_url : 'api/orginfo.json'
    };
    //
    var global = {
        svg : null
        ,paper : null
        , pbar : null
        , config : __config
        , currentCacheDept : null
    };
    window.global = global;
    // 部门节点的属性模板
    var defaultDeptNode = {
        width : global.config.width_dept, // 宽
        height : global.config.height_dept, //高
        expand: 0,	// 是否强制展开, 记录在tree中比较合理
        hideNodeCount : 0, // 隐藏元素数量
        spanX  : 0, // 占用宽
        spanY : 0,  // 占用高
        x : 0,
        y : 0,
        expand_status : 0, // 1 是展开,显示 - 号，强制显示子节点; 0 是默认不确定， 2是收缩显示加号
        expand_level : 0,
        treenode : null,  // 树节点
        occs:new Array(),
        mems:new Array()
    };
    $(pageInit);
    //
    // 根据node节点，绘制整棵树
    function drawDeptTree(paper, root){
        //
        if(!paper || !root){
            return null;
        }
        //
        var expand_level = global.config.expand_level;

        // 1. 遍历,计算整体大小,调用另一个递归方法来计算
        var tree_with_size = calcTreeSize(root, expand_level);
        //tree_with_size.spanX = 2936;
        // 2. 预定义大小,计算出结果
        var tree_with_xy = calcXY(tree_with_size, 0, 0, expand_level);

        // 缓存
        global.tree_with_xy = tree_with_xy;

        // 3. 开始绘制
        fitPaperSize(paper, tree_with_xy, expand_level);
        var tree = drawTree(paper, tree_with_xy, expand_level);
        //
        // refreshPaperZoom();

        // 校正位置
        moveRootToCenter(paper, tree_with_xy);

        // 3.1 校正点击加号展开时自身位置不改变的问题
        fixExpandNodePosition(paper, tree_with_xy);

        // 4. 绑定事件

        // 5. 绘制复选框等其他按钮
        //
        return tree;
    };
    // 自适应 paper 大小
    function fitPaperSize(paper, tree_with_xy, expand_level){
        // 设置paper的大小
        var spanX = tree_with_xy.spanX + global.config.left_paper * 2.5;
        var spanY = tree_with_xy.spanY + global.config.top_paper * 2.5;

        var width = global.config.min_paper_width;
        var height = global.config.min_paper_height;
        // 比对最小限制
        if(spanX > width){
            width = spanX;
        }
        if(spanY > height){
            height = spanY;
        }
        if(width<1900){
            width=900;
        }
        paper.setSize(width, height);
    };
    // 校正位置
    function moveRootToCenter(paper, tree_with_xy){
        //
        var dxdy = calcDxDy(tree_with_xy);
        //
        var dx = dxdy.dx;
        var dy = dxdy.dy;
        //
        if(global.config.direction){
            //global.config.offset.y = dy;
        } else {
            //global.config.offset.x = dx;
        }
        global.config.offset.y = dy;
        global.config.offset.x = dx;
        //
        refreshPaperZoom();

    };
    // 清空缓存的这个值.
    function clearExpandNodePosition(){
        if(global.config.prevNodePositionInfo){
            global.config.prevNodePositionInfo.enable = false; // 不启用
            global.config.prevNodePositionInfo.bbox = null; // 置空
            global.config.prevNodePositionInfo.prevoffset = null; // 置空
            global.config.prevNodePositionInfo.prevnode = null;
        }
    };
    // 设置展开节点的信息保存
    function setExpandNodePosition(rect){
        if(!rect){
            return;
        }
        clearExpandNodePosition();
        //
        var node = rect.datanode;
        var bbox = rect.getBBox();

        //
        var paper = global.paper;
        //
        //
        var config = global.config;
        //
        var prevRootXY = getPrevRootXY();
        var dxdy = calcDxDy(prevRootXY);
        // 克隆
        var offset = {
            x : config.offset.x
            , y : config.offset.y
        };
        //
        var prevNodePositionInfo = {
            enable : true // 启用
            ,bbox : bbox  // 设值
            , prevoffset : offset	// 之前的offset
            , dxdy : dxdy
            , prevnode : node
        };
        //
        global.config.prevNodePositionInfo = prevNodePositionInfo;
    };
    function calcDxDy(tree_with_xy){
        //
        var rx = tree_with_xy.x;
        var ry = tree_with_xy.y;
        //
        var startX = startX || global.config.left_paper;
        var startY = startY || global.config.top_paper;
        //
        var spanX = tree_with_xy.spanX;
        var spanY = tree_with_xy.spanY;
        //
        var pW = global.config.min_paper_width;
        var pH = global.config.min_paper_height;
        //
        var dx =  rx - pW/2 + startX;
        var dy =  ry - pH/2 + startY;

        //
        // 在计算中心点偏移量时,就根据 direction 固定偏移少的那一边的x,y偏移
        if(global.config.direction){ // 根据方向决定
            dx = 0; // 强制不使用计算值
        } else {
            dy = 0;
        }
        //
        var dxdy = {
            dx: dx
            ,dy: dy
        };
        return dxdy;
    };
    // 根据 root 获取相对偏移,修正
    function fixExpandNodePosition(paper, tree_with_xy){
        //
        var config = global.config;
        var prevNodePositionInfo = config.prevNodePositionInfo;
        if(!prevNodePositionInfo || !prevNodePositionInfo.enable){
            savePrevRootXY(tree_with_xy);
            return; // 没有相应的信息,则不往下执行.
        }
        //
        var prevoffset = prevNodePositionInfo.prevoffset;
        var dxdy = prevNodePositionInfo.dxdy;

        //
        var _dx = dxdy.dx;
        var _dy = dxdy.dy;
        //
        var _ox = prevoffset.x;
        var _oy = prevoffset.y;
        // 取得点击加号以前的拖动偏移量
        var _x = _ox - _dx;
        var _y = _oy - _dy;

        config.offset.y += _y;
        config.offset.x += _x;
        //
        config.prevNodePositionInfo = null;
        savePrevRootXY(tree_with_xy);
        refreshPaperZoom();

        return;

    };
    function getPrevRootXY(){
        return global.config.prevRootXY;
    };
    function savePrevRootXY(prevRoot){
        if(!prevRoot){
            return;
        }
        global.config.prevRootXY = prevRoot;
    };

    function fixZoom2Offset(){
        if(global.config.prevZoomOffset != null){
            global.config.offset = global.config.prevZoomOffset;
            refreshPaperZoom();
        }
        //
        global.config.prevZoomOffset = null;
    };
    function cacheZoom2Offset(){
        // 新对象
        global.config.prevZoomOffset = {
            x : global.config.offset.x
            ,
            y : global.config.offset.y
        };
    };
    // 根据node节点，获取 shape. 这是绘制单个节点
    function drawDept(paper, node){
        //
        if(!node){
            return null;
        }
        //
        var w = node.width || global.config.width_dept;
        var h = node.height || global.config.height_dept;
        var r = global.config.radius_dept;
        var pad = global.config.padding_dept;
        var pad_top = global.config.padding_dept_top;
        var direction = global.config.direction;

        //
        var x_s = node.x || global.config.left_paper;
        var y_s = node.y || global.config.top_paper;
        var x_e = x_s + w;
        var y_e = y_s + h;

        // 1. 绘制矩形框
        var rect = paper.rect(x_s, y_s, w, h, r);
        //
        //rect.dblclick(dbclickHandler);
        rect.datanode = node;

        var color = Raphael.color(global.config.color);
        rect.attr({
            fill : color,
            stroke : color,
            "fill-opacity" : 1,
            "stroke-width" : 2
        });
        //
        node.rect = rect;

        // 绘制展开/收缩按钮

        // expand_level
        // expand_status
        // 1.1 绘制下方的展开状态图标
        // 没有子节点的情况
        var expand_status = node.expand_status;
        //
        var exp_circle = null;

        // 上下左右.
        var pDown = {
            x : x_s + w/2 +1,
            y : y_e + 8
        };
        var pRight = {
            x : x_e +  8,
            y : y_s + h / 2
        };
        //
        if(0 == direction){// 判断横竖
            var exp_x = pDown.x;
            var exp_y = pDown.y;
        } else {
            var exp_x = pRight.x;
            var exp_y = pRight.y;
        }

        //
        var exp_w = 16;
        var exp_h = 16;
        exp_x -= exp_w/2;
        exp_y -= exp_h/2;

        var expsrc = global.config.exp_c_src;
        if(2 == expand_status){
            expsrc = global.config.exp_x_src;
        }
        /**
         *
         //exp_radius
         var exp_radius = global.config.exp_radius;
         var charExp = "";
         if(1 == expand_status){
			// 展开状态, -号
			charExp = "-";
		} else if(2 == expand_status){
			// 收缩状态, +号
			var charExp = "+";
		}
         */
        //
        if(1 == expand_status || 2 == expand_status){

            var exp_image = paper.image(expsrc, exp_x, exp_y, exp_w, exp_h);
            iconcursor(exp_image);
            /**
             // 绘制展开状态/收缩状态, +号
             var exp_circle = paper.circle(exp_x, exp_y, exp_radius);
             var exp_char = paper.text(exp_x, exp_y, charExp);
             //
             exp_circle.attr({
				"stroke-width": 2
			});
             exp_char.attr({
				"font-size": 18
			});
             unselect(exp_char);
             */
        } else {
            // 不绘制. 0
        }
        //
        /**
         exp_circle && exp_circle.attr({
			fill : "#eee"
			,stroke : color
			,cursor : "pointer"
		});
         exp_char && exp_char.attr({
			stroke : color
			,cursor : "pointer"
		});
         */
            // 绑定展开事件
        function exp_handler(e, data){
            // treenode
            var to_expand_status = 0;
            if(1 == expand_status){
                // 变成关闭
                to_expand_status = 2;
            } else {
                to_expand_status = 1;
            }
            //
            setExpandNodePosition(rect);
            // 改变状态,刷新
            node.treenode &&( node.treenode.to_expand_status = to_expand_status);
            refreshDeptTree();
        }
        //
        exp_image && exp_image.click(exp_handler);
        //exp_circle && exp_circle.click(exp_handler);
        //exp_char && exp_char.click(exp_handler);


        // 设置字体
        var font = paper.getFont("Times", 800); //Times
        // 2. 绘制部门信息
        var text = node.name;
        var tx = x_s + pad;
        var ty = y_s + pad_top;
        //
        var fontSize = 16;
        var textAnchor = "start";
        var textMaxLen = 10;
        var lines = 1;
        if(text.length > textMaxLen){
            lines = 2;
        }
        //
        // 如果缩放比例太小,则进行特殊处理
        if(20-global.config.zoom_num >= global.config.zoom_num_emp){ // 补丁
            //
            textMaxLen = 6;
            fontSize = 24;
            tx = x_s + w/2;
            ty = ty += h/2 - pad_top*4/5;
            if(lines > 1){
                ty -= pad_top*2/3;
            }
            textAnchor = "middle";
        } else if(20-global.config.zoom_num >= global.config.zoom_num_dept){ // 补丁
            //
            textMaxLen = 8;
            fontSize = 20;
            tx = x_s + w/2;
            ty = ty += h/2 - pad_top*3/2;
            if(lines > 1){
                ty -= pad_top/2;
            }
            textAnchor = "middle";
        }

        if(text.length > textMaxLen){
            text = text.substr(0, textMaxLen) + "\n" + text.substr(textMaxLen);
            ty += pad_top/2;
            lines = 2;
        }
        var nameText = paper.text(tx, ty, text);
        //var nameText = paper.print(x_s + w/2, y_s + pad, text, font, 30);

        nameText.click(dbclickHandler);
        nameText.datanode = node;
        nameText.attr({
            "fill":"#fff",
            "font-family": "microsoft yahei",
            "font-weight": "bold",
            "text-anchor": textAnchor,
            "font-size" : fontSize,
            cursor : "default"
        });
        unselect(nameText);
        iconcursor(nameText);
        node.nameText = nameText;

        // 3. 绘制部门经理
        //
        var show_mgr_title = global.config.show_mgr_title;
        var show_mgr_name = global.config.show_mgr_name;

        var original = node || {};
        //
        var linkman = original.linkman;
        var linktitle = original.linktitle;
        //
        var linkinfo = "";
        if(show_mgr_title && linktitle){
            linkinfo += linktitle ;
        }
        if(show_mgr_title && show_mgr_name && linktitle && linkman){
            linkinfo += "（" ;
        }
        if(show_mgr_name && linkman){
            linkinfo += linkman;
        }
        if(show_mgr_title && show_mgr_name && linktitle && linkman){
            linkinfo += "）" ;
        }
        //
        var linkx = x_s + w/2;
        var linky = y_s + h/2 + pad/2 + 4;
        // 如果缩放比例太小,则不显示
        if(20-global.config.zoom_num < global.config.zoom_num_dept){

            var linkText = paper.text(linkx, linky , linkinfo);
            //linkText.dblclick(dbclickHandler);
            linkText.datanode = node;
            linkText.attr({
                "font-family":"microsoft yahei"
                , "fill": "#fff"
                , "font-size" : 14
                , "text-anchor" : "middle"
            });
            unselect(linkText);
        }

        // !!!! 注意, 因为绘制职员图标在此函数的最后面,所以不显示时,直接return了。否则，需要包含在else里面。
        //
        // 如果缩放比例太小,则不显示
        if(20-global.config.zoom_num >= global.config.zoom_num_emp){
            //
            return node;
        }


        // 4. 职位图标
        var msrc = global.config.mgr_src;
        var mw = global.config.mgr_emp_size;
        var mh = global.config.mgr_emp_size;
        var mx = x_s + pad;
        var my = y_e - pad/2 - mh;
        var manEle = paper.image(msrc, mx, my, mw, mh);
        iconcursor(manEle);
        //
        var fsize= 14;
        var mnx = mx + pad + mw;
        var mny = my +  fsize - pad/2;
        var manText =null;
        var occ=null;
//        for(var i=0;i<defaultDeptNode.occs.length;i++){
//            if(defaultDeptNode.occs[i].oid==node.id){
//                occ=defaultDeptNode.occs[i];
//            }
//        }
        if(occ==null){
//            var onSuccess=function (data) {
//                occ=new Object({});
//                occ.oid=node.id;
//                occ.datas=data.result.returnObject;
//                defaultDeptNode.occs.push(occ);
//                manText = paper.text(mnx, mny , occ.datas.length).attr({
//                    "font-family":"microsoft yahei"
//                    , "font-size" : fsize
//                    , "text-anchor" : "middle"
//                    , "font-weight": "bold"
//                    , "fill": "#fff"
//                    , "color" : "white"
//                });
//                unselect(manText);
//            };
//            var onError=function (data, status) {
//                $scope.showMsg(data.errorMsg||data.result.errorMessages,'warn');
//            };
//            OrganizationControllerService.listOccByOrgNum({oid:node.id}).then(onSuccess,onError);
        }else{
            manText = paper.text(mnx, mny , occ.datas.length).attr({
                "font-family":"microsoft yahei"
                , "font-size" : fsize
                , "text-anchor" : "middle"
                , "font-weight": "bold"
                , "fill": "#fff"
                , "color" : "white"
            });
            unselect(manText);
        }
        //
        // 可能后期用来做模板
        var $tipDivTempMgr = $("#tip_holder_template_mgr");
        var $tipDivTempEmp = $("#tip_holder_template_emp");
        //
        var $tipMgr = null;
        var $tipEmp = null;
        //
        function cloneDiv($temp){
            var current = new Date().getTime();
            safeApply($scope,function(){
                for(var i=0;i< defaultDeptNode.occs.length;i++){
                    if(defaultDeptNode.occs[i].oid==node.id){
                        $scope.occs=defaultDeptNode.occs[i].datas;
                        break;
                    }
                }
                for(var i=0;i< defaultDeptNode.mems.length;i++){
                    if(defaultDeptNode.mems[i].oid==node.id){
                        $scope.mems=defaultDeptNode.mems[i].datas;
                        break;
                    }
                }
            });
            var $tipDiv = $temp.clone();
            $tipDiv.attr("id", "tip_"+current);
            $tipDiv.addClass("transient");
            // 关闭事件
            var $close = $tipDiv.find(".close");
            //
            $close.click(function(){
                //$tipDiv.addClass("hide");
                //$tipDiv.remove();
                //$tipDiv = null;
                if($tipMgr){
                    // 移除同一个作用域内的,即同一个部门的弹出窗
                    $tipMgr.remove();
                    $tipMgr = null;
                } ;
                if($tipEmp){
                    // 移除同一个作用域内的,即同一个部门的弹出窗
                    $tipEmp.remove();
                    $tipEmp = null;
                }

            });
            //
            return $tipDiv;
        };

        //
        var needRemove = 0;
        var manTipsClick = function(x,y,z){
            //
            z = z + 10;
            y = y + 2;
            //
            if($tipMgr){
                //
                $tipMgr.remove();
                $tipMgr = null;
            } ;
            if($tipEmp){
                //
                $tipEmp.remove();
                $tipEmp = null;
            } else {
                $tipEmp = cloneDiv($tipDivTempEmp);
                //
                // 需要修改坐标
                $tipEmp.css({top : z +"px", left : y +"px"});
                $tipEmp.appendTo(document.body);
                $tipEmp.removeClass("hide");
            }
            //
        };
        manEle.click(manTipsClick);

        // 职员图标
        var esrc = global.config.emp_src;
        var ew = mw;
        var eh = mh;
        var ex = x_e - 2*pad - ew;
        var ey = my -2;
        var pcEle = paper.image(esrc, ex, ey, ew, eh);
        iconcursor(pcEle);

        //
        var fsize= 14;
        var enx = ex + pad/2 + ew + 4;
        var eny = mny;
        var pcText =null;
        var mem=null;
//        for(var i=0;i<defaultDeptNode.mems.length;i++){
//            if(node.id==defaultDeptNode.mems[i].oid){
//                mem=defaultDeptNode.mems[i];
//            }
//        }
        if(mem==null){
//            var onSuccess=function (data) {
//                mem=new Object({});
//                mem.oid=node.id;
//                mem.datas=data.result.returnObject;
//                defaultDeptNode.mems.push(mem);
//                pcText = paper.text(enx, eny , mem.datas.length).attr({
//                    "font-family":"microsoft yahei"
//                    , "font-size" : fsize
//                    , "text-anchor" : "middle"
//                    , "font-weight": "bold"
//                    , "fill": "#fff"
//                    , "color" : "white"
//                });
//                unselect(pcText);
//            };
//            var onError=function (data, status) {
//                $scope.showMsg(data.errorMsg||data.result.errorMessages,'warn');
//            };
//            OrganizationControllerService.listMemByOrgNumTwo({oid:node.id}).then(onSuccess,onError);
        }else{
            pcText = paper.text(enx, eny , mem.datas.length).attr({
                "font-family":"microsoft yahei"
                , "font-size" : fsize
                , "text-anchor" : "middle"
                , "font-weight": "bold"
                , "fill": "#fff"
                , "color" : "white"
            });
            unselect(pcText);
        }
        var pcTipsClick = function(x,y,z){
            //
            z = z + 25;
            y = y - 35;
            //
            if($tipEmp){
                //
                $tipEmp.remove();
                $tipEmp = null;
            };
            if($tipMgr){
                //
                $tipMgr.remove();
                $tipMgr = null;
            } else {
                $tipMgr = cloneDiv($tipDivTempMgr);
                //
                // 需要修改坐标
                $tipMgr.css({top : z +"px", left : y +"px"});
                $tipMgr.appendTo(document.body);
                $tipMgr.removeClass("hide");
            }
        };
        pcEle.click(pcTipsClick);
        //
        return node;
    };

    // 计算树的大小,完全包装为新对象
    function calcTreeSize(root, expand_level){
        if(!root){
            return null;
        }
        // 每次都是新对象
        var defNode = $.extend({} ,defaultDeptNode);
        // 克隆对象
        var tree_with_size = $.extend(defNode, root);
        //

        // treenode
        //
        tree_with_size.treenode = root; // 缓存tree对象
        tree_with_size.expand_level = expand_level;// 存储展开级别

        //
        var treeObj = $.fn.zTree.getZTreeObj("gsTree");
        var children = treeObj.getNodesByParam("pId", root.id, null);
        //
        var subnodes = [];
        for (var i = 0; i < children.length;  i++) {
            var jsnode = children[i];
            //
            // 迭代遍历, 如果还有子元素,则遍历子元素
            // 没有,则不会进入循环
            var snode = calcTreeSize(jsnode, expand_level-1);
            //
            var to_expand = getExpandStatus(root);
            if(2 == to_expand){ // 必须先判断收缩状态
                // 不展开
                tree_with_size.expand_status = 2;// 1 是展开,显示 - 号，强制显示子节点; 0 是默认不确定， 2是收缩显示加号
                tree_with_size.hideNodeCount += 1;
            } else if(1 == to_expand || expand_level > 1){
                // 记录下子节点是否展开
                tree_with_size.expand_status = 1; // 1 是展开,显示 - 号，强制显示子节点; 0 是默认不确定， 2是收缩显示加号
                //
                subnodes.push(snode);
                snode.pnode = root;
            } else {
                tree_with_size.expand_status = 2;// 1 是展开,显示 - 号，强制显示子节点; 0 是默认不确定， 2是收缩显示加号
                tree_with_size.hideNodeCount += 1;
            }
        }
        //
        var subNodesSize = subnodes.length;
        var direction = global.config.direction;
        var marginp = global.config.margin_parent;
        var margins = global.config.margin_partner;
        //
        var spanX = 0;
        var spanY = 0;
        // 计算占用宽高
        for (var i = 0; i < subNodesSize;  i++) {
            var snode = subnodes[i];
            //
            spanX += snode.spanX;
            spanY += snode.spanY;
            //
            // 判断方向,计算与子节点的跨度
            if(0 == direction){
                spanY += marginp;
            } else {
                spanX += marginp;
            }
            //
            if(0 == i){
                continue;
            }
            // 判断方向,计算子节点之间的跨度
            if(0 == direction){
                spanX += margins;
            } else {
                spanY += margins;
            }
        }
        // 修正没有子节点的情况
        if(subNodesSize < 1){
            //
            spanX = tree_with_size.width;
            spanY = tree_with_size.height;

        }

        //
        tree_with_size.jsnode = root;
        tree_with_size.subnodes = subnodes;
        tree_with_size.spanX = spanX;
        tree_with_size.spanY = spanY;
        //
        return tree_with_size;
    };
    function calcXY(tree_with_size, startX, startY, expand_level){
        if(!tree_with_size){
            return null;
        }
        //
        startX = startX || global.config.left_paper;
        startY = startY || global.config.top_paper;
        //
        var direction = global.config.direction;
        var defw = tree_with_size.width || global.config.width_dept;
        var defh = tree_with_size.height || global.config.height_dept;
        var marginp = global.config.margin_parent;
        var margins = global.config.margin_partner;

        //
        var sx = startX;
        var sy = startY;
        //
        if(direction){
            sx += defw + marginp;
        } else {
            sy += defh + marginp ;
        }
        // 迭代遍历, 如果还有子元素,则遍历子元素
        var subnodes = tree_with_size.subnodes;
        for (var i = 0; i < subnodes.length;  i++) {
            var node = subnodes[i];
            // 先计算节点, 再加下一个节点的值
            calcXY(node, sx, sy);
            // 修补
            // 算法要改变. 根据前一个元素累加, 因为不规则子树
            var spanX_sub = node.spanX;
            var spanY_sub = node.spanY;
            if(direction){
                sy += (spanY_sub + margins);
            } else {
                sx += (spanX_sub + margins);
            }
        }
        //
        // 设置自身的
        tree_with_size.x = startX;
        tree_with_size.y = startY;
        //
        var spanX = tree_with_size.spanX;
        var spanY = tree_with_size.spanY;
        // 根据跨度修正
        if(0 == direction){
            tree_with_size.x += spanX/2;
        } else {
            tree_with_size.y += spanY/2;
        }

        //
        return tree_with_size;
    };
    function drawTree(paper, tree_with_xy, expand_level){
        //
        //
        var curnode = drawDept(paper, tree_with_xy);
        //
        // 遍历,挨个绘制
        var subnodes = tree_with_xy.subnodes;
        for (var i = 0; i < subnodes.length;  i++) {
            var node = subnodes[i];
            // 迭代遍历, 如果还有子元素,则遍历子元素
            drawTree(paper, node);
        }
        //
        // 设置连线
        drawDeptConnectLine(paper, tree_with_xy);
        //
        return tree_with_xy;
    };
    //
    function drawDeptConnectLine(paper, pnode){
        //
        var config = global.config;

        var subnodes = pnode.subnodes;
        for (var i = 0; i < subnodes.length;  i++) {
            var snode = subnodes[i];
            // 迭代遍历, 如果还有子元素,则遍历子元素
            paper.connectDept(pnode.rect, snode.rect, config, config.line_color)
        }
    };

    //
    function dbclickHandler(e, x, y){
        //
        var datanode = this.datanode || {};
        var jsnode = datanode.jsnode;
        var treeObj = $.fn.zTree.getZTreeObj("gsTree");
        var children = treeObj.getNodesByParam("pId", treeObj.getNodes()[0].id, null);

        //
        var pnode = datanode.pnode;
        if(pnode){
            //
            if(children && children.length){
                // 尝试显示自身为 root
                //showDeptImage(jsnode);
                //return;
            }
        }
        //
        var parent = treeObj.getNodeByParam("id", jsnode.pId, null);
        var subnodes = datanode.subnodes;
        if(subnodes && subnodes.length){
            //有子元素,则返回上一级
            if(parent && parent.text){
                //showDeptImage(parent);
                //return;
            }
        }
        var url=$state.href("depres.depresponse",{id:jsnode.id},{location:false});
        $window.open(url,'_blank');

    };


    // 该节点的子节点是否展开
    function getExpandStatus(node){
        //
        var result = 0;
        // 展开状态
        if(!node){
            result = 0; // 错误情况
        } else if(node.treenode && node.treenode.to_expand_status){
            result = node.treenode.to_expand_status; //记忆中的关闭
        } else if(node.to_expand_status){ // 1,2
            result = node.to_expand_status; //记忆中的关闭
        } else if(global.config.expand_all){
            result = 1; // 全局展开
        } else if(2 == node.expand_status){
            result = node.expand_status; // 这个没用
        } else if(node.treenode){ // 记忆中的展开
            result = node.treenode.to_expand_status || 0;
        }
        //
        return result;
    };
    // 展开所有节点状态
    function expandAllNodeStatus(checked){
        //
        global.config.expand_level = checked ? 100 : 2;
        global.config.expand_all = checked ? 1 : 0;
        //
        if(!checked){
            // 选中,那就没什么事了
            return;
        }
        // 否则:
        // 去除所有节点的记忆状态
        var root = currentCacheDept();
        clear_to_expand_status(root);
        //
    };
    //
    function clear_to_expand_status(node){
        //
        if(!node){
            return;
        }
        if(node.treenode){
            node.treenode.to_expand_status = 0;
        }
        if(node.to_expand_status){
            node.to_expand_status = 0;
        }
        // 循环子节点
        var children = node.children;
        if(!children){
            return;
        }
        //
        for(var i=0; i< children.length; i++){
            clear_to_expand_status(children[i]);
        }
    };


    // 显示部门信息
    function showDeptImage(node){
        //
        if(!node){
            return;
        }
        var paper = global.paper;
        currentCacheDept(node);
        //
        // 清空旧有的元素
        paper.clear();

        //
        // 6. 重置一些值
        //global.config.prevposition=null;
        //global.config.downposition=null;
        //global.config.offset = {x: 0, y:0};
        //
        //
        // 这是设置基础形状,需要进行封装
        //var shape = drawDept(paper, node);
        drawDeptTree(paper, node);
        //
        loadRaphaelProgressBar();
    };
    //

    // 刷新paper的zoom
    function refreshPaperZoom(){
        //
        var paper = global.paper;
        var zoomNum = global.config.zoom_num;
        var offset = 10 - zoomNum;
        zoomNum = 10 + offset;
        //
        var width = paper.width;
        var height = paper.height;
        //
        var nw = width * zoomNum /10;
        var nh = height * zoomNum / 10;
        //
        var x = global.config.offset.x || 0;
        var y = global.config.offset.y || 0;
        fixZoomAndOffset();
        var fit = false;
        //
        paper.setViewBox(x, y,nw, nh, fit);
        //
        $(".transient").remove();
        // 修正大小和倍数
        function fixZoomAndOffset(){
            //
            var tree_with_xy = global.tree_with_xy;
            var offset = global.config.offset;
            var width_dept = global.config.width_dept;
            var height_dept = global.config.height_dept;
            //
            var ox = (tree_with_xy.x + width_dept/2 - offset.x)* (10-zoomNum) /10;
            var oy = (tree_with_xy.y - offset.y)* (10-zoomNum) /10;
            // 计算 根元素相对偏移了多少距离 //
            //
            x += ox; //
            y += oy;
            //debug(offset, ox, oy,zoomNum);
        };
    };
    //
    function currentCacheDept(node){
        if(node){
            global.currentCacheDept = node;
        }
        return global.currentCacheDept;
    };

    function refreshDeptTree(){
        newPaper();
        var node = currentCacheDept();
        showDeptImage(node);

    };

    // 加载 Raphael
    function loadRaphael(holderid) {
        //
        var holder = document.getElementById(holderid);
        //
        newPaper(holder);

        // 然后就完了。
        // 等着触发事件. 然后绘制相应的图形
    };
    //
    function newPaper(holder){
        holder = holder || global.holder; // 缓存
        global.holder = holder;
        //
        var $holder = $(holder);
        //
        var _width = $holder.width();
        var _height = $holder.height();
        //
        if(_width > global.config.min_paper_width){
            global.config.min_paper_width = _width;
        }
        if(_height > global.config.min_paper_height){
            global.config.min_paper_height = _height;
        }


        var width = global.config.min_paper_width;
        var height = global.config.min_paper_height;
        //
        if(global.paper){
            global.paper.clear();
            if(global.svg){
                $(global.svg).remove();
            }
        }
        // 清空旧元素
        $holder.empty();
        // paper 画纸。
        var paper = new Raphael(holder, width, height);
        // 持有
        global.paper = paper;
        global.svg = paper ? paper.canvas : null;
    };

    // 创建进度条
    function loadRaphaelProgressBar() {
        //
        var holder1 = document.getElementById("holder1");
        //
        var value = global.config.zoom_num;
        var maxvalue = 16;
        var minvalue = 3;
        var c = {
            value : value
            , maxvalue : maxvalue
            , minvalue : minvalue
            , element : holder1
            , onchange : function(value){
                // 先保存旧的值
                var o_value = global.config.zoom_num;
                //
                global.config.zoom_num = value;
                if(needRebuild()){
                    cacheZoom2Offset();
                    return refreshDeptTree();
                } else {
                    // 普通情况
                    refreshPaperZoom();
                }
                // 是否需要重新构建
                function needRebuild(){ // TODO
                    //
                    var zoom_num_dept = global.config.zoom_num_dept;
                    var zoom_num_emp = global.config.zoom_num_emp;
                    var old_value = 20 - o_value;
                    var value = 20 - global.config.zoom_num;
                    // 触动阀值
                    if(old_value < zoom_num_dept && value >= zoom_num_dept){
                        return true;
                    } else if(old_value >= zoom_num_dept && value < zoom_num_dept){
                        return true;
                    }
                    // 触动阀值
                    if(old_value < zoom_num_emp && value >= zoom_num_emp){
                        return true;
                    } else if(old_value >= zoom_num_emp && value < zoom_num_emp){
                        return true;
                    }
                    return false;
                };
            }
        };
        var pbar = new ScaleBar(c);
        pbar.init();
        global.pbar = pbar;
    };

    // 将svg保存为png
    function saveSVGToPNG(imgId, callback) {
        //
        var canvas = document.getElementById("tempcanvas");

        var img = document.getElementById(imgId);
        //
        //load a svg snippet in the canvas//
        var svg = global.svg;
        var paper = global.paper;
        if(!svg){
            return "";
        }
        paper && paper.setViewBox(0, 0,paper.width, paper.height, false);
        canvas.width = paper.width;
        canvas.height = paper.height;
        // 修改了源码,将文本重复问题解决
        // 异步方法
        canvg(canvas, svg.outerHTML, {
            renderCallback : function(){
                var imgData = canvas.toDataURL('image/jpg');
                var image = new Image();
                $(image).load(function(){
                    $("#drawing_area").html("");
                    $(img).appendTo("#drawing_area");
                });
                image.src = imgData;
                //
                img.src = imgData;
                //
                callback && callback(imgData);
            }
        });
        return "";
    };


    //
    function bindEvents() {
        //
        var $holder = $("#holder");
        //
        var $export_image = $("#btn_export_image");
        var $btn_direction_toggle = $("#btn_direction_toggle");
        var $checkbox_expand_all = $("#checkbox_expand_all");
        var $btn_fullscreen = $("#btn_fullscreen");
        var $btn_deptshow = $("#btn_deptshow");
        var $btn_save_showconfig = $("#btn_save_showconfig");
        var $savedimg_anchor = $("#savedimg_anchor");
        var $popup_saveimage_area = $("#popup_saveimage_area");
        var $btn_close_popup = $("#btn_close_popup");
        var $savefile = $("#savefile"); // 用来
        //
        $export_image.click(function() {
            //
            var imgSrc = saveSVGToPNG("savedimg", callback) ;
            function callback(imgSrc){
                var fileName = "组织架构图_" + new Date().getTime();
                //
                $popup_saveimage_area.removeClass("hide");
                //
                $savedimg_anchor.attr("href", imgSrc);
                $savedimg_anchor.attr("download", "" + fileName + ".png");
            };
        });

        //
        $checkbox_expand_all.click(function() {
            //
            var checked = $checkbox_expand_all.attr('checked') || $checkbox_expand_all.prop("checked");
            //
            expandAllNodeStatus(checked);
            // 刷新部门树 ...
            refreshDeptTree();
        });
        //
        $btn_direction_toggle.click(function() {
            //
            var direction = global.config.direction;
            var text_btn = direction ?  "纵向显示" : "横向显示";
            if(0 === direction){
                global.config.direction = 1;
            } else {
                global.config.direction = 0;
            }
            //
            $btn_direction_toggle.text(text_btn);
            // 刷新部门树 ...
            refreshDeptTree();
        });
        //
        var preFullWH = null;
        $btn_fullscreen.click(function () {
            if ($.util.supportsFullScreen) {
                if ($.util.isFullScreen()) {
                    $.util.cancelFullScreen();
                    if(preFullWH){
                        var w = preFullWH.w;
                        var h = preFullWH.h;
                        //
                        $holder.width(w);
                        $holder.height(h);
                    }
                } else {
                    //alert("else");
                    //
                    var $holder = $("#holder");
                    var w_h = wh("holder");

                    // 获取窗口宽高
                    var w = $(window).width();
                    var h = $(window).height();
                    //
                    w = window.screen.width;
                    h = window.screen.height;
                    debug("w="+w, "h="+h);
                    //
                    $holder.width(w);
                    $holder.height(h);
                    global.config.min_paper_width = w;
                    global.config.min_paper_height = h;
                    // 暂存
                    preFullWH = w_h;
                    //
                    $.util.requestFullScreen("#holder");
                    global.config.convert_fullscreen=1;
                    refreshDeptTree();
                }
            } else {
                $.easyui.messager.show("当前浏览器不支持全屏 API，请更换至最新的 Chrome/Firefox/Safari 浏览器或通过 F11 快捷键进行操作。");
            }
        });

        $(document).on('webkitfullscreenchange mozfullscreenchange fullscreenchange', function(e) {
            var isFullScreen= $.util.isFullScreen();
            // 刚进入全屏
            if(isFullScreen){
                //
                return;
            } else {
                // 退出全屏
                handlerExitFullScreen();
            }
        });
        //
        function handlerExitFullScreen(){
            //
            if (false == $.util.supportsFullScreen) {
                return;
            }
            //
            //
            var isFullScreen= $.util.isFullScreen();
            // 已进入全屏
            if(isFullScreen){
                //
                return;
            }
            //
            if(preFullWH){
                var w = preFullWH.w;
                var h = preFullWH.h;
                //
                $holder.width(w);
                $holder.height(h);
                global.config.min_paper_width = w;
                global.config.min_paper_height = h;
            }
            //global.config.left_paper = 100;
            //global.config.offset = {x: 0, y:0};
            //global.config.prevRootXY = null;
            //
            refreshDeptTree();
        };

        //
        $btn_deptshow.click(function (e) {
            //
            var showmgrtitle = global.config.show_mgr_title ? true: false;
            var showmgrname = global.config.show_mgr_name ? true: false;

            var $showconfig = $("#showconfig_area");
            //
            var pos = $(this).offset();
            //
            var _left = pos.left + 15;
            var _top = pos.top + 40;
            //
            $showconfig.css({
                "top" : _top + "px",
                "left": _left + "px"
            });
            $showconfig.removeClass("hide");

            // 设置值
            var $showmgrtitle = $("#showmgrtitle");
            var $showmgrname = $("#showmgrname");
            //
            if($showmgrtitle.prop){
                $showmgrtitle.prop("checked", showmgrtitle);
            } else {
                $showmgrtitle.attr("checked", showmgrtitle);
            }
            if($showmgrname.prop){
                $showmgrname.prop("checked", showmgrname);
            } else {
                $showmgrname.attr("checked", showmgrname);
            }
            //
        });
        $btn_save_showconfig.click(function (e) {
            // TODO
            var $showconfig = $("#showconfig_area");
            //
            var $showmgrtitle = $("#showmgrtitle");
            var $showmgrname = $("#showmgrname");
            //
            var showmgrtitle = $showmgrtitle.prop("checked") || $showmgrtitle.attr("checked");
            var showmgrname = $showmgrname.prop("checked") || $showmgrname.attr("checked");
            //
            if(showmgrtitle){
                global.config.show_mgr_title=1;
            } else {
                global.config.show_mgr_title=0;
            }
            if(showmgrname){
                global.config.show_mgr_name=1;
            } else {
                global.config.show_mgr_name=0;
            }
            //
            $showconfig.addClass("hide");
            // 刷新部门树 ...
            refreshDeptTree();
        });

        // 闭包内的函数
        function hidePopUp(){
            $popup_saveimage_area.addClass("hide");
        };
        //
        function processZoom(zoomUp){
            //
            var zoomNum = global.config.zoom_num;
            //
            if(zoomUp){
                // 放大
                zoomNum ++; // 这是相反的
            } else {
                zoomNum --; // 这是相反的
            }
            //
            if(zoomNum < 3){
                zoomNum = 3;
            } else if(zoomNum > 25){
                zoomNum = 25;
            }
            //
            if(global.pbar && global.pbar.val){
                global.pbar.val(zoomNum);
            } else {
                //
                global.config.zoom_num = zoomNum;
                refreshPaperZoom();
            }
        };
        //
        $btn_close_popup.click(function(){
            hidePopUp();
        });

        // 监听键盘按键
        $(document).keyup(function (e) {
            //
            var WHICH_ESC = 27;
            var WHICH_UP = 38;
            var WHICH_DOWN = 40;
            //
            var which = e.which;

            // 监听ESC键
            if (which === WHICH_ESC) {
                /** 这里编写当ESC时的处理逻辑！ */
                hidePopUp();
                return stopEvent(e);
            }
            /**  CTRL + ??? 的情况  */
            if(e.ctrlKey){
                // 监听鼠标滚轮.  CTRL + Up/Down 作为快捷键
                if (which === WHICH_UP) {
                    // Ctrl + Up
                    processZoom(1);
                    return stopEvent(e);
                } else if (which === WHICH_DOWN) {
                    // Ctrl + Down
                    processZoom(0);
                    return stopEvent(e);
                }
            }
        });

        // 只监听 holder. 是否应该监听 svg元素? 先不管
        var $svg = $(global.svg);
        // 监听2个位置,依靠阻止事件传播
        global.svg && $svg.bind('mousewheel', mouseWheelHandler);
        $holder.bind('mousewheel', mouseWheelHandler);
        //
        // 监听鼠标滚轮.  CTRL + Up/Down 作为快捷键
        function mouseWheelHandler(event, delta, deltaX, deltaY) {
            //
            if(event.ctrlKey){
                return; // Ctrl 键则取消
            }
            // 是否向上滚动
            var zoomUp = delta > 0 ? 1 : 0;

            processZoom(zoomUp);
            //
            return stopEvent(event);
        };

        // 全局
        $(document).mouseup(function(e){ // 放开鼠标
            global.config.prevposition=null;
            global.config.downposition=null;
        });
        $holder.mousedown(function(e){ // 按下鼠标
            //
            var screenX = e.screenX;
            var screenY = e.screenY;
            //
            var downposition = {
                screenX : screenX
                ,screenY: screenY
            };
            //
            global.config.downposition=( downposition);
        }).mouseup(function(e){ // 放开鼠标
            global.config.prevposition=null;
            global.config.downposition=null;
        }).mouseout(function(e){ // 鼠标离开
            //global.config.downposition=null;
        }).mouseleave(function(e){ // 鼠标离开
            //global.config.downposition=null;
        }).mousemove(function(e){ // 鼠标移动
            //
            var screenX = e.screenX;
            var screenY = e.screenY;
            //
            var current = {
                screenX : screenX
                ,screenY: screenY
            };
            //
            var prevposition = global.config.prevposition;
            var downposition = global.config.downposition;
            if(!downposition){
                return;
            }
            if(!prevposition || !prevposition.screenX){
                prevposition = downposition;
            }
            //
            if(!prevposition || !prevposition.screenX){
                return; // 没有按下什么,返回
            }
            //
            var pX = prevposition.screenX;
            var pY = prevposition.screenY;
            //
            var deltaX = pX - screenX;
            var deltaY = pY - screenY;
            //
            var min = 5;
            if(deltaX > min || deltaX < -1*min
                || deltaY > min || deltaY < -1*min){
                // 移动超过 min 个像素

                var delta = {
                    deltaX : deltaX
                    , deltaY : deltaY
                };
                dragRaphael(delta);
                //
                global.config.prevposition=( current);
            }
        });

        //
        function dragRaphael(delta){
            if(!delta){
                return;
            }
            //
            var paper = global.paper;
            //
            var width = paper.width;
            var height = paper.height;
            //
            var x = global.config.offset.x || 0;
            var y = global.config.offset.y || 0;
            //
            x += delta.deltaX;
            y += delta.deltaY;
            // 判断x, y的合理性
            var times = 0.7;
            //
            if(x < -1*width*times){
                x =  -1*width*times;
            }
            if(x > width*times){
                x =  width*times;
            }
            if(y < -1*height*times){
                y =  -1*height*times;
            }
            if(y > height*times){
                y =  height*times;
            }
            //
            global.config.offset.x = x;
            global.config.offset.y = y;
            var fit = false;
            //
            refreshPaperZoom();
        };
    }; // end of bindEvents

    //
    function getorginfo_tree(){
        var $orginfo_tree = $('#orginfo_tree');
        //
        return $orginfo_tree;
    };
    //
    function currentTree(){
        var $orginfo_tree = getorginfo_tree();
        return $orginfo_tree.jstree();
    };

    // 请参考: http://www.jstree.com/api/#/?q=.jstree%20Event
    function loadJsTree() {
        var $orginfo_tree = $('#gsTree');
        // 绑定选择事件; select_node.jstree
        $orginfo_tree.on('select_node.jstree', function(e, data) {
            var node = data.node;
            showDeptImage(node);
        });
    }
    // 初始化页面JS调用
    function pageInit() {
        var holderid = "holder";
        //
        try{
            // 加载 Raphael
            loadRaphael(holderid);
            //
        } catch(ex){
            debug(ex);
        }
        try{
            // 加载 Tree
            loadJsTree();
        } catch(ex){
            debug(ex);
        }
        // 绑定各种自定义事件
        bindEvents();
    };
//////////////////////////////////////////////////////////////////////////////////////
///////// 工具函数
//////////////////////////////////////////////////////////////////////////////////////

// 调试
    function debug(obj) {
        // 只适用于具有console的浏览器
        if(!window["console"]){  return;	}
        var params = Array.prototype.slice.call(arguments, 0);
        for(var i=0; i < params.length; i++){
            if ("object" === typeof params[i] ) {
                window["console"]["dir"](params[i]);
            } else {
                window["console"]["info"](params[i]);
            }
        }
    };

// 停止事件.
    function stopEvent(e){
        if(!e){
            return;
        }
        if(e.stopPropagation){
            e.stopPropagation();
        }
        if(e.preventDefault){
            e.preventDefault();
        }
        //
        return false;
    };


// 扩展 Raphael.fn, 成为插件； 绘制连接线。
// 定制，专门画组织结构的线
// 可以只传递1个参数,则这个参数就是现成的线条
// (线条)
// (父节点, 子节点, direction, 线条色, 线条内部填充色)
    Raphael.fn.connectDept = function(pnode, snode, config, lineORcolor, bgColor) {
        // 方向
        var direction = config.direction || 0 ;
        var marginp = config.margin_parent;
        var margins = config.margin_partner;
        var exp_radius = config.exp_radius;
        // 取得颜色
        //var color = typeof lineORcolor == "string" ? lineORcolor : "#000";
        var color = Raphael.color(config.color);
        //
        // 如果传入的第一个元素符合 linePath 的特征,则表示是一条线,将相关参数进行变换
        if (pnode.linePath && pnode.from && pnode.to) {
            lineORcolor = pnode;
            pnode = lineORcolor.from;
            snode = lineORcolor.to;
        }
        // 返回该元素的边界框
        var bboxP = pnode.getBBox();
        var bboxS = snode.getBBox();
        //
        // 上下左右.
        var pUp = {
            x : bboxP.x + bboxP.width / 2,
            y : bboxP.y - 1
        };
        var pDown = {
            x : bboxP.x + bboxP.width / 2,
            y : bboxP.y + bboxP.height + 12
        };
        var pLeft = {
            x : bboxP.x - 1,
            y : bboxP.y + bboxP.height / 2
        };
        var pRight = {
            x : bboxP.x + bboxP.width + 12,
            y : bboxP.y + bboxP.height / 2
        };
        //
        var sUp = {
            x : bboxS.x + bboxS.width / 2,
            y : bboxS.y - 1
        };
        var sDown = {
            x : bboxS.x + bboxS.width / 2,
            y : bboxS.y + bboxS.height + 1
        };
        var sLeft = {
            x : bboxS.x - 1,
            y : bboxS.y + bboxS.height / 2
        };
        var sRight = {
            x : bboxS.x + bboxS.width + 1,
            y : bboxS.y + bboxS.height / 2
        }

        //////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////
        //
        // 简单绘制,只需要4个点
        // Number.toFixed(dn); 在数字小数点 后面补dn个0
        if(0 == direction){
            var pStart  = pDown;
            var pStart  = {
                x : pDown.x + 1,
                y : pDown.y + exp_radius/3 + 1
            };
            var pBreak  = {
                x : pStart.x,
                y : pStart.y + marginp/2 - exp_radius
            };
            var sEnd = sUp;
            var sEnd = {
                x: sUp.x + 1,
                y: sUp.y
            };
            var sBreak = {
                x : sEnd.x,
                y : pBreak.y  // y 保持一致
            };
        } else {
            var pStart  = pRight;
            var pStart  = {
                x : pRight.x + + exp_radius/3 + 1,
                y : pRight.y
            };
            var pBreak  = {
                x : pStart.x + marginp/2 - exp_radius,
                y : pStart.y
            };
            var sEnd = sLeft;
            var sBreak = {
                x : pBreak.x , // x 保持一致
                y : sEnd.y
            };
        }
        var path0 = ["M", pStart.x.toFixed(3), pStart.y.toFixed(3),
            "C", pBreak.x.toFixed(3), pBreak.y.toFixed(3),
            sBreak.x.toFixed(3), sBreak.y.toFixed(3),
            sEnd.x.toFixed(3), sEnd.y.toFixed(3)
        ].join(",");

        var path = ["M", pStart.x.toFixed(3), pStart.y.toFixed(3),
            "L", pBreak.x.toFixed(3), pBreak.y.toFixed(3),
            "L", sBreak.x.toFixed(3), sBreak.y.toFixed(3),
            "L", sEnd.x.toFixed(3), sEnd.y.toFixed(3)
        ].join(",");

        // 判断,是新绘制,还是使用已有的线条和背景
        if (lineORcolor && lineORcolor.linePath) {
            lineORcolor.bgPath && lineORcolor.bgPath.attr({
                path : path, "stroke-width": 2, "stroke-linecap": "round"
            });
            lineORcolor.linePath.attr({
                path : path, "stroke-width": 2, "stroke-linecap": "round"
            });
        } else {
            return {
                bgPath : bgColor && bgColor.split && this.path(path).attr({
                    stroke : bgColor.split("|")[0], // 背景
                    fill : "none",
                    "stroke-width" : 2 //bgColor.split("|")[1] || 3 // 背景宽度
                }),
                linePath : this.path(path).attr({
                    stroke : color,
                    fill : "none"
                    ,"stroke-width": 2, "stroke-linecap": "round"
                }),
                from : pnode,
                to : snode
            };
        }
    };


// 获取宽高
    function wh(id){
        if(!id){
            return {};
        }
        return {
            w : $("#"+id).width(),
            h : $("#"+id).height()
        };
    };

// 文字样式不允许选择
    function unselect(element){
        if(!element || !element.node || !element.attr){return element;}
        var style = element.node.style || {};
        style.unselectable = "on";
        style.MozUserSelect =  "none";
        style.WebkitUserSelect= "none";
        //
        element.attr({
            "font-family": "microsoft yahei",
            cursor : "default"
        });
        return element;
    };

    function iconcursor(element){
        if(!element || !element.attr){return element;}
        element.attr(
            {
                cursor : "pointer"
                , stroke: "none"
            });
        return element;
    };


}]);

