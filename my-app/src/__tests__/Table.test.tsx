import { Table } from "../Table";
import { create } from "react-test-renderer";
import React from "react";
import { Emotion } from "../types";

const emotions: Emotion = {
    anger: 0.1,
    contempt: 0.2,
    disgust: 0.3,
    fear: 0.4,
    happiness: 0.5,
    neutral: 0.6,
    sadness: 0.07,
    surprise: 0,
}

it("ImageInfo test", () => {
    const tree = create(
        <Table
            emotion={emotions}
        />
    ).toJSON();
    expect(tree).toMatchSnapshot();

})