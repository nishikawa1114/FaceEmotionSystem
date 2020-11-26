import './index.css';

export class Util {

    // 
    static isInput(str: string) {
        if (!str) { // urlが入力済
            return true;
        } else { // urlが未入力
            return false;
        }
    }

    // urlで指定した画像が存在するか判定する
    static exitImage(str: string) {
        return new Promise((resolve) => {
            const img = new Image();
            img.onload = () => { 
                resolve(true) ;
            }
            img.onerror = () => {
                resolve(false);
            }
            img.src = str;
        });
    }
}