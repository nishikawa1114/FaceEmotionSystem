import { ImageArea } from "../ImageArea";
import { create } from "react-test-renderer";
import React from "react";

interface Image {
    id: number;
    url: string;
}

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

const checkedImages: Array<boolean> = [true, false];

    it("snapshot test", () => {
        const tree = create(
            <ImageArea
                images={images}
                checkedImages={checkedImages}
                onClick={() => {}}
            />
        ).toJSON();
        expect(tree).toMatchSnapshot();

    })