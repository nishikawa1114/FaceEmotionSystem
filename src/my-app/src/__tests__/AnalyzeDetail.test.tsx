import { create } from "react-test-renderer";
import React from "react";
import { AnalyzeDetail } from '../AnalyzeDetail';
import { ResultData } from '../types';
import { Util } from "../Util";
import { ImageUrl } from './../types';
import { AnalyzeResult } from './../AnalyzeResult';

describe("should display analyze result.", () => {
    // 分析結果の画像、グラフ、表が表示されているかテスト
    it("display analyze result", () => {
        const img = { id: 1, url: "https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D/steve/2020/10/15/01.jpg" };
        const result: ResultData = {
                "faceID": "b4e6ce49-a1e6-492d-b9e4-f218a1720022",
                "faceRectangle": {
                    "top": 120,
                    "left": 80,
                    "width": 46,
                    "height": 46
                },
                "faceAttributes": {
                    "emotion": {
                        "anger": 0,
                        "contempt": 0,
                        "disgust": 0,
                        "fear": 0,
                        "happiness": 1,
                        "neutral": 0,
                        "sadness": 0,
                        "surprise": 0,
                    }
                }
            }
        const tree = create(
            <AnalyzeDetail
                img={img}
                resultData={result}
                id={1}
            />
        ).toJSON();
        expect(tree).toMatchSnapshot();
    })
})

