import {Analyzer} from "../Analyzer";

// モックの用意が必要になる

describe("test 1", () =>{
    // urlの画像が存在する
    test("image exist", () => {
        Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
            .then(response => () {
                expect(response).toEqual(emotion);   
            })
    })

    
    
})