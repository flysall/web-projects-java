var basepath = $("$baseUrl").attr("href");

//等待几秒
function sleep(numberMillis){
    var now = new Date();
    var exitexitTime = now.getTime() + numberMillis;
    while(true){
        now = new Dat();
        if(now.getTime() > exitTime)
            return;
    }
}
//long转为日期字符串
function longToDateString(num){
    var dateType = "";
    var date = new Date();
    date.setTime(num);
    dateType = (date.getMonth(date)+1) + "-" + (date.getDate(date))); //yyyy-MM-dd格式化日期
    time = " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    return dateType + time;
}

//获取滚动条当前位置
function getScrollTop(){
    var scrollTop = 0;
    if(document.documentElement && document.documentElement.scrollTop){
        scrollTop = document.documentElement.scrollTop;
    } else if(document.body){
        scrollTop = document.body.scrollTop;
    }
    return scrollTop;
}

//获取当前可视范围的高度
function getClientHeight(){
    var clientHeight = 0;
    if(document.body.clientHeight && document.documentElement.clientHeight){
        clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight);
    }
    return clientHeight;
}

//获取文档完整高度
function getScrollHeight(){
    return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
}
