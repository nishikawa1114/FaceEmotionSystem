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
    disgust: 0.3,
    fear: 0.4,
    happiness: 0.5,
    neutral: 0.6,
    sadness: 0.7,
    surprise: 0.8,
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