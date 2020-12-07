import { Home } from "../Home";
import { create } from "react-test-renderer";
import React from "react";

interface Image {
    id: number;
    url: string;
}

it("2 images, 1checked", () => {
    const images: Array<Image> = [
        {
            id: 1,
            url: "https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D",
        },
        {
            id: 2,
            url: "https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D",
        }
    ]
    const checkedImages: Array<boolean> = [true, false]
    const canAnalyze = true;
    const tree = create(
        <Home
            inputUrl="https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D"
            isInputUrl={true}
            images={images}
            checkedImages={checkedImages}
            canAnalyze={canAnalyze}
            handleSubmit={() => {}}
            handleChange={() => {}}
            handleClick={() => {}}
            handleSubmitAnalyze={() => {}}
        />
    ).toJSON();
    expect(tree).toMatchSnapshot();

})