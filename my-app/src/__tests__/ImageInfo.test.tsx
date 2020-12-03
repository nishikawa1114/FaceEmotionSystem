import { ImageInfo } from "../ImageInfo";
import { create } from "react-test-renderer";
import React from "react";

interface Image {
    id: number;
    url: string;
}

const img: Image = {id: 1, url: "https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D"};

it("ImageInfo test", () => {
    const tree = create(
        <ImageInfo
            image={img}
            onClick={() => { }}
            checked={false}
        />
    ).toJSON();
    expect(tree).toMatchSnapshot();

})