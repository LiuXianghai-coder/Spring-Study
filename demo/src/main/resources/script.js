setTimeout(function () {
    let xhr = createXHR();
    // 请求数据的处理结果的状态监听
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            // 检查相应码与相应内容
            if (xhr.status >= 200 && xhr.status < 300) {
                console.log("处理 DOM 内容成功")
            } else {
                console.log("处理 DOM 内容失败, 状态码：" + xhr.status);
            }
        }
    };

    xhr.open("post", "http://127.0.0.1:8080/view/dom", false);
    // 获取所有的 Div 标签元素， 因为所有的信息都包含在对应的 Div 盒子中， 其它的信息都不是必要的
    let obj = document.getElementsByTagName("html");

    // 将得到的所有 Div 标签元素转变为对应的列表
    let elementList = Array.from(obj);

    // 最终得到的 DOM 的内容
    let stringObj = String();

    // 列表索引计数
    let i;
    for (i = 0; i < elementList.length; ++i) {
        stringObj += elementList[i].outerHTML;
    }

    /* 向服务器发送的数据， 服务端会接受名为 “dom” 的数据，
       因此 DOM 数据需要设置对应的标签， 因此设置对应的对象属性来完成这一操作。
     */
    let transferData = {};
    transferData["serialNo"] = "test";
    transferData["data"] = stringObj;

    // 设置发送信息的主机信息， 这里是本地主机 127.0.0.1
    xhr.setRequestHeader("Host", "127.0.0.1");
    // 设置传入的数据的类型， 这里是 “application/json”
    xhr.setRequestHeader("Content-Type", "application/json");
    /* 设置 XHR 的跨域资源共享， 应当设置为对应的服务地址的URL，
        但是由于 Desktop 的 IP 地址的变化， 暂且设置为所有的地址都能访问， 即为 '*'
     */
    xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
    // 发送得到的数据体对象
    xhr.send(JSON.stringify(transferData));

    window.close();
}, 2500);

/*
    原生 JS 创建 XMLHttpRequest 对象 实现 Ajax
 */
function createXHR() {
    if (typeof XMLHttpRequest != "undefined") {
        return new XMLHttpRequest();
    } else if (typeof ActiveXObject != "undefined") {
        let versions = ["MSXML2.XMLHttp.6.0", "MSXML2.XMLHttp.3.0", "MSXML2.XMLHttp"];
        let i, len;

        for (i = 0, len = versions.length; i < len; ++i) {
            try {
                new ActiveXObject(versions[i]);
                arguments.callee.activeXString = versions[i];
                break;
            } catch (ex) {
                console.log("The " + versions[i] + "can't use Ajax");
            }
        }

        return new ActiveXObject(arguments.callee.activeXString);
    } else {
        throw new Error("这个浏览器没救了， 赶紧换一个吧。");
    }
}