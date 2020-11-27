'use strict';
import fetch from 'node-fetch';

export class Analyser {

    public static analyse(str: string) {

        // Add a valid subscription key and endpoint to your environment variables.
        let subscriptionKey = '';
        let endpoint = '' + '/face/v1.0/detect'

        // Optionally, replace with your own image URL (for example a .jpg or .png URL).
        let imageUrl = str;

        const params = {    // Face API に渡すパラメータ
            "detectionModel": "detection_01",
            "returnFaceAttributes": "emotion",
            "returnFaceId": "true"
        };

        const query_params = new URLSearchParams(params);

        
        return fetch(endpoint + '?' + query_params, {  // パラメータparamsをURLに入れてFace API に渡す
            method: 'POST',
            headers: { 
                'Ocp-Apim-Subscription-Key': subscriptionKey,   // Face API サブスクリプションキー
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({'url': imageUrl}) // Face API に渡したい画像のURL
        })
            .then((response: { json: () => any; }) => {
                return response.json(); // Promiseを返す
            })
            .then((data: { [x: string]: { [x: string]: any; }; }[]) => { // JSONデータ
                let id = data[0]['faceId'];
                let emotion = data[0]['faceAttributes']['emotion'];
                console.log(emotion);
                return emotion;
            })
            .catch((error: any) => { // エラーの場合
                console.log(error);
            });
    }
}