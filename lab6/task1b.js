const async = require('async');
function loop(n){
        if(n===0) console.log('done!');
        if(n!==0){
            async.waterfall([
                task1,
                task2,
                task3,
            ], function (error, result) {
                loop(n-1);
            });
        }
}

function task1(cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log('1');
        cb(null, '1');
    }, delay);
}

function task2(arg1, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log('2');
        cb(null, '2');
    }, delay);
}

function task3(arg1, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log('3');
        cb(null, '3');
    }, delay);
}

loop(4);
