<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>史上最精简，最强大的JS遮罩层效果，支持ie firefox jQuery遮罩层</title>
<script type="text/javascript" src="http://www.94this.com.cn/myCode/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
//显示灰色JS遮罩层
function showBg(ct,content){
	var bH=$("body").height();
	var bW=$("body").width()+16;
	var objWH=getObjWh(ct);
	$("#fullbg").css({width:bW,height:bH,display:"block"});
	var tbT=objWH.split("|")[0]+"px";
	var tbL=objWH.split("|")[1]+"px";
	$("#"+ct).css({top:tbT,left:tbL,display:"block"});
	$("#"+content).html("<div style='text-align:center'>正在加载，请稍后...</div>");
	$(window).scroll(function(){resetBg()});
	$(window).resize(function(){resetBg()});
}
function getObjWh(obj){
	var st=document.documentElement.scrollTop;//滚动条距顶部的距离
	var sl=document.documentElement.scrollLeft;//滚动条距左边的距离
	var ch=document.documentElement.clientHeight;//屏幕的高度
	var cw=document.documentElement.clientWidth;//屏幕的宽度
	var objH=$("#"+obj).height();//浮动对象的高度
	var objW=$("#"+obj).width();//浮动对象的宽度
	var objT=Number(st)+(Number(ch)-Number(objH))/2;
	var objL=Number(sl)+(Number(cw)-Number(objW))/2;
	return objT+"|"+objL;
}
function resetBg(){
	var fullbg=$("#fullbg").css("display");
	if(fullbg=="block"){
		var bH2=$("body").height();
		var bW2=$("body").width()+16;
		$("#fullbg").css({width:bW2,height:bH2});
		var objV=getObjWh("dialog");
		var tbT=objV.split("|")[0]+"px";
		var tbL=objV.split("|")[1]+"px";
		$("#dialog").css({top:tbT,left:tbL});
	}
}

//关闭灰色JS遮罩层和操作窗口
function closeBg(){
	$("#fullbg").css("display","none");
	$("#dialog").css("display","none");
}

</script>
<style type="text/css">
*{
font-family:Arial, Helvetica, sans-serif;
font-size:12px;
}
#fullbg{
background-color: Gray;
display:none;
z-index:3;
position:absolute;
left:0px;
top:0px;
filter:Alpha(Opacity=30);
/* IE */
-moz-opacity:0.4;
/* Moz + FF */
opacity: 0.4;
}

#dialog {
position:absolute;
width:200px;
height:200px;
background:#F00;
display: none;
z-index: 5;
}

#main {
height: 1500px;
}
</style>
</head>
<body>
<div id="main">
<a href="#rhis" onclick="showBg('dialog','dialog_content');">点击这里看JS遮罩层效果</a>
</div>
<!-- JS遮罩层 -->
<div id="fullbg"></div>
<!-- end JS遮罩层 -->
<!-- 对话框 -->
<div id="dialog">
<div id="dialog_content"></div>
<div style="text-align: center;"><a href="#" onclick="closeBg();">关闭</a></div>
</div>
<!-- JS遮罩层上方的对话框 -->
</body>
</html>
