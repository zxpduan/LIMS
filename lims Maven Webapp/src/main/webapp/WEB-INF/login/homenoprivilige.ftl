 <script type="text/javascript">
 	 alert("无权登录，请联系管理员！");     
     if (window!=top){    
    	window.top.location.href='login_face.action'; 
     }else{    
     	window.location.href='login_face.action';
     }
   </script>