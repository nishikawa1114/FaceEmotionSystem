import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

export class Util {

    static isInput(url: string) {
        if (!url) { // urlが入力済
            return true;
        } else { // urlが未入力
            return false;
        }
    }

    static exitImageUrl(str: string) {
        // 画像オブジェクトの新規生成
        let image = new Image();
        let exit: boolean;

        // ロード完了時処理
        image.onload =
            function () {
                console.log('success');
            };

        // ロードエラー時処理
        image.onerror =
            function () {
                console.log('error');
            };

        image.src = str;
        
    }

}