import { Analyzer } from "../Analyzer";
import fetch from 'node-fetch'
require('jest-fetch-mock').enableMocks()
// モックの用意が必要になる

describe("Analyzer test", () => {
    // urlの画像が存在する
    test("image exist", async () => {
        // Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D")
        //     .then(response => () {
        //         expect(response).toEqual(emotion);   
        //     })

        fetchMock.mockOnce("the next call to fetch will always return this as the body");

        fetch.mockResponse(JSON.stringify({ test: 'example' }));

        const data = await Analyzer.analyze("https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D");
        expect(data).toEqual({ test: 'example' });

        beforeEach(() => { // if you have an existing `beforeEach` just add the following line to it
            fetchMock.doMock()
        })

    })

})

