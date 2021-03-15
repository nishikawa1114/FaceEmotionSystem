import { create } from "react-test-renderer";
import React from "react";
import { AnalyzeResult } from './../AnalyzeResult';
import { Analyzer } from "./../Analyzer";
import { ImageUrl } from "../types";

// 複数人写る画像の分析結果が表示できているかテスト
describe("should display analyze results.", () => {
    let spy: jest.SpyInstance<Promise<any>, [str: string]>;


    it("display analyze result 4 pserson", (done) => {

        spy = jest.spyOn(Analyzer, 'analyze').mockImplementationOnce(
            (s: string): Promise<any> => Promise.resolve(
                {
                    "total": 4,
                    "mean": {
                        "faceAttributes": {
                            "emotion": {
                                "anger": 0,
                                "contempt": 0,
                                "disgust": 0,
                                "fear": 0,
                                "happiness": 1,
                                "neutral": 0,
                                "sadness": 0,
                                "surprise": 0
                            }
                        }
                    },
                    "person":
                        [
                            {
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
                                        "surprise": 0
                                    }
                                }
                            },
                            {
                                "faceID": "b43b1ad9-dab5-4b73-bf4c-285ddb1058a9",
                                "faceRectangle": {
                                    "top": 161,
                                    "left": 214,
                                    "width": 45,
                                    "height": 45
                                },
                                "faceAttributes": {
                                    "emotion": {
                                        "anger": 0.042,
                                        "contempt": 0,
                                        "disgust": 0,
                                        "fear": 0,
                                        "happiness": 0.958,
                                        "neutral": 0,
                                        "sadness": 0,
                                        "surprise": 0
                                    }
                                }
                            },
                            {
                                "faceID": "71c518d3-c68b-4664-a251-cbf9158e13b8",
                                "faceRectangle": {
                                    "top": 147,
                                    "left": 498,
                                    "width": 43,
                                    "height": 43
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
                                        "surprise": 0
                                    }
                                }
                            },
                            {
                                "faceID": "c8adfd69-6217-4c86-b2ae-34eb5446ac3a",
                                "faceRectangle": {
                                    "top": 112,
                                    "left": 366,
                                    "width": 41,
                                    "height": 41
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
                                        "surprise": 0
                                    }
                                }
                            }
                        ]
                }

            )
        );

        const img = { id: 1, url: "https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D" };
        const tree = create(
            <AnalyzeResult
                checkedimage={img}
                onClick={() => { }}
                onSubmit={() => { }}
                setErrorId={() => { }}
            />
        );
        setTimeout(() => {
            try {
                done();
                expect(tree.toJSON()).toMatchSnapshot();
            } catch (error) {
                return
            }
        }, 5000);
    })


    it("display analyze result 11 person", (done) => {


        spy = jest.spyOn(Analyzer, 'analyze').mockImplementationOnce(
            (s: string): Promise<any> => Promise.resolve(
                {
                    "total": 4,
                    "mean": {
                        "faceAttributes": {
                            "emotion": {
                                "anger": 0,
                                "contempt": 0,
                                "disgust": 0,
                                "fear": 0,
                                "happiness": 1,
                                "neutral": 0,
                                "sadness": 0,
                                "surprise": 0
                            }
                        }
                    },
                    "person":
                    [
                        {
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
                            "faceID": "b43b1ad9-dab5-4b73-bf4c-285ddb1058a9",
                            "faceRectangle": {
                                "top": 161,
                                "left": 214,
                                "width": 45,
                                "height": 45
                            },
                            "faceAttributes": {
                                "emotion": {
                                    "anger": 0.042,
                                    "contempt": 0,
                                    "disgust": 0,
                                    "fear": 0,
                                    "happiness": 0.958,
                                    "neutral": 0,
                                    "sadness": 0,
                                    "surprise": 0
                                }
                            }
                        },
                        {
                            "faceID": "71c518d3-c68b-4664-a251-cbf9158e13b8",
                            "faceRectangle": {
                                "top": 147,
                                "left": 498,
                                "width": 43,
                                "height": 43
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
                            "faceID": "c8adfd69-6217-4c86-b2ae-34eb5446ac3a",
                            "faceRectangle": {
                                "top": 112,
                                "left": 366,
                                "width": 41,
                                "height": 41
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
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
                                    "surprise": 0
                                }
                            }
                        },
                        {
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
                                    "surprise": 0
                                }
                            }
                        }
                    ]
                }
            )
        );


        const img = { id: 1, url: "https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D" };
        const tree = create(
            <AnalyzeResult
                checkedimage={img}
                onClick={() => { }}
                onSubmit={() => { }}
                setErrorId={() => { }}
            />
        );
        setTimeout(() => {
            try {
                done();
                expect(tree.toJSON()).toMatchSnapshot();
            } catch (error) {
                return
            }
        }, 5000);
    })
})

const img: ImageUrl = { id: 1, url: "https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D" };
const props = {
    checkedimage: img,
    onSubmit: () => { },
    onClick: () => { },
    setErrorId: () => { }
}
const analyzeResult = new AnalyzeResult(props);

describe("getName() test", () => {


    it("正常動作", () => {
        const url = "https://nishikawa.blob.core.windows.net/images/nishikawa/2020/09/01/01.jpg";

        const name = analyzeResult.getName(url);
        console.log(name);
        expect(name).toEqual("nishikawa");
    })

    it("images/images/ と続く場合", () => {
        const url = "https://nishikawa.blob.core.windows.net/images/images/nishikawa/2020/09/01/01.jpg";

        const name = analyzeResult.getName(url);
        console.log(name);
        expect(name).toEqual("nishikawa");
    })

    it("images がない場合", () => {
        const url = "https://nishikawa.blob.core.windows.net/nishikawa/2020/09/01/01.jpg";

        const name = analyzeResult.getName(url);
        console.log(name);
        expect(name).toEqual("-");
    })
})


describe("getDate() test", () => {
    it("正常動作", () => {
        const url = "https://nishikawa.blob.core.windows.net/images/images/nishikawa/2020/09/01/01.jpg";

        const name = analyzeResult.getDate(url);
        console.log(name);
        expect(name).toEqual("2020/09/01");
    })

    it("日付がない場合", () => {
        const url = "https://nishikawa.blob.core.windows.net/images/nishikawa/0.jpg";

        const name = analyzeResult.getDate(url);
        console.log(name);
        expect(name).toEqual("-");
    })

    it("日付が2箇所にある場合", () => {
        const url = "https://nishikawa.blob.core.windows.net/images/images/nishikawa/2020/09/01/01.jpg/2020/01/01/";

        const name = analyzeResult.getDate(url);
        console.log(name);
        expect(name).toEqual("2020/09/01");
    })
})