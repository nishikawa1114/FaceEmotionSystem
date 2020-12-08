import { ImageInfo } from "../ImageInfo";
import { create } from "react-test-renderer";
import React from "react";
import { Image } from "../types";


const img: Image = {id: 1, url: "https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D"};

it("unchecked", () => {
    const tree = create(
        <ImageInfo
            image={img}
            onClick={() => { }}
            checked={false}
        />
    ).toJSON();
    expect(tree).toMatchSnapshot();

})

it("checked", () => {
    const tree = create(
        <ImageInfo
            image={img}
            onClick={() => { }}
            checked={true}
        />
    ).toJSON();
    expect(tree).toMatchSnapshot();

})