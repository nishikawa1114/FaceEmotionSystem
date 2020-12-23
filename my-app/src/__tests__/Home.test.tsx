import { Home } from "../Home";
import { Image } from "../types"
import { create } from "react-test-renderer";
import React from "react";

describe("input test", () => {
    it("not input", () => {
        const images: Array<Image> = [];
        const checkedImages: Array<boolean> = [];
        const canAnalyze = false;
        const tree = create(
            <Home
                inputUrl=""
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
    
    it("inputed url", () => {
        const images: Array<Image> = [];
        const checkedImages: Array<boolean> = [];
        const canAnalyze = false;
        const tree = create(
            <Home
                inputUrl="https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D"
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
})

describe("display images test", () => {
    it("2 images, 1 checked", () => {
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
        const canAnalyze = true; //
        const tree = create(
            <Home
                inputUrl=""
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
    
    it("2 images,  unchecked", () => {
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
        const checkedImages: Array<boolean> = [false, false]
        const canAnalyze = false;
        const tree = create(
            <Home
                inputUrl=""
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
    
    it("2 images, all checked", () => {
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
        const checkedImages: Array<boolean> = [true, true]
        const canAnalyze = false;
        const tree = create(
            <Home
                inputUrl=""
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
})

