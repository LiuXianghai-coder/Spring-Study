let diff = JSON.parse('{"addOp#1&add":{"oldVal":null,"newVal":{"name":"yfj","age":20.0}},' +
    '"modifyOp#0&modify":{"oldVal":{"name":"xhliu","age":22.0},' +
    '"newVal":{"name":"liuxhanghai","age":22.0}},' +
    '"delOp#0&del":{"oldVal":{"name":"xhliu","age":22.0},"newVal":null}}');

let obj = JSON.parse('{"addOp":[{"name":"xhliu","age":22.0},{"name":"yfj","age":20.0}],"modifyOp":' +
    '[{"name":"liuxhanghai","age":22.0},{"name":"yfz","age":20.0}],"delOp":' +
    '[{"name":"yfj","age":21.0},{"name":"aoz","age":23.0}]}');

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
        if (listRegex.test(fs[idx])) {
            let arr = fs[idx].split("#");
            fs[idx] = arr[0];
        }

        curObj[fs[idx]] = diffVal;
        return;
    }

    if (listRegex.test(fs[idx])) {
        let arr = fs[idx].split("#");
        let ops = arr[1].split("&");

        curObj = curObj[arr[0]][ops[0]];
        fs[idx] = arr[0];
    } else {
        curObj = curObj[fs[idx]];
    }

    dfs(curObj, fs, idx + 1, diffVal);
}
