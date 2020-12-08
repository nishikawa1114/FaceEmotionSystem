import { Error } from "../Error";
import { create } from "react-test-renderer";
import React from "react";

describe("Error test", () => {
    it("error no image", () => {
        const tree2 = create(
            <Error
                errorId={1}
                onSubmit={() => { }}
            />
        ).toJSON();
        expect(tree2).toMatchSnapshot();
    })

    it("error analyze", () => {
        const tree3 = create(
            <Error
                errorId={2}
                onSubmit={() => { }}
            />
        ).toJSON();
        expect(tree3).toMatchSnapshot();
    })

    it("other error", () => {
        const tree = create(
            <Error
                errorId={0}
                onSubmit={() => { }}
            />
        ).toJSON();
        expect(tree).toMatchSnapshot();
    })
})