<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%=request.getAttribute("message")==null?"":request.getAttribute("message") %>
<form action="/day14_shopping/loginServlet" onsubmit="return check()" method="post">			
		<table id="mtb" align="center" border="1px" cellpadding="0px" cellspacing="0px" width="80%">
				
					<caption>用户登录</caption>
				
				<tr id="tr1"
					onmouseover="change(this.id,'over')";
					onmouseout="change(this.id,'out')";
					>
					<td width="50%" align="center">用户名</td>
					<td width="50%" >
						<input type="text" id="ue" name="username"/>
						<span id="sp1"></span>
					</td>
				</tr>
				<tr
					id="tr2"
					onmouseover="change(this.id,'over')";
					onmouseout="change(this.id,'out')";>
					<td width="50%" align="center">密码</td>
					<td width="50%" >
						<input type="password" id="pd" name="password"/>
						<span id="sp2"></span>
					</td>
				</tr>
				<tr width="50%"
				id="tr3"
					onmouseover="change(this.id,'over')";
					onmouseout="change(this.id,'out')";>
					<td width="50%" align="center">验证码</td>
					<td width="50%" align="center">
						<input type="text" name="checkcode" width="50%">
						<img src="/day14_shopping/checkImgServlet" onclick="changeImg(this)" width="50%"/>
					</td>
				</tr>
				<tr width="50%"
					id="tr4"
					onmouseover="change(this.id,'over')";
					onmouseout="change(this.id,'out')";>
					<td colspan="2" width="100%">
						<table width="100%">
							<tr width="100%">
								<td width="33%" align="right">
									<input type="submit" value="登录"/>
						
								</td>	
								<td width="33%" align="middle">
									<input type="reset"  value="重置"/>
								</td>
								<td width="33%" align="left"> 
										<input type="button"  value="返回"/>
								</td>
							</tr>
						</table>
					</td>					
				</tr>				
			</table>
		</form>	
</body>
<script>	
		function check(){
			var username=document.getElementById("ue");
			var password=document.getElementById("pd");				
			var ueV=username.value;
			var pdV=password.value; 
			var sp1=document.getElementById("sp1");
			var sp2=document.getElementById("sp2");
			sp1.innerHTML="";
			sp2.innerHTML="";
			if(ueV==null||ueV==""){
				sp1.innerHTML="<font color='red'>用户名不能为空</font>";
				return false;
			}
			if(pdV==null||pdV==""){
				sp2.innerHTML="<font color='red'>密码不能为空</font>";
				return false;
			}			
		}
		
		function change(id,info){
			var tr=document.getElementById(id);
			if(info=="over"){
				tr.style.backgroundColor="blueviolet";
			}
			if(info=="out"){
				tr.style.backgroundColor="white";
			}
		}
		function changeImg(obj){
			obj.src="/day14_shopping/checkImgServlet?time="+new Date().getTime();
		}
	</script>
</html>