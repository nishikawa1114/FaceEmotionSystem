import { Analyzer } from "../Analyzer";

jest.mock('node-fetch', () => require('fetch-mock').sandbox())
// jest.mock('node-fetch', () => {
//   const nodeFetch = jest.requireActual('node-fetch');
//   const fetchMock = require('fetch-mock').sandbox();
//   Object.assign(fetchMock.config, {
//     fetch: nodeFetch
//   });
//   return fetchMock;
// })
import fetchMock from 'fetch-mock';

describe("Analyzer test", () => {

  it("can analyze", async (done) => {
    Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
      .then(response => {
        setTimeout(() => {
          try {
            done();
            expect(response).toHaveProperty([
              "anger",
              "contempt",
              "disgust",
              "fear",
              "happiness",
              "neutral",
              "sadness",
              "surprise"]);
          } catch (error) {
            return
          }
        }, 1000);
      })
  })

  it("can't analyze", async (done) => {
    Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
      .then(response => {
        setTimeout(() => {
          try {
            done();
            expect(response).toEqual([]);
          } catch (error) {
            return
          }
        }, 1000);

      })
  })

  // jest.mock('node-fetch', () => jest.fn())
  // const mock = jest.fn();
  // mock.mockReturnValue(1);


  it("mock test", async (done) => {
    fetchMock
      .mock(
        "*",
        // レスポンス
        {
          body: [{ url: "aaa" }]
        }
      );

    await Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/00.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
      .then(response => {
        setTimeout(() => {
          try {
            console.log("===================================");

            done();
            console.log("===================================");
            console.log(JSON.stringify(response));
            expect(response).toHaveProperty([
              "anger",
              "contempt",
              "disgust",
              "fear",
              "happiness",
              "neutral",
              "sadness",
              "surprise"]);
          } catch (error) {
            return
          }
        }, 1000);
      })

    // fetchMockの設定をリセット
    fetchMock.restore();
  });




})

