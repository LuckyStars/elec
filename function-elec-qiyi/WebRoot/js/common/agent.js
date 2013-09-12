function sethash() {

   var proxySrc = 'http://127.0.0.1:8080/schoolapp/common/agent.html';   //设置门户的服务器地址
   
   var hashH = document.documentElement.scrollHeight;
   var frame =document.createElement("iframe");
   frame.src = proxySrc+'#'+hashH;
   frame.style.display="none";
   frame.style.width="0";
   frame.style.height="0";
   document.body.appendChild(frame);
}
if (document.all){
	window.attachEvent('onload',sethash);// IE
}
else{
	window.addEventListener('load',sethash,false);// firefox
}
