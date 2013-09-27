var request = false;  
var btype=getInternet();    
function getInternet()      
{      
if(navigator.userAgent.indexOf("MSIE")>0) {      
              return "MSIE";       //IE浏览器    
 }    
 if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){      
              return "Firefox";     //Firefox浏览器    
 }    
 if(isSafari=navigator.userAgent.indexOf("Safari")>0) {      
              return "Safari";      //Safan浏览器    
 }    
 if(isCamino=navigator.userAgent.indexOf("Camino")>0){      
              return "Camino";   //Camino浏览器    
}    
 if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){      
              return "Gecko";    //Gecko浏览器    
 }      
}     
try {  
  request = new XMLHttpRequest();  
} catch (trymicrosoft) {  
  try {  
    request = new ActiveXObject("Msxml2.XMLHTTP");//支持microsoft  
  } catch (othermicrosoft) {  
    try {  
      request = new ActiveXObject("Microsoft.XMLHTTP");//支持非microsoft  
    } catch (failed) {  
      request = false;    
    }    
 }  
}  
  
if(!request)  
  alert("Error!!游览器不安全,请选择较高版本游览器!");  
//调用的ajax 事件  
function getInfo() {
alert('111');
  var url = "siteStatus/getJsonSiteStatus.action";  

  request.open("post", url, true);//发送请求  
   if(btype=="Firefox"){  //firefox 的回调设置  
         request.onreadystatechange = function(){    
         showData();    
    }; 
    request.send(null);
    request.onreadystatechange = function(){    
    showData();    
  };     
  }else{//其他的回调设置{这里只考虑到了ie，其他的能用这个算碰对了呗}    
     request.onreadystatechange = showData;    
     request.send(null);    
   }   
}  
  
function showData(){  
  if(request.readyState == 4){//响应完成  
         if (request.status == 200){//正常运行  
            var result=request.responseText;//获得返回数据字符串  
        //---对result处理就可以了  
             //***************************demo star***********************//  
        //-----获得下拉框对象  
            document.getElementById('labelShow').innerHTML="<font style='#f90'>"+result+"</font>";
    //***************************end*********************//  
        }else if (request.status == 404)//未找到请求  
        alert("Request URL 无法找到!!");  
    else  
      alert("Error:异常：编号为:" + request.status);  
     }  
} 