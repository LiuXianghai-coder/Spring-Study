let newObj = {
    "data": {
        "proj": {
            "data": [
                {
                    "id": "000001",
                    "name": "xhliu",
                    "version": "version-1",
                    "age": 22,
                    "period": [
                        {
                            "id": "040d38eb9b5caf214baf0a",
                            "date_time": "2017-09-11 10:52:27",
                            "des": "第一期次"
                        },
                        {
                            "id": "hh7a536e6eb2ba4hyb17d7",
                            "date_time": "2019-10-11 21:15:17",
                            "des": "第二期次"
                        },
                        {
                            "id": "htgb8c6e6eb2ba468347kl",
                            "date_time": "2018-02-15 09:25:15",
                            "des": "第三期次"
                        },
                        {
                            "id": "hybjpc6e6eb2ba46837pt3",
                            "date_time": "2018-04-16 15:02:19",
                            "des": "第四期次"
                        },
                        {
                            "id": "h8mozc6e6eb2ba467yhbkz",
                            "date_time": "2019-06-18 21:46:08",
                            "des": "第十一期次"
                        }
                    ]
                },
                {
                    "id": "000002",
                    "name": "aozhao",
                    "version": "version-2",
                    "age": 24,
                    "period": [
                        {
                            "id": "8f362181271803abc75qa0",
                            "date_time": "2019-08-21 14:02:37",
                            "des": "第十二期次"
                        },
                        {
                            "id": "d38eb9b5caf214baf049a0",
                            "date_time": "2019-10-16 16:00:07",
                            "des": "第七期次"
                        },
                        {
                            "id": "abc756b4e04e68582b6bb4",
                            "date_time": "2019-12-15 19:15:15",
                            "des": "第八期次"
                        },
                        {
                            "id": "271803abc756b4e04e582",
                            "date_time": "2020-02-06 10:12:39",
                            "des": "第九期次"
                        },
                        {
                            "id": "38eb9b5caf214baf06218",
                            "date_time": "2020-04-08 19:16:18",
                            "des": "第十期次"
                        }
                    ]
                }
            ],
            "type": "人民币",
            "instId": "a665a45920422f9"
        }
    }
};
let diffObj = {
    "data.proj.data#0.period#4.des": {"oldVal": "第五期次", "newVal": "第十一期次", "op": "MODIFY"},
    "data.proj.data#0.period#1.date_time": {
        "oldVal": "2017-12-11 21:15:17",
        "newVal": "2019-10-11 21:15:17",
        "op": "MODIFY"
    },
    "data.proj.data#0.period#0": {
        "oldVal": {
            "id": "040d38eb9b5caf214baf0a",
            "date_time": "2017-09-11 10:52:27",
            "des": "第一期次"
        }, "newVal": null, "op": "DEL"
    },
    "data.proj.data#0.period#1": {
        "oldVal": {
            "id": "hh7a536e6eb2ba4hyb17d7",
            "date_time": "2019-10-11 21:15:17",
            "des": "第二期次"
        }, "newVal": {"id": "hh7a536e6eb2ba4hyb17d7", "date_time": "2017-12-11 21:15:17", "des": "第二期次"}, "op": "MODIFY"
    },
    "data.proj.data#0.period#4": {
        "oldVal": {
            "id": "h8mozc6e6eb2ba467yhbkz",
            "date_time": "2019-06-18 21:46:08",
            "des": "第十一期次"
        }, "newVal": {"id": "h8mozc6e6eb2ba467yhbkz", "date_time": "2019-06-18 21:46:08", "des": "第五期次"}, "op": "MODIFY"
    },
    "data.proj.data#0.period#5": {
        "oldVal": null,
        "newVal": {"id": "48818c6e6eb2ba468317d7", "date_time": "2017-09-11 10:52:27", "des": "第一期次"},
        "op": "ADD"
    },
    "data.proj.data#1.period#0": {
        "oldVal": {
            "id": "8f362181271803abc75qa0",
            "date_time": "2019-08-21 14:02:37",
            "des": "第十二期次"
        }, "newVal": {"id": "8f362181271803abc75qa0", "date_time": "2019-08-21 14:02:37", "des": "第六期次"}, "op": "MODIFY"
    },
    "data.proj.data#1.period#0.des": {"oldVal": "第六期次", "newVal": "第十二期次", "op": "MODIFY"},
    "data.proj.data#0": {
        "oldVal": {
            "id": "000001",
            "name": "xhliu",
            "version": "version-1",
            "age": 22.0,
            "period": [{
                "id": "48818c6e6eb2ba468317d7",
                "date_time": "2017-09-11 10:52:27",
                "des": "第一期次"
            }, {
                "id": "hh7a536e6eb2ba4hyb17d7",
                "date_time": "2017-12-11 21:15:17",
                "des": "第二期次"
            }, {
                "id": "htgb8c6e6eb2ba468347kl",
                "date_time": "2018-02-15 09:25:15",
                "des": "第三期次"
            }, {
                "id": "hybjpc6e6eb2ba46837pt3",
                "date_time": "2018-04-16 15:02:19",
                "des": "第四期次"
            }, {"id": "h8mozc6e6eb2ba467yhbkz", "date_time": "2019-06-18 21:46:08", "des": "第五期次"}]
        },
        "newVal": {
            "id": "000001",
            "name": "xhliu",
            "version": "version-1",
            "age": 22.0,
            "period": [{
                "id": "040d38eb9b5caf214baf0a",
                "date_time": "2017-09-11 10:52:27",
                "des": "第一期次"
            }, {
                "id": "hh7a536e6eb2ba4hyb17d7",
                "date_time": "2019-10-11 21:15:17",
                "des": "第二期次"
            }, {
                "id": "htgb8c6e6eb2ba468347kl",
                "date_time": "2018-02-15 09:25:15",
                "des": "第三期次"
            }, {
                "id": "hybjpc6e6eb2ba46837pt3",
                "date_time": "2018-04-16 15:02:19",
                "des": "第四期次"
            }, {"id": "h8mozc6e6eb2ba467yhbkz", "date_time": "2019-06-18 21:46:08", "des": "第十一期次"}]
        },
        "op": "MODIFY"
    },
    "data.proj.data#1": {
        "oldVal": {
            "id": "000002",
            "name": "aozhao",
            "version": "version-2",
            "age": 24.0,
            "period": [{
                "id": "8f362181271803abc75qa0",
                "date_time": "2019-08-21 14:02:37",
                "des": "第六期次"
            }, {
                "id": "d38eb9b5caf214baf049a0",
                "date_time": "2019-10-16 16:00:07",
                "des": "第七期次"
            }, {
                "id": "abc756b4e04e68582b6bb4",
                "date_time": "2019-12-15 19:15:15",
                "des": "第八期次"
            }, {
                "id": "271803abc756b4e04e582",
                "date_time": "2020-02-06 10:12:39",
                "des": "第九期次"
            }, {"id": "38eb9b5caf214baf06218", "date_time": "2020-04-08 19:16:18", "des": "第十期次"}]
        },
        "newVal": {
            "id": "000002",
            "name": "aozhao",
            "version": "version-2",
            "age": 24.0,
            "period": [{
                "id": "8f362181271803abc75qa0",
                "date_time": "2019-08-21 14:02:37",
                "des": "第十二期次"
            }, {
                "id": "d38eb9b5caf214baf049a0",
                "date_time": "2019-10-16 16:00:07",
                "des": "第七期次"
            }, {
                "id": "abc756b4e04e68582b6bb4",
                "date_time": "2019-12-15 19:15:15",
                "des": "第八期次"
            }, {
                "id": "271803abc756b4e04e582",
                "date_time": "2020-02-06 10:12:39",
                "des": "第九期次"
            }, {"id": "38eb9b5caf214baf06218", "date_time": "2020-04-08 19:16:18", "des": "第十期次"}]
        },
        "op": "MODIFY"
    }
}

let delQue = [];
let addQue = [];

const listRegex = new RegExp("[^#]+#(\\d+)");
let diffKeys = Object.keys(diffObj);

for (let i = 0; i < diffKeys.length; ++i) {
    let key = diffKeys[i];
    let fs = key.split(".");

    dfs({}, newObj, fs, 0, diffObj[key]);
}

console.log(newObj);

function dfs(lastObj, curObj, fs, idx, diffVal) {
    if (idx >= fs.length) return;
    if (idx === fs.length - 1) {
        curObj[fs[idx]] = diffVal;
        if (lastObj['delQue'] === undefined) lastObj['delQue'] = [];
        if (lastObj['addQue'] === undefined) lastObj['addQue'] = [];

        let op = diffVal['op'];
        if (op === 'DEL') lastObj['delQue'].push(diffVal);
        else if (op === "ADD") lastObj['addQue'].push(diffVal);

        return;
    }

    lastObj = curObj;
    if (listRegex.test(fs[idx])) {
        let arr = fs[idx].split("#");
        curObj = curObj[arr[0]][arr[1]];
    } else {
        curObj = curObj[fs[idx]];
    }

    dfs(lastObj, curObj, fs, idx + 1, diffVal);
}
