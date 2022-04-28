let diff = JSON.parse('{"indexList#1.page":{"oldVal":2,"newVal":100},' +
    '"indexList#0.chapterName":{"oldVal":"comics","newVal":"algorithm"},' +
    '"bookName":{"oldVal":"APUE","newVal":"CSAPP"},"indexList#1.chapterName"' +
    ':{"oldVal":"introduction","newVal":"performance"},"indexList#0.page":' +
    '{"oldVal":10,"newVal":50},"authors":{"oldVal":[{"name":"Ken Thomson",' +
    '"age":65,"describe":"Unix Invented","education":{"name":"BSD","level":"PHD"}},' +
    '{"name":"Richard","age":44,"describe":"Unix Professor","education":{"name":"MIT",' +
    '"level":"MASTER"}}],"newVal":[{"name":"Ritchie Denis","age":44,"describe":' +
    '"C Programmer","education":{"name":"MIT","level":"PHD"}},' +
    '{"name":"Brain Kenihan","age":55,"describe":"Awk Programmer",' +
    '"education":{"name":"CMU","level":"PHD"}}]}}');

let obj = JSON.parse('{"indexList":[{"chapterName":"comics","page":10},' +
    '{"chapterName":"introduction","page":2}],"bookName":"APUE","authors":' +
    '[{"name":"Richard","age":44,"describe":"Unix Professor","education":{"name":"MIT","level":"MASTER"}},' +
    '{"name":"Ken Thomson","age":65,"describe":"Unix Invented","education":{"name":"BSD","level":"PHD"}}],"flag":10}');

const listRegex = new RegExp("[^#]+#(\\d+)");
let diffKeys = Object.keys(diff);

for (let i = 0; i < diffKeys.length; ++i) {
    let key = diffKeys[i];
    let fs = key.split(".");

    dfs(obj, fs, 0, diff[key]);
}

console.log(obj);

function dfs(curObj, fs, idx, diffVal) {
    if (idx >= fs.length) return;
    if (idx === fs.length - 1) {
        curObj[fs[idx]] = diffVal;
        return;
    }

    if (listRegex.test(fs[idx])) {
        let arr = fs[idx].split("#");
        curObj = curObj[arr[0]][arr[1]];
    } else {
        curObj = curObj[fs[idx]];
    }

    dfs(curObj, fs, idx + 1, diffVal);
}
