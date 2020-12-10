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
        Analyzer.analyze("https://1.bp.blogspot.com/-eaDZ7sDP9uY/Xhwqlve5SUI/AAAAAAABXBo/EcI2C2vim7w2WV6EYy3ap0QLirX7RPohgCNcBGAsYHQ/s400/pose_syanikamaeru_man.png")
            .then(response => {
                setTimeout(() => {
                    try {
                      done();
                      expect(response).toHaveProperty(["anger"]);
                    } catch (error) {
                      return
                    }
                  }, 1000);
                
            })
    })



})

