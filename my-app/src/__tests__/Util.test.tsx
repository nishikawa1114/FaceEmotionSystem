import {Util} from "../Util";

describe("Util.ts test", () =>{
    // isInput()
    // urlが正しく入力されている
    it("input url", () => {
        const isInput1 = Util.isInput("https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D");
        expect(isInput1).toEqual(true);
    })

    // 存在しない画像のurlが入力されている
    it("input url noImage", () => {
        const isInput2 = Util.isInput("https://nishikawa.blob.core.windows.net/images/nishikawa/2020/10/10/10.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D");
        expect(isInput2).toEqual(true);
    })

    // urlでない文字列が入力
    it("input url not", () => {
        const isInput3 = Util.isInput("abcdefg.xyz");
        expect(isInput3).toEqual(true);
    })

    // 全角文字列が入力
    it("input fullwidth", () => {
        const isInput4 = Util.isInput("あいうえお");
        expect(isInput4).toEqual(true);
    })

    // 空文字の入力
    it("not Input", () => {
        const notInput = Util.isInput("");
        expect(notInput).toEqual(false);
    })

    // existImage()
    // urlの画像が存在する
    it("exist image", () => {
        Util.imageExists("https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
            .then(response => {
                expect(response).toEqual(true);
            })
    })

    // urlの画像が存在しない
    it("not exist image", () => {
        Util.imageExists("https://nishikawa.blob.core.windows.net/images/nishikawa/2020/10/10/10.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
         .then(response => {
            expect(response).toEqual(false);
        })
    })

    // urlが空文字列
    it("no url", () => {
        const noUrl = Util.imageExists("")
            .then(response => {
                expect(noUrl).toEqual(false);
            })
    })

    // urlが全角文字列
    it("url fullwidth", () => {
        const urlFullWidth = Util.imageExists("あいうえお")
            .then(response => {
                expect(urlFullWidth).toEqual(false);
            })
    })

})