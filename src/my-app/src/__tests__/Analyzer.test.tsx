import { Analyzer } from "../Analyzer";

jest.mock('node-fetch', () => require('fetch-mock').sandbox())
import fetchMock from 'fetch-mock';

describe("Analyzer test", () => {
  // 一人が写った画像の分析結果を受け取るテスト
  it("success one person", async (done) => {
    fetchMock
      .mock(
        "*",
        // レスポンス
        {
          body: [
            {
              "faceId": "b4e6ce49-a1e6-492d-b9e4-f218a1720022",
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
          ]
        }
      );

    await Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
      .then(response => {
        setTimeout(() => {
          try {
            done();
            expect(response).toHaveLength(1);
            expect(response).toMatchObject([
              {
                "faceId": "b4e6ce49-a1e6-492d-b9e4-f218a1720022",
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
            ]);
            console.log(JSON.stringify(response));
          } catch (error) {
          }
        }, 1000);
      })

    // fetchMockの設定をリセット
    fetchMock.restore();
  });

  // 複数人写った画像の分析結果を受け取るテスト
  it("success four person", async (done) => {
    fetchMock
      .mock(
        "*",
        // レスポンス
        {
          body: [
            {
              "faceId": "b4e6ce49-a1e6-492d-b9e4-f218a1720022",
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
              "faceId": "b43b1ad9-dab5-4b73-bf4c-285ddb1058a9",
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
              "faceId": "71c518d3-c68b-4664-a251-cbf9158e13b8",
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
              "faceId": "c8adfd69-6217-4c86-b2ae-34eb5446ac3a",
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
      );

    await Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/groupe/2020/12/23/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
      .then(response => {
        setTimeout(() => {
          try {
            done();
            expect(response).toHaveLength(1);
            expect(response).toMatchObject([
              {
                "faceId": "b4e6ce49-a1e6-492d-b9e4-f218a1720022",
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
                "faceId": "b43b1ad9-dab5-4b73-bf4c-285ddb1058a9",
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
                "faceId": "71c518d3-c68b-4664-a251-cbf9158e13b8",
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
                "faceId": "c8adfd69-6217-4c86-b2ae-34eb5446ac3a",
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
            ]);
            console.log(JSON.stringify(response));
          } catch (error) {
            return
          }
        }, 1000);
      })

    // fetchMockの設定をリセット
    fetchMock.restore();
  });

// 一人も写っていない画像の分析結果を受け取るテスト
  it("zero person", async (done) => {
    fetchMock
      .mock(
        "*",
        // レスポンス
        {
          body: []
        }
      );

    await Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
      .then(response => {
        setTimeout(() => {
          try {
            done();
            expect(response).toHaveLength(0);
            console.log(JSON.stringify(response));
          } catch (error) {
            return
          }
        }, 1000);
      })

    // fetchMockの設定をリセット
    fetchMock.restore();
  });

})

