$(function(){
/*************    方法     **************/
	//判断是否是闰年,计算每个月的天数
	function leapYear(year){
		var isLeap = year%100==0 ? (year%400==0 ? 1 : 0) : (year%4==0 ? 1 : 0);
		return new Array(31,28+isLeap,31,30,31,30,31,31,30,31,30,31);
	}

	//获得某月第一天是周几
	function firstDay(day){
		return day.getDay();
	}

	//获得当天的相关日期变量
	function dateNoneParam(){
		var day = new Date();
		var today = new Array();
		today['year'] = day.getFullYear();
		today['month'] = day.getMonth();
		today['date'] = day.getDate();
		today['hour'] = day.getHours();
		today['minute'] = day.getMinutes();
		today['second'] = day.getSeconds();
		today['week'] = day.getDay();
		today['firstDay'] = firstDay(new Date(today['year'],today['month'],1)); 
		return today;
	}

	//获得所选日期的相关变量
	function dateWithParam(year,month){
		var day = new Date(year,month);
		var date = new Array();
		date['year'] = day.getFullYear();
		date['month'] = day.getMonth();
		date['firstDay'] = firstDay(new Date(date['year'],date['month'],1));
		return date;
	}

	//生成日历代码 的方法
	//参数依次为 年 月 日 当月第一天(是星期几)
	function calendarCode(codeYear,codeMonth,codeDay,codeFirst){
		calendar_html = "<table id='calendar'>\n<tr id='select'>\n<td colspan=7>\n<div id='year'>\n<ul>\n<li><input type='button' id='yearPrev' value='<<' /></li>\n<li class='selectChange'>\n<select name='year'>";
		//年 选择
		for(var i=2010;i<=codeYear+yearfloor;i++){
			if(i == codeYear){
				calendar_html += "<option value='"+i+"' selected='true'>"+i+"</option>";
			}else{
				calendar_html += "<option value='"+i+"'>"+i+"</option>";
			}
		}

		calendar_html += "</select>\n</li>\n<li><input type='button' id='yearNext' value='>>' /></li>\n</ul>\n</div>\n<div id='month'>\n<ul>\n<li><input type='button' id='monthPrev' value='<' /></li>\n<li class='selectChange'>\n<select name='year'>";

		//月 选择
		for(var j=0;j<=11;j++){
			if(j == codeMonth){
				calendar_html += "<option value='"+j+"' selected='true'>"+month[j]+"</option>";
			}else{
				calendar_html += "<option value='"+j+"'>"+month[j]+"</option>";
			}
		}

		calendar_html += "</select>\n</li>\n<li><input type='button' id='monthNext' value='>' /></li>\n</ul>\n</div>\n<div id='time'>\n</div>\n</td>\n</tr>\n\n<tr id='week'>\n<td>\n<ul>\n\n<li>星期一</li>\n<li>星期二</li>\n<li>星期三</li>\n<li>星期四</li>\n<li>星期五</li>\n<li class='weekend'>星期六</li><li class='weekend'>星期日</li>\n</ul>\n</td>\n</tr>\n\n<tr id='day'>\n<td colspan=7>\n";

		//日 列表
		for(var m=0;m<6;m++){//日期共 4-6 行
			if(m >= Math.ceil((codeFirst+monthDays[codeMonth])/7)){//第五、六行是否隐藏				
				calendar_html += "<ul id='dayList"+m+"'class='dayList hide dayListHide"+m+"'>\n";
			}else{
				calendar_html += "<ul id='dayList"+m+"'class='dayList dayListHide"+m+"'>\n";
			}	
			for(var n=1;n<=7;n++){//列
				if((7*m+n) < codeFirst || (7*m+n) >= (codeFirst+monthDays[codeMonth])){//某月日历中不存在的日期
					calendar_html += "<li></li>";
				}else{
					if((7*m+n+1-codeFirst == codeDay)&&(((7*m+n)%7 == 0) || ((7*m+n)%7 == 6))){//当天是周末
						calendar_html += "<li class='todayWeekend' id='day"+(7*m+n+1-codeFirst)+"'>"+(7*m+n+1-codeFirst)+"</li>";
					}else if(((7*m+n)%7 == 0) || ((7*m+n)%7 == 6)){//仅是周末
						calendar_html += "<li class='weekend'  id='day"+(7*m+n+1-codeFirst)+"'>"+(7*m+n+1-codeFirst)+"</li>";
					}else if(7*m+n+1-codeFirst == codeDay){//仅是当天
						calendar_html += "<li class='today' id='day"+(7*m+n+1-codeFirst)+"'>"+(7*m+n+1-codeFirst)+"</li>";
					}else{//其他日期
						calendar_html += "<li  id='day"+(7*m+n+1-codeFirst)+"'>"+(7*m+n+1-codeFirst)+"</li>";
					}
				}
			}
			calendar_html += "</ul>\n";
		}
		calendar_html += "</td>\n</tr>\n</table>";
		return calendar_html;
	}

	//年-月select框改变数值 的方法
	//参数依次为 1、操作对象(年下拉菜单 或 月下拉菜单) 2、被选中的下拉菜单值
	function y_mChange(obj,stopId){
		obj.val(stopId);
	}

	//修改日历列表 的方法
	//参数依次为 操作对象(每一天) 月份 修改后的第一天是星期几 修改后的总天数 当天的具体日期
	function dateChange(dateObj,dateMonth,dateFirstDay,dateTotalDays,dateCurrentDay){
		//判断新日历有几行,需要显示或隐藏
		if(dateFirstDay==0)
			dateFirstDay = 7;
		var newLine = Math.ceil((dateFirstDay+monthDays[dateMonth]-1)/7);//新行数
		if(newLine > dateLine){//增加行
			for(var i=dateLine;i<newLine;i++){
				$('.dayListHide'+i).show();
			}
		}else if(newLine < dateLine){//减少行
			for(var i=dateLine-1;i>=newLine;i--){
				$('.dayListHide'+i).hide();
			}
		}
		//重置日期排序
		dateLine = newLine;
		/*如果改变 月 后，选中月的总天数小于当前日期，
		*如当前3.31，选中2月，可2月最多29天，则将当前日期改为选中月的最后一天，即2.39
		*/
		if(dateTotalDays < dateCurrentDay){
			dateCurrentDay = dateTotalDays;
		}
		//刷新所有ID为-1
		dateObj.attr("id","-1");
		for(var i=0;i<7*newLine;i++){
			if(i < dateFirstDay-1 || i> (dateTotalDays+dateFirstDay-2)){//日历中 当月不存在的日期
				dateObj.eq(i).text('').removeClass();
			}else{
				if((i+1-dateFirstDay == dateCurrentDay) && (i%7 == 5 || i%7 == 6)){
					dateObj.eq(i).removeClass().addClass('todayWeekend');
				}else if(i%7 == 5 || i%7 == 6){//仅周末
					dateObj.eq(i).removeClass().addClass('weekend');
				}else if(i+1-dateFirstDay == dateCurrentDay){//仅当天
					dateObj.eq(i).removeClass().addClass('today');
				}else{//其他日期
					dateObj.eq(i).removeClass();
				}
				
				dateObj.eq(i).text(i+2-dateFirstDay);
				//重设ID
				dateObj.eq(i).attr("id","day"+(i+2-dateFirstDay));
				////console.log("i:+"+i);
				////console.log("i+1-dateFirstDay:"+(i+1-dateFirstDay));
			}
		}
	}
	/******************     订单部分     **********************/

	function getJSON(year,month){
		var manage_url = "action/OrderAction!getOrdersByMonth.action?";
		month++;
		$.getJSON(manage_url+"year="+year+"&month="+month,function(json){ 
			json = JSON.parse(json);
			InsertOrder(json);
			staticJSON = json;
    	});
	}
	function InsertOrder(json){
		var list = [];
		for(i = 0 ; i<32 ; i++)
			list[i]=0;//初始化
		for(i = 0 ; i < json.length ; i++)
			list[json[i].order_date.date]+=json[i].client_No;//获取每天总人数
		for(i = 0 ; i < list.length ; i++){//添加
			if(list[i]==0)
				continue;
			var sum = "<p onclick='showOrderByDate("+(yearChange)+","+(monthChange+1)+","+i+")'>共:"+(list[i])+"人</p>";
			$("#day"+i).append(sum);
		}
		/*for(iterator = 0 ; iterator < json.length;iterator++)
		{
			var html = "<p onclick='showOrder("+json[iterator].order_ID+")'>"+json[iterator].client_name+"</p>"
			//console.log("#day"+json[iterator].order_date.date+"输出")
			$("#day"+json[iterator].order_date.date).append(html);
		}*///去掉每天显示内容
	}
	function InsertDayButton(){
		for(i = 0 ; i < 6 ; i++){
			var button  = $("<button onclick='showOrderByDay(yearChange,monthChange+1,"+(i+1)+")'>></button>");
			button.height($("#dayList0").height());
			//var width = $("#dayList0").width()-$("#day1").width()*7-20;
			button.width("5%");
			$("#dayList"+i).append(button);
			//for the fucking IE and Edge
			button.css("width","5%");
		}
	}
	
/*************    缓存节点和变量     **************/
	var rili_location = $('#wrap');//日历代码的位置
	var calendar_html = '';//记录日历自身代码 的变量
	var yearfloor = 10;//选择年份从1970到当前时间的后10年

	var someDay = dateNoneParam();//修改后的某一天,默认是当天
	/* 修改为全局变量  */
	yearChange = someDay['year'];//改变后的年份，默认当年
	monthChange = someDay['month'];//改变后的年份，默认当月
	
	/*************   将日历代码放入相应位置，初始时显示此处内容      **************/

	//获取时间，确定日历显示内容
	var today = dateNoneParam();//当天

	/*月-日 设置*/
	var month = new Array('一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月');
	var monthDays = leapYear(today['year']);//返回数组，记录每月有多少天
	var weekDay = new Array('日','一','二','三','四','五','六');
	// alert('年:'+someDay['year']+'\n月:'+someDay['month']+'\n日:'+someDay['date']+'\n星期:'+someDay['week']+'\n本月第一天星期:'+someDay['firstDay']);return false;

	calendar_html = calendarCode(today['year'],today['month'],today['date'],today['firstDay']);
	rili_location.html(calendar_html);

/*************   js写的日历代码中出现的节点     **************/
	var yearPrev = $('#yearPrev');//上一年按钮
	var yearNext = $('#yearNext');//下一年按钮
	var monthPrev = $('#monthPrev');//上一月按钮
	var monthNext = $('#monthNext');//下一月按钮
	var selectYear = $('#year .selectChange select');//选择年份列表
	var listYear = $('#year .selectChange select option');//所有可选年份
	var selectMonth = $('#month .selectChange select');//选择月份列表
	var listMonth = $('#month .selectChange select option');//所有可选月份
	var dateLine = Math.ceil((monthDays[today['month']]+today['firstDay'])/7);;//当前日历中有几行日期，默认是 当年当月
	var dateDay = $('#calendar tr#day td ul.dayList li');//日历中的每一天


/***************************/
	//年 按钮事件
	yearPrev.bind('click',function(){
		yearChange --;
		if(yearChange < 1970){
			yearChange = 1970;
			alert('太前也没意义了...');
			return false;
		}
		//修改年份
		y_mChange(selectYear,yearChange);
		//重新获得 每月天数
		monthDays = leapYear(yearChange);//alert(monthDays);
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);	
		getJSON(yearChange,monthChange);
	});

	yearNext.bind('click',function(){
		yearChange ++;
		if(yearChange >= today['year']+yearfloor){
			yearChange = today['year']+yearfloor;
			alert('太后也没意义了...');
			return false;
		}
		//修改年份
		y_mChange(selectYear,yearChange);
		//重新获得 每月天数
		monthDays = leapYear(yearChange);//alert(monthDays);
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
		getJSON(yearChange,monthChange);
	});

	// 月 按钮事件
	monthPrev.bind('click',function(){
		monthChange --;
		if(monthChange >= 0){//仍在本年内
			//修改月份
			y_mChange(selectMonth,monthChange);			
		}else{
			monthChange = 11;//前一年的最后一个月
			yearChange --;
			if(yearChange < 2010){
				yearChange = 2010;
				alert('太久远也没意义了...');
				return false;
			}
			//修改月份
			y_mChange(selectMonth,monthChange);
			//修改年份
			y_mChange(selectYear,yearChange);
			//重新获得 每月天数
			monthDays = leapYear(yearChange);
		}
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
		getJSON(yearChange,monthChange);
	});

	monthNext.bind('click',function(){
		monthChange ++;
		if(monthChange <= 11){//仍在本年内
			//修改月份
			y_mChange(selectMonth,monthChange);
		}else{
			monthChange = 0;//下一年的第一个月
			yearChange ++;
			if(yearChange >= someDay['year']+yearfloor){
				yearChange = someDay['year']+yearfloor;
				alert('太久远也没意义了...');
				return false;
			}
			//修改月份
			y_mChange(selectMonth,monthChange);
			//修改年份
			y_mChange(selectYear,yearChange);
			//重新获得 每月天数
			monthDays = leapYear(yearChange);
		}
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
		getJSON(yearChange,monthChange);
	});

	// 年 选择事件
	selectYear.bind('change',function(){
		//获得年份
		yearChange = $(this).val();
		//修改年份
		y_mChange(selectYear,yearChange);
		//重新获得 每月天数
		monthDays = leapYear(yearChange);
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
		getJSON(yearChange,monthChange);
	});

	// 月 选择事件
	selectMonth.bind('change',function(){
		//获得 月
		monthChange = $(this).val();
		//修改月份
		y_mChange(selectMonth,monthChange);
		//新 年-月 下的对象信息
		someDay = dateWithParam(yearChange,monthChange);
		//修改 日期 列表
		dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
		getJSON(yearChange,monthChange);
	});

	/*日 鼠标事件*/
	/*dateDay.hover(function(){
		$(this).addClass('mouseFloat');
	},function(){
		$(this).removeClass('mouseFloat');
	});
	*/
	$.refreshCalendar = function(){
		dateChange(dateDay,someDay['month'],someDay['firstDay'],monthDays[someDay['month']],today['date']);
		getJSON(yearChange,monthChange);
	}
	/*************      获取JSON                **************/
	getJSON(yearChange,monthChange);
	InsertDayButton();
	
});