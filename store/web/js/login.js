let flag = false;
$(function () {

    //检测注册用户名是否存在
    $("#username").bind('blur keyup',function () {
        flag=false;
        var username=$(this).val();
        if (username.length<3){
            $("#nametips").html("用户名长度3~15位，仅支持英文字母和数字和下划线").css("color","#ff0006");
            return;
        }
        $.ajax({
            url: "/store/UserServlet?method=checkUsername&username="+username,    //请求的url地址
            dataType: "html",   //返回格式为json
            async: true, //请求是否异步，默认为异步，这也是ajax重要特性
            type: "get",   //请求方式
            success: function(data) {
                console.log(data);
                if (data!=1){
                    $("#nametips").html("该用户不存在!").css("color","#ff0006");
                    flag=false;

                }else{
                    $("#nametips").html("");
                    flag=true;
                }
            },
            error: function() {
                //请求出错处理
                console.log("请求查询失败");
                flag=false;
            },
        });


    });


    //切换验证码
    $("#changeCode").click(function () {
        $.get("/store/UserServlet?method=codeimg",
            function (data) {
                if (data!=null){
                    console.log(data);
                  $("#codeImg").prop("src",data);
             //     $("#codeImg").prop("src","/store/img/code/15913441758303.jpg");
                }
            });


    });
    //提交时再次验证
    $("#submit02").click(function () {
        checkname();
        if (flag){
            $("#form02").submit();
        }
    });

});

//提交时再次验证
function checkname() {
    flag=false;
    var username=$("#username").val();
    if (username.length<3){
        $("#nametips").html("用户名长度3~15位，仅支持英文字母和数字和下划线").css("color","#ff0006");
        return;
    }
    $.ajax({
        url: "/store/UserServlet?method=checkUsername&username="+username,    //请求的url地址
        dataType: "html",   //返回格式为json
        async: false, //请求是否异步，默认为异步，这也是ajax重要特性
        type: "get",   //请求方式
        success: function(data) {
            console.log(data);
            if (data!=1){
                $("#nametips").html("该用户不存在!").css("color","#ff0006");
                flag=false;
            }else{
                $("#nametips").html("");
                flag=true;
            }
        },
        error: function() {
            //请求出错处理
            console.log("请求查询失败");
            flag=false;
        },
    });

}