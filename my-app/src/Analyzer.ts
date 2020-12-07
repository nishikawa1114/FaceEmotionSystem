'use strict';

import fetch from 'node-fetch';
require('dotenv').config();

export class Analyzer {

    public static analyze(str: string) {

        // Add a valid subscription key and endpoint to your environment variables.
        const subscriptionKey = String(process.env.REACT_APP_FACE_SUBSCRIPTION_KEY);
        const endpoint = String(process.env.REACT_APP_FACE_ENDPOINT) + '/face/v1.0/detect';
        // Optionally, replace with your own image URL (for example a .jpg or .png URL).
        const imageUrl = str;

        const queryParams = {    // Face API に渡すパラメータ
            "detectionModel": "detection_01",
            "returnFaceAttributes": "emotion",
            "returnFaceId": "true"
        };

        const queryString = new URLSearchParams(queryParams);

        
        return fetch(endpoint + '?' + queryString, {  // パラメータparamsをURLに入れてFace API に渡す
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
                // 分析結果が空の場合 
                if (data.length === 0) {
                    return Object;
                }
                const emotion = data[0]['faceAttributes']['emotion'];
                return emotion;
            })
            .catch((error: any) => { // エラーの場合
                console.log(error);
                return Object;
            });
    }
}