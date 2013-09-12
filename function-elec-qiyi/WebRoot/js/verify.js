/**
 * 一。数字类验证
 * isNegative(str) 判断是否为负数
 * isInt(str) : 判断是否为整数，正负均可
 * isValidNum(num, length, decimal)  检查数字规范 length:总长度 decimal：小数点后位数
 * 
 * 二。字符类验证
 * isSpace(str) 是否为空
 * isValidStr(t, invalidStr) 检测是否含有非法字符
 * isChinese(str)  输入为中文判断
 * 
 * 三。时间类验证
 * isDateTime(str) 长时间，形如 (2003-12-05 13:04:06 或 2003/12/05 13:04:06)
 * format  按yyyy-MM-dd hh:mm:ss格式化时间对象
 * dateToStr(date, formatStr)	日期对象转换为指定格式的字符串，格式 "yyyy-MM-dd HH:mm:ss w"
 * strToDate(dateStr)	字符串转换为日期对象，dateStr格式为yyyy-MM-dd HH:mm:ss，必须按年月日时分秒的顺序，分隔符不限制可为 -或/； 另外字符串也可以只包括 年月日，不用全部
 * compareDate(dateStr1, dateStr2)	比较两个时间大小。 true：前者大于后者，false ：前者小于或等于后者
 * 
 * 四。综合类验证
 * isBrowser(vision) 浏览器类型，参数固定：IE6.0、IE7.0、IE8.0、FF
 * isFileExtension(filePath) 文件扩展名验证（图片）
 * html2char(source) 字符转义
 */
/** ******************************* 数字类验证 ********************************* */
/**
 * 判断是否为负数
 */
function isNegative(str) {
	return /^(\-)(\d|\.)+$/.test(str);
}

/**
 * 判断是否为整数，正负均可
 */
function isInt(str) {
	return /^(-|\+)?\d+$/.test(str);
}

/**
 * 检查数字规范 length:总长度 decimal：小数点后位数
 */
function isValidNum(num, length, decimal){
    if(num == "") return false;
	
    //1.检查是否为数字
    for(var i = 0; i < num.length; ++i){
        var ch = num.charAt(i);
        if(!(ch >= '0' && ch <= '9') && ch != '.')
            return false;
    }
    if(num.indexOf('.') != num.lastIndexOf('.'))
        return false;
	
    //2.检查总长度
    if(length != undefined){
        if(num.length > length)
            return false;
    }
	
    //3.检查小数位数
    if(decimal != undefined){
        if(num.indexOf('.')!=-1){
            if(num.length - 1 - num.indexOf('.') > decimal)
                return false;
        }
    }
	
    return true;
}

/** ******************************* 字符类验证 ********************************* */
/**
 * 是否为空
 */
function isSpace(str){
	return /^\s*$/g.test(str);
}

/**
 * 检测是否含有非法字符
 * @param {Object} inValidArr 非法字符串，[可空]
 */
function isValidStr(t, invalidStr) {
	// 默认非法字符串
	var szMsg = "[\\`=\[]'/<>\"{}|*&^%$#@《》?？!！。.+-～()（）]";
	if(invalidStr != undefined){
		szMsg = invalidStr;
	}
	
	var result = true;
	for (i = 1; i < szMsg.length + 1; i++) {
		if (t.indexOf(szMsg.substring(i - 1, i)) > -1) {
			result = false;
			break;
		}
	}
	return result;
}

/**
 * 输入为中文判断
 */
function isChinese(str){
	var pattern=/[^\x00-\xff]/g;
	return pattern.test(str);
}

/** ******************************* 日期验证 ********************************* */
/**
 * 长时间，形如 (2003-12-05 13:04:06 或 2003/12/05 13:04:06)
 */
function isDateTime(str){
    var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var r = str.match(reg);
	if(r == null) return false;
	var d = new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
	return (d.getFullYear() == r[1]&&(d.getMonth()+1) == r[3]&&d.getDate() == r[4]&&d.getHours() == r[5]&&d.getMinutes() == r[6]&&d.getSeconds() == r[7]);
}

/**  
 * 时间对象的格式化，若缺省，则为yyyy-MM-dd HH:mm:ss
 */
Date.prototype.format = function(format){
	var formatStr = arguments[0] || "yyyy-MM-dd HH:mm:ss";
	return dateToStr(this, formatStr);
};


/**
 * 日期对象转换为指定格式的字符串
 * @param date Date日期对象, 如果缺省或null，则为当前时间
 * @param f 日期格式,格式定义如下 "yyyy-MM-dd HH:mm:ss w"
 * --------------------------------------------------
 * YYYY/yyyy/YY/yy 表示年份  
 * MM/M 月份  
 * W/w 星期  
 * dd/DD/d/D 日期  
 * hh/HH/h/H 时间  
 * mm/m 分钟  
 * ss/SS/s/S 秒  
 * --------------------------------------------------
 */
function dateToStr(date, formatStr){
    var date = arguments[0] || new Date();
    var formatStr = arguments[1] || "yyyy-MM-dd HH:mm:ss";
    
    var str = formatStr;   
    var Week = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];  
    str=str.replace(/yyyy|YYYY/,date.getFullYear());   
    str=str.replace(/yy|YY/,(date.getYear() % 100)>9?(date.getYear() % 100).toString():'0' + (date.getYear() % 100));   
    str=str.replace(/MM/,date.getMonth()>9?(date.getMonth() + 1):'0' + (date.getMonth() + 1));   
    str=str.replace(/M/g,date.getMonth());   
    str=str.replace(/w|W/g,Week[date.getDay()]);   
  
    str=str.replace(/dd|DD/,date.getDate()>9?date.getDate().toString():'0' + date.getDate());   
    str=str.replace(/d|D/g,date.getDate());   
  
    str=str.replace(/hh|HH/,date.getHours()>9?date.getHours().toString():'0' + date.getHours());   
    str=str.replace(/h|H/g,date.getHours());   
    str=str.replace(/mm/,date.getMinutes()>9?date.getMinutes().toString():'0' + date.getMinutes());   
    str=str.replace(/m/g,date.getMinutes());   
  
    str=str.replace(/ss|SS/,date.getSeconds()>9?date.getSeconds().toString():'0' + date.getSeconds());   
    str=str.replace(/s|S/g,date.getSeconds());   
  
    return str;   
}


/**
 * 字符串转换为日期对象
 * dateStr格式为yyyy-MM-dd HH:mm:ss，必须按年月日时分秒的顺序，分隔符不限制可为 -或/； 另外字符串也可以只包括 年月日，不用全部
 */
function strToDate(dateStr){
    var data = dateStr;  
    var reCat = /(\d{1,4})/gm;   
    var t = data.match(reCat);
    t[1] = t[1] - 1;
    eval('var d = new Date('+t.join(',')+');');
    return d;
}

/**
 * 传入似于yyyy-MM-dd 时间字符串，比较两个时间大小。 true：前者大于后者，false ：前者小于或等于后者
 */
function compareDate(dateStr1, dateStr2) {
	var d1 = strToDate(dateStr1);
	var d2 = strToDate(dateStr2);
	return d1 > d2;
}


/** ******************************* 综合类验证 ********************************* */
/**
 * 浏览器类型	  例如: isBrowser("FF")
 * @param {Object} vision 参数固定：IE6.0、IE7.0、IE8.0、FF
 */
function isBrowser(vision) {
    if (typeof vision == 'undefiend')
        return false;
    vision = vision.toUpperCase();
	
	var ua = navigator.userAgent.toLowerCase();
    if (window.ActiveXObject) {
		return vision === 'IE'+ ua.match(/msie ([\d.]+)/)[1];
	}else{
		return vision === 'FF';
	}
}

/**
 * 文件扩展名验证（图片）
 */
function isFileExtension(filePath){
	var patten = /\.(jpe?g|png|gif)$/i; //忽略大小写
	return patten.test(filePath);
}

/**
 * 字符转义
 */
function html2char(source){
	return String(source)
			.replace(/&/g,'&amp;')
            .replace(/</g,'&lt;')
            .replace(/>/g,'&gt;')
            .replace(/\\/g,'&#92;')
            .replace(/"/g,'&quot;')
            .replace(/'/g,'&#39;');
}