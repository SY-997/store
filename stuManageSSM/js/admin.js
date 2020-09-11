var count=0;//完成人数
$(function(){
	//获取改变当前任务事件
	$("#c_thing").click(function(){
		var thing=$("#t_thing2 option:selected").text()
		var t_id=$("#t_thing2").val();
		$("#t_text").html(thing);
	});
	//确认改变当前任务
	$("#set_tid").click(function(){
		var tid=$("#t_thing2").val();
		window.location.href = "admin/setTid/"+tid;
	});

	//新加任务
	$("#t_bt").click(function(){
		var tname=$("#t_name").val();
		if(tname.length<2){
			alert("任务名太短！");
		}else{
			window.location.href = "admin/addTask?tname="+tname;
		}

		console.log(tname);
	});

	//名字查询
	$("#findname").click(function(){
		var name=$("#inputname").val();
		if(name.length!=0){
			$("tr:has(td)").css("display","none");

			$("tr:contains('"+name+"')").css("display","");
		}

	});

	//查询选中任务情况
	$("#t_thing1").change(function(){
		var time=new Date().getTime();
		var tid=this.value;
		var Url="admin/infoSelectByTid?tid="+tid+"&time="+time;
		$.ajax({
			url: Url, //请求的url地址
			dataType: "json", //返回格式为json
			async: true, //请求是否异步，默认为异步，这也是ajax重要特性
			type: "get", //请求方式
			success: function(req) {
				count=0;
				$.each(req,function(i,arr){
					var id=arr.id;
					var flag=arr.flag;
					var str1='<input type="checkbox" checked="checked" disabled="disabled"  value="'+id+'"/>';
					var str0='<input type="checkbox"  name="unchecked" disabled="disabled" value="'+id+'"/>';
					if(flag==1){
						count++;
						$("#tab td[name='"+id+"']").html(str1);
						$("#change_"+id).html('<button type="button" onclick="change('+id+','+flag+')"  class="btn btn-warning">更改</button>');
					}else{
						$("#tab td[name='"+id+"']").html(str0);
						$("#change_"+id).html('<button type="button" onclick="change('+id+','+flag+')"  class="btn btn-warning">更改</button>');
					}

				});
				$("#count").html(count+"人");
				$("#inlineRadio1").prop("checked","checked");
				//显示全部
				$("tr").css("display","");
			},

			error: function() {
				//请求出错处理
				console.log("请求失败");
			}
		});
	});

	$("#inlineRadio1").change(function(){
		//显示全部
		$("tr").css("display","");
	})
	$("#inlineRadio1").click(function(){
		//显示全部
		$("tr").css("display","");
	})
	$("#inlineRadio2").change(function(){
		//未完成
		$("tr:has(input[type='checkbox'][checked='checked'])").css("display","none");
		$("tr:has(input[type='checkbox'][name='unchecked'])").css("display","");
	})
	$("#inlineRadio3").change(function(){
		//完成
		$("tr:has(input[type='checkbox'][checked='checked'])").css("display","");
		$("tr:has(input[type='checkbox'][name='unchecked'])").css("display","none");
	})
});





//用于更改选框后刷新
function update(tid){
	var time=new Date().getTime();
	var Url="admin/infoSelectByTid?tid="+tid+"&time="+time;
	$.ajax({
		url: Url, //请求的url地址
		dataType: "json", //返回格式为json
		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
		type: "get", //请求方式

		success: function(req) {
			count=0;
			$.each(req,function(i,arr){
				var id=arr.id;
				var flag=arr.flag;
				var str1='<input type="checkbox" checked="checked" disabled="disabled"  value="'+id+'"/>';
				var str0='<input type="checkbox"  name="unchecked" disabled="disabled" value="'+id+'"/>';
				if(flag==1){
					count++;
					$("#tab td[name='"+id+"']").html(str1);
					$("#change_"+id).html('<button type="button" onclick="change('+id+','+flag+')"  class="btn btn-warning">更改</button>');

				}else{
					$("#tab td[name='"+id+"']").html(str0);
					$("#change_"+id).html('<button type="button" onclick="change('+id+','+flag+')"  class="btn btn-warning">更改</button>');
				}

			});
			$("#count").html(count+"人");
			$("#inlineRadio1").prop("checked","checked");
			//显示全部
			$("tr").css("display","");
		},

		error: function() {
			//请求出错处理
			console.log("请求失败");
		}
	});
}
//改变选中的状态
function change(id,flg){
	var tid=$("#t_thing1").val();
	if (flg==0){
		flg=1;
	}else {
		flg=0;
	}
	var Url="admin/updateTaskFinished?uid="+id+"&tid="+tid+"&flag="+flg;

	$.ajax({
		url: Url, //请求的url地址
		dataType: "json", //返回格式为json
		async: true, //请求是否异步，默认为异步，这也是ajax重要特性
		type: "get", //请求方式
		success: function(req) {
			if(req.Flag==1){
				$("#span_"+id).prop("class","glyphicon glyphicon-ok");
				setTimeout(function(){$("#span_"+id).prop("class","");},5000);
				update(tid);
				console.log("改变成功");
			}else{
				$("#span_"+id).prop("class","glyphicon glyphicon-remove");
				setTimeout(function(){
					$("#span_"+id).prop("class","");
				},5000);
				console.log("改变失败");
			}

		},
		error: function() {
			//请求出错处理
			$("#span_"+id).prop("class","glyphicon glyphicon-remove");
			setTimeout(function(){
				$("#span_"+id).prop("class","");
			},5000);
			console.log("请求失败");
		}
	});
}



var Url=window.location.href;
function timestamp(url){
	//  var getTimestamp=Math.random();
	var getTimestamp=new Date().getTime();
	if(url.indexOf("?")>-1){
		url=url.substr(0,url.indexOf("?"))+"?timestamp="+getTimestamp
	}else{
		url=url+"?timestamp="+getTimestamp
	}
	return url;
}
//网页URL添加时间戳
window.history.pushState({},0,timestamp(Url));
console.log(window.location.href);