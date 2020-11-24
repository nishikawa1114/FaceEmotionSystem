import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

export class Util {

    static isInput(url: string) {
        if(!url) { // urlが入力済
            return true;
        }else{ // urlが未入力
            return false;
        }
    }

}