import './index.css';

export class Util {

    // 
    static isInput(inputStr: string) {
        if (inputStr) { // urlが入力済
            return true;
        } else { // urlが未入力
            return false;
        }
    }

    // urlで指定した画像が存在するか判定する
    static imageExists(imageUrl: string) {
        return new Promise((resolve) => {
            const img = new Image();
            img.onload = () => { 
                resolve(true) ;
            }
            img.onerror = () => {
                resolve(false);
            }
            img.src = imageUrl;
        });
    }
}