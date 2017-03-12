'use strict'

// HIERARCHY
//   res
//    |- values-hdpi
//    |   |- dimens_dp_hdpi
//    |   |_ dimens_sp_hdpi
//    |
//    |- values-xhdpi
//    |   |- dimens_dp_xhdpi
//    |   |_ dimens_sp_xhdpi
//    |
//    |- values-xxhdpi
//        |- dimens_dp_xxhdpi
//        |- dimens_sp_xxhdpi

let path = require('path');
let res = path.resolve(__dirname, 'res');

let dimenPrefix = '<dimen name=';
let dimenPostfix = '</dimen>';

let main = function(){
    createHDPI();
    createXHDPI();
    createXXHDPI();
};

let createHDPI = function(){

};

let createXHDPI = function(){

};

let createXXHDPI = function(){

};

let createFolder = function(container, name){

};

let createFile = function(container, name, content){
	
};


main();