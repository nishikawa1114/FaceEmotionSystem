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
        if(tree2) {
            expect(JSON.stringify(tree2)).toContain("画像が存在しません");
        }
    })

    it("error analyze", () => {
        const tree3 = create(
            <Error
                errorId={2}
                onSubmit={() => { }}
            />
        ).toJSON();
        expect(tree3).toMatchSnapshot();
        if(tree3) {
            expect(JSON.stringify(tree3)).toContain("分析結果が取得できませんでした");            
        }
    })

    it("other error", () => {
        const tree = create(
            <Error
                errorId={0}
                onSubmit={() => { }}
            />
        ).toJSON();
        expect(tree).toMatchSnapshot();
        if (tree) {
            expect(JSON.stringify(tree)).toContain("予期しないエラーが発生しました");
        }
    })
})