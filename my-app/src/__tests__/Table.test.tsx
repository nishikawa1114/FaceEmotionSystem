import { Table } from "../Table";
import { create } from "react-test-renderer";
import React from "react";

interface Emotion {
    anger: number;
    contempt: number;
    disgust: number;
    fear: number;
    happiness: number;
    neutral: number;
    sadness: number;
    surprise: number;
}

const emotions: Emotion = {
    anger: 0.1,
    contempt: 0.2,
    disgust: 0.1,
    fear: 0,
    happiness: 0.005,
    neutral: 0.4,
    sadness: 0.01,
    surprise: 0.002,
}
// const img: Image = { id: 1, url: "https://nishikawa.blob.core.windows.net/images/steve/2020/10/15/01.jpg?sv=2019-07-07&sr=c&si=myPolicyPS&sig=FkKJ4nXCiqzDYjbSaDfqli%2FnErPRTKrD%2BUQfH0MT3ac%3D" };

it("ImageInfo test", () => {
    const tree = create(
        <Table
            emotion={emotions}
        />
    ).toJSON();
    expect(tree).toMatchSnapshot();

})