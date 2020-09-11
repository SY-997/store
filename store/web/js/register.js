var flag=false;//设置一个全局变量，用于表单是否正确填写
$(function () {

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
                 if (data==1){
                     $("#nametips").html("该用户已被注册!").css("color","#ff0006");
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

     //检测密码
     $("#inputPassword3").bind('blur keyup',function () {
         var password=$(this).val();
         var flagPass=isNumberAndLettr(password);
         if (flagPass){
             $("#passtips").html("");
             //只有上一个是true，后面才能改为true
             if (flag){
                 flag=true;
             }
         }else {
             $("#passtips").html("密码长度6-16位，仅支持英文字母和数字和_!-*./+@?组成").css("color","#ff0006");
             flag=false;
         }
     });
     //确认密码
     $("#confirmpwd").blur(function () {
         var password1=$(this).val();
         var password2=$("#inputPassword3").val();
         if (password1==password2){
             //只有上一个是true，后面才能改为true
             if (flag){
                 flag=true;

             }
             $("#passtips1").html("");
         }else {
             flag=false;
             $("#passtips1").html("密码不一致！").css("color","#ff0006");

         }
     });

     //邮箱检验
     $("#inputEmail3").blur(function () {
         var email=$(this).val();
         var flagEmail=checkEmail(email);
         if (flagEmail){
             //只有上一个是true，后面才能改为true
             if (flag){
                 flag=true;
             }
             $("#emailtips").html("");
         }else {
             $("#emailtips").html("请填写真实邮箱,以便接收激活邮件").css("color","#ff0006");
             flag=false;
         }


     });

     //切换验证码
     $("#changeCode").click(function () {
         $.get("/store/UserServlet?method=codeimg",
             function (data) {
                 if (data!=null){
                     console.log(data);
                     $("#codeImg").prop("src",data);
                 }
             });
     });

     //提交时再次验证
     $("#sub").click(function () {
        checkname();
         $("#inputPassword3").triggerHandler("blur");
         $("#confirmpwd").triggerHandler("blur");
         $("#inputEmail3").triggerHandler("blur");
         if (flag){
             $("form[name='form01']").submit();
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
            if (data==1){
                $("#nametips").html("该用户已被注册!").css("color","#ff0006");
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