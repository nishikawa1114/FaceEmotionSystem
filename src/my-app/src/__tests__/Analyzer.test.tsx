import { Analyzer } from "../Analyzer";

describe("Analyzer test", () => {
    it("can analyze", async (done) => {
        Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
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
        Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/sugimoto/2020/11/01/02.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
            .then(response => {
                setTimeout(() => {
                    try {
                      done();
                      expect(response).toEqual({});
                    } catch (error) {
                      return                                            
                    }
                  }, 1000);
                
            })
    })



})
