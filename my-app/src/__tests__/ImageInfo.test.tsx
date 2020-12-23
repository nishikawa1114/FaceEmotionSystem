import { ImageInfo } from "../ImageInfo";
import { create } from "react-test-renderer";
import React from "react";
import { Image } from "../types";


const img: Image = { id: 1, url: "https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D" };

describe("checked test", () => {
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
})

describe("display test", () => {
    it("shold display name steve", () => {
        const tree = create(
            <ImageInfo
                image={img}
                onClick={() => { }}
                checked={true}
            />
        ).toJSON();
        expect(JSON.stringify(tree)).toContain("steve");
    })

    it("shold display date 2020/10/15", () => {
        const tree = create(
            <ImageInfo
                image={img}
                onClick={() => { }}
                checked={true}
            />
        ).toJSON();
        expect(JSON.stringify(tree)).toContain("2020/10/15");
    })

})

describe("funcotino test", () => {
    const props = {
        image: img,
        checked: false,
        onClick: () => { },
    };
    const imageInfo = new ImageInfo(props);

    it("should get name steve", () => {
        const name = imageInfo.getName(img.url);
        expect(name).toEqual("steve")
    })

    it("should get date 2020/10/15", () => {
        const date = imageInfo.getDate(img.url);
        expect(date).toEqual("2020/10/15")
    })
})