<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录学生管理系统</title>
<link href="/static/css/style.css" rel="stylesheet" type="text/css" />
</head>
<body  >
    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  
    <div class="logintop">
        <span>欢迎登录学生管理界面</span>
        <ul>
            <li><a href="login.jsp">回首页</a></li>
            <li><a href="#">帮助</a></li>
            <li><a href="#">关于</a></li>
        </ul>
    </div>
    <div class="loginbody">
        <span class="systemlogo"></span>
        <div class="loginbox">


            <ul>

                <li><input name="mail" id="mail" type="text" class="loginuser"  /></li>
                <li>
                    <input name="code" id="ucode" type="text" class="loginpwd"  />
                    <input  id="code" type="hidden" />
                    <button onclick="sendMail()" class="loginbtn0">获取验证码</button>
                </li>
                <li>
                    <input name="" type="button" class="loginbtn" value="登录"  onclick="submitForm()"/><label>
                    <input name="" type="checkbox" value="" checked="checked" />记住密码</label><label><a href="#">忘记密码</a></label>
                </li>

            </ul>
        </div>
    </div>
    <div class="loginbm">版权所有  Xinghuo4  4</div>

    <script language="JavaScript" src="/static/js/jquery.js"></script>
    <script src="/static/js/cloud.js" type="text/javascript"></script>
    <script language="javascript">
        $(function(){
            $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            $(window).resize(function(){
                $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
            })
        });

        function  sendMail() {
           var code  =document.getElementById("code");

            var ajax = new XMLHttpRequest();
            ajax.open('post', 'mail' );
            ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            //发送请求
            ajax.send(null);
            ajax.onreadystatechange = function () {
                if(ajax.readyState==4){
                    var flag = (ajax.responseText);
  code.value =flag;

  alert(code.value)
     }
            }
   }
   
   
   function  submitForm() {
       var mail  =document.getElementById("mail").value;
       var code  =document.getElementById("code").value;
       var ucode  =document.getElementById("ucode").value;
       alert(mail);
       alert(code); alert(ucode);


       var ajax = new XMLHttpRequest();
       ajax.open('post', 'mailLogin' );
       ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
       //发送请求
       ajax.send("mail="+mail+"&code="+code+"&ucode="+ucode);
       ajax.onreadystatechange = function () {
           if(ajax.readyState==4){
               var flag = ajax.responseText;
                 alert(flag);
               if (flag=="true"){
                   window.location.href="http://localhost:8080/index";
               }



           }
       }


   }

    </script>
    <script src="/static/js/login.js"></script>
</body>
</html>
