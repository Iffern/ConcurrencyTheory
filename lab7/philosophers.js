let randomDelay = function(){
    return Math.floor((Math.random()*1000)+500);
};

let Fork = function() {
    this.state = 0;
    return this;
};

Fork.prototype.acquire = function(philosopherID, forkID, cb) {
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
    console.log("Philosopher "+philosopherID+" want to acquire fork "+forkID);
    let wait = function(delay, fork){
        if(fork.state === 0){
            fork.state = 1;
            console.log("Philosopher "+philosopherID+" acquires fork "+forkID);
            if(cb) cb();
        }
        else{
            setTimeout(function() {wait(2*delay, fork)}, delay);
        }
    };
    let fork = this;
    setTimeout(function () {wait(2, fork)}, 1);
};

Fork.prototype.release = function() {
    this.state = 0;
};

let Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
};

let eat = function(philosopherID, fork1, fork2, cb){
    setTimeout(function () {
        console.log("Philosopher "+philosopherID+" is eating");
        fork1.release();
        fork2.release();
        console.log("Philosopher "+philosopherID+" put down the forks");
        if(cb) cb();
    }, 0);
};

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
        let loop = function(n) {
            if(n>0) {
                    forks[f1].acquire( id,f1,function(){
                    forks[f2].acquire( id,f2,function(){
                        eat(id,forks[f1],forks[f2], function () {
                    loop(n-1);
                })})});
            }
        };

    loop(count);
};

Philosopher.prototype.startAsyn = function(count) {
    var forks = this.forks, id = this.id, f1, f2;
        if(id%2===1){
            f1 = this.f2;
            f2 = this.f1;
        }
        else{
            f1 = this.f1;
            f2 = this.f2;
        }

    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    let loop = function(n) {
        if(n>0) {
            forks[f1].acquire( id,f1,function(){
                forks[f2].acquire( id,f2,function(){
                    eat(id,forks[f1],forks[f2], function () {
                        loop(n-1);
                    })})});
        }
    };

    loop(count);
};

let Conductor = function(n){
    this.state = 0;
    return this;
};

Conductor.prototype.acquire = function (cb) {
    let wait = function(delay, conductor){
        if(conductor.state < 4){
            conductor.state += 1;
            if(cb) cb();
        }
        else{
            setTimeout(function() {wait(2*delay, conductor)}, delay);
        }
    };
    let conductor = this;
    setTimeout(function () {wait(2, conductor)}, 1);
};

Conductor.prototype.release = function () {
    this.state-=1;
};

var N = 5;
var conductor = new Conductor(N);

Philosopher.prototype.startConductor = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
    let loop = function(n) {
        if(n>0) {
            conductor.acquire(function () {
                forks[f1].acquire( id,f1,function(){
                    forks[f2].acquire( id,f2,function(){
                        eat(id,forks[f1],forks[f2], function () {
                            conductor.release();
                            loop(n-1);
                        })})});
            });
        }
    };

    loop(count);
};


var forks = [];
var philosophers = [];
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    philosophers[i].startConductor(10);
}
