/**
 * 回车焦点到下一个输入框，没有页面中的好用，比如contract下entering.html页面中
 * @param theForm
 */
function focusInputText(theForm) {
	$("#" + theForm + " input:text:first").focus();
	var $inp = jQuery("#" + theForm + " input:text");
	$inp.bind("keydown", function(e) {
		var key = e.which;
		if (key == 13) {
			e.preventDefault();
			var nextIds = $inp.index(this) + 1;
			$("#" + theForm + " input:text:eq(" + nextIds + ")").focus();
		}

	});
}
/**
 * 显示简单对话框
 * @param title
 * @param contentId
 * @param callback 回调函数
 * @param width 对话框宽度
 */

function showSimpleDialogByKevin(title, contentId,callback,width) {
	var dialog = $("#" + contentId).removeClass('hide').dialog({
	width:width?width:400,
	modal : true,
	title : "<div class='widget-header widget-header-small'><h4 class='smaller'><i class='ace-icon fa fa-check'></i>" + title + "</h4></div>",
	title_html : true,
	buttons : [ {
	text : "取消",
	"class" : "btn btn-xs",
	click : function() {
		$(this).dialog("close");
	}
	}, {
	text : "确定修改",
	"class" : "btn btn-primary btn-xs",
	click : function() {
		if (callback) callback(dialog);
		else{
			$(this).dialog("close");
		}	
		
	}
	} ]
	});
}
/**
 * 显示简单确认框
 * @param msg
 * @param okcallback
 * @param cancelcallback
 */
function showConfirmDialogByKevin(msg, okcallback, cancelcallback) {

	$.confirm({
	title : '确认消息',
	content : msg,
	buttons : {
	confirm : {
	text : '确定',
	action : function() {
		if (okcallback)
			okcallback();
	}
	},
	cancel : {
	text : '取消',
	action : function() {
		if (cancelcallback)
			cancelcallback();
	}
	}
	}
	});

}
/**
 * 显示简单消息或警告框
 * @param msg
 * @param title
 */
function showMsgByKevin(msg,title) {
	$.confirm({
	title : title?title:'警告',
	content : msg,
	buttons : {
		cancel : {
			text : '确定'
		}
	}
	});
}
// ///////////lowcase to uppercase
function atoc(numberValue) {
	var numberValue = new String(Math.round(numberValue * 100)); // 数字金额
	var chineseValue = ""; // 转换后的汉字金额
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
	var len = numberValue.length; // numberValue 的字符串长度
	var Ch1; // 数字的汉语读法
	var Ch2; // 数字位的汉字读法
	var nZero = 0; // 用来计算连续的零值的个数
	var String3; // 指定位置的数值
	if (len > 15) {
		alert("超出计算范围");
		return "";
	}
	if (numberValue == 0) {
		chineseValue = "零元整";
		return chineseValue;
	}
	String2 = String2.substr(String2.length - len, len); // 取出对应位数的STRING2的值
	for (var i = 0; i < len; i++) {
		String3 = parseInt(numberValue.substr(i, 1), 10); // 取出需转换的某一位的值
		if (i != (len - 3) && i != (len - 7) && i != (len - 11) && i != (len - 15)) {
			if (String3 == 0) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			}
		} else { // 该位是万亿，亿，万，元位等关键位
			if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 != 0 && nZero == 0) {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 == 0 && nZero >= 3) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else {
				Ch1 = "";
				Ch2 = String2.substr(i, 1);
				nZero = nZero + 1;
			}
			if (i == (len - 11) || i == (len - 3)) { // 如果该位是亿位或元位，则必须写上
				Ch2 = String2.substr(i, 1);
			}
		}
		chineseValue = chineseValue + Ch1 + Ch2;
	}
	if (String3 == 0) { // 最后一位（分）为0时，加上“整”
		chineseValue = chineseValue + "整";
	}
	return chineseValue;
}
// //////////////////date lowcase to uppercase
// ////////////datestr format is yyyy-mm-dd
function dateL2U(datestr) {
	var len = datestr.length;
	if (len == 0)
		return "";
	var o = datestr.split("-");
	var String1 = "零一二三四五六七八九十"; // 汉字数字
	var String2 = "0123456789";
	var reStr = "";
	var tem = "", tem1 = 0;
	var i_loc = -1, s1 = 0, s2 = 0; // s1 个位，s2十位
	for (var k = 0; k < o.length; k++) {
		reStr = ""

		if (k == 0) {
			for (var i = 0; i < o[k].length; i++) {
				tem = o[k].substr(i, 1);
				i_loc = String2.indexOf(tem);
				if (i_loc != -1) {
					reStr += String1.substr(i_loc, 1);
				} else {
					reStr += tem;
				}
			}
		} else {
			tem1 = o[k] * 1;
			s1 = Math.floor(tem1 / 10);
			s2 = tem1 % 10;
			console.log(s1 + ":" + s2);
			if (s1 == 0) {
				reStr += String1.substr(s2, 1);
			} else if (s1 == 1) {
				reStr += "十" + String1.substr(s2, 1);
			} else {
				reStr += String1.substr(s1, 1) + "十" + String1.substr(s2, 1);
			}
		}
		o[k] = reStr;
	}

	return o[0] + "年" + o[1] + "月" + o[2] + "日";
}
(function($) {
	$.fn.openWidow = function(options) {
		var divId = "dialog" + Math.round(Math.random() * 100);
		var settings = {
		id : divId,
		width : 300,
		height : 200,
		modal : true,
		buttons : {},
		show : "explode",
		hide : "highlight",
		title : "提示",
		url : "test.html",
		close : function() {
			$("#" + this.id).remove();
			// debugger
			if (document.getElementById(this.id))
				document.body.removeChild(document.getElementById(this.id));
		}
		};
		if (options) {
			$.extend(settings, options);
		}
		$("body").append('<div id="' + settings.id + '" title="Dialog Title" style="width:100%;height:100%;overflow:hidden;margin:10px;background:yellow;"><p class="loading"></p></div>');
		// Dialog
		$('#' + settings.id).dialog({
		autoOpen : false,
		title : settings.title,
		width : settings.width,
		height : settings.height,
		modal : true,
		bgiframe : true,
		show : settings.show,
		hide : settings.hide,
		buttons : settings.buttons,
		close : settings.close,
		open : function() {
			$("#" + settings.id).html('<iframe src="' + settings.url + '" frameborder="0" style="background:green;" height="100%" width="100%" id="dialogFrame" scrolling="auto"></iframe>');
		},
		resizeStop : function() {
			// $("#dialogFrame").css("width", parseInt($(this).css("width")) -
			// 5);
			// $("#dialogFrame").css("height", parseInt($(this).css("height")) -
			// 5);
		}
		});

		$('#' + settings.id).dialog("open");
		return this;
	};
	/*
	 * 方法:Array.remove(dx) 功能:根据元素值删除数组元素. 参数:元素值 返回:在原数组上修改数组
	 */
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) {
				return i;
			}
		}
		return -1;
	};
	Array.prototype.removevalue = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	// 清除两边的空格
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, '');
	};
	// 合并多个空白为一个空白
	String.prototype.ResetBlank = function() {
		var regEx = /\s+/g;
		return this.replace(regEx, ' ');
	};

	// 保留数字
	String.prototype.GetNum = function() {
		var regEx = /[^\d]/g;
		return this.replace(regEx, '');
	};

	// 保留中文
	String.prototype.GetCN = function() {
		var regEx = /[^\u4e00-\u9fa5\uf900-\ufa2d]/g;
		return this.replace(regEx, '');
	};

	// String转化为Number
	String.prototype.ToInt = function() {
		return isNaN(parseInt(this)) ? this.toString() : parseInt(this);
	};

	// 得到字节长度
	String.prototype.GetLen = function() {
		var regEx = /^[\u4e00-\u9fa5\uf900-\ufa2d]+$/;
		if (regEx.test(this)) {
			return this.length * 2;
		} else {
			var oMatches = this.match(/[\x00-\xff]/g);
			var oLength = this.length * 2 - oMatches.length;
			return oLength;
		}
	};

	// 获取文件全名
	String.prototype.GetFileName = function() {
		var regEx = /^.*\/([^\/\?]*).*$/;
		return this.replace(regEx, '$1');
	};

	// 获取文件扩展名
	String.prototype.GetExtensionName = function() {
		var regEx = /^.*\/[^\/]*(\.[^\.\?]*).*$/;
		return this.replace(regEx, '$1');
	};

	// 替换所有
	String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
		if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
			return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")), replaceWith);
		} else {
			return this.replace(reallyDo, replaceWith);
		}
	};
	// 格式化字符串 add By 刘景宁 2010-12-09
	String.Format = function() {
		if (arguments.length == 0) {
			return '';
		}

		if (arguments.length == 1) {
			return arguments[0];
		}

		var reg = /{(\d+)?}/g;
		var args = arguments;
		var result = arguments[0].replace(reg, function($0, $1) {
			return args[parseInt($1) + 1];
		});
		return result;
	};

	// 数字补零
	Number.prototype.LenWithZero = function(oCount) {
		var strText = this.toString();
		while (strText.length < oCount) {
			strText = '0' + strText;
		}
		return strText;
	};

	// Unicode还原
	Number.prototype.ChrW = function() {
		return String.fromCharCode(this);
	};

	// 数字数组由小到大排序
	Array.prototype.Min2Max = function() {
		var oValue;
		for (var i = 0; i < this.length; i++) {
			for (var j = 0; j <= i; j++) {
				if (this[i] < this[j]) {
					oValue = this[i];
					this[i] = this[j];
					this[j] = oValue;
				}
			}
		}
		return this;
	};

	// 数字数组由大到小排序
	Array.prototype.Max2Min = function() {
		var oValue;
		for (var i = 0; i < this.length; i++) {
			for (var j = 0; j <= i; j++) {
				if (this[i] > this[j]) {
					oValue = this[i];
					this[i] = this[j];
					this[j] = oValue;
				}
			}
		}
		return this;
	};

	// 获得数字数组中最大项
	Array.prototype.GetMax = function() {
		var oValue = 0;
		for (var i = 0; i < this.length; i++) {
			if (this[i] > oValue) {
				oValue = this[i];
			}
		}
		return oValue;
	};

	// 获得数字数组中最小项
	Array.prototype.GetMin = function() {
		var oValue = 0;
		for (var i = 0; i < this.length; i++) {
			if (this[i] < oValue) {
				oValue = this[i];
			}
		}
		return oValue;
	};
	/* 
	*  方法:Array.remove(dx) 
	*  功能:删除数组元素. 
	*  参数:dx删除元素的下标. 
	*  返回:在原数组上修改数组. 
	*  splice方法见http://www.w3school.com.cn/js/jsref_slice_array.asp 
	*/
	Array.prototype.remove = function(dx) 
	{ 
	    if(isNaN(dx)||dx>this.length){return false;} 
	    this.splice(dx,1); 
	} 
	Array.prototype.removeV = function(val) 
	{ 
		for (var i = 0; i < this.length; i++) {
			if (this[i].indexOf(val) != -1) {
				this.remove(i);
			}
		}
		return this;
	}
	// 获取当前时间的中文形式
	Date.prototype.GetCNDate = function() {
		var oDateText = '';
		oDateText += this.getFullYear().LenWithZero(4) + new Number(24180).ChrW();
		oDateText += this.getMonth().LenWithZero(2) + new Number(26376).ChrW();
		oDateText += this.getDate().LenWithZero(2) + new Number(26085).ChrW();
		oDateText += this.getHours().LenWithZero(2) + new Number(26102).ChrW();
		oDateText += this.getMinutes().LenWithZero(2) + new Number(20998).ChrW();
		oDateText += this.getSeconds().LenWithZero(2) + new Number(31186).ChrW();
		oDateText += new Number(32).ChrW() + new Number(32).ChrW() + new Number(26143).ChrW() + new Number(26399).ChrW() + new String('26085199682010819977222352011620845').substr(this.getDay() * 5, 5).ToInt().ChrW();
		return oDateText;
	};
	// 获取当前时间,format is yyyy-MM-dd
	Date.prototype.GetNowDate = function() {
		var oDateText = '';
		oDateText += this.getFullYear().LenWithZero(4) + "-";
		oDateText += (this.getMonth()+1).LenWithZero(2) +"-";
		oDateText += this.getDate().LenWithZero(2) 
		return oDateText;
	};
	

})(jQuery);
//为jquery.serializeArray()解决radio,checkbox未选中时没有序列化的问题
$.fn.zxp_serialize = function () {
    var a = this.serializeArray();
    var $radio = $('input[type=radio],input[type=checkbox]', this);
    var temp = {};
    $.each($radio, function () {
        if (!temp.hasOwnProperty(this.name)) {
            if ($("input[name='" + this.name + "']:checked").length == 0) {
                temp[this.name] = "";
                a.push({name: this.name, value: ""});
            }
        }
    });
    //console.log(a);
    return jQuery.param(a);
};