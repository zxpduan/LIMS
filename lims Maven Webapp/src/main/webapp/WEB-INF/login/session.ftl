<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>LIMS</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <script type="text/javascript">
     var longin_address="";
     <#if (loginuri?exists)>
     	<#if (loginuri!="")>
     		longin_address='${loginuri}';
     	<#else>
     		longin_address='login_face.action';
     	</#if>     	
     <#else>
     	longin_address='login_face.action';
     </#if>     
     if (window!=top){    
    	window.top.location.href=longin_address; 
     }else{    
     	window.location.href=longin_address;
     }
    </script>
  </body>
</html>
