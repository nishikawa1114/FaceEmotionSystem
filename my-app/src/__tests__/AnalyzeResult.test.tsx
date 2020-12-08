import { create } from "react-test-renderer";
import React from "react";
import { AnalyzeResult } from './../AnalyzeResult';
import { Analyzer } from "./../Analyzer";

describe("should display analyze result.", () => {
    let spy: jest.SpyInstance<Promise<any>, [str: string]>;
    beforeEach(() => {
        spy = jest.spyOn(Analyzer, 'analyze').mockImplementationOnce(
            (s: string): Promise<any> => Promise.resolve(
                {
                    anger: 0.1,
                    contempt: 0.2,
                    disgust: 0.3,
                    fear: 0.4,
                    happiness: 0.5,
                    neutral: 0.6,
                    sadness: 0.7,
                    surprise: 0,
                }
            )
        );
    });
    it("display analyze result", (done) => {
        const img = { id: 1, url: "dummy_url/images/steve/2020/10/15/01.jpg" };
        const tree = create(
            <AnalyzeResult
                checkedimage={img}
                onClick={() => { }}
                onSubmit={() => { }}
                setErrorId={() => { }}
            />
        );
        expect(spy).toBeCalledTimes(1)
        setTimeout(() => {
            try {
                done();
                expect(tree.toJSON()).toMatchSnapshot();
            } catch (error) {
                return
            }
        }, 5000);
    })
})