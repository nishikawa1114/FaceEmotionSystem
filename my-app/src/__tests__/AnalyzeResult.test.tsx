import { create } from "react-test-renderer";
import React from "react";
import { AnalysResult } from './../AnalyzeResult';
import { Emotion } from './../types';

import * as AnalyzerClass from './../Analyzer'

jest.mock('./../Analyzer');
const AnalyzerClassMock = AnalyzerClass.Analyzer as unknown as jest.Mock;

it("snapshot test", () => {
    const emotionValue: Emotion = {
        anger: 0.1,
        contempt: 0.1,
        disgust: 0.1,
        fear: 0.1,
        happiness: 0.1,
        neutral: 0.1,
        sadness: 0.1,
        surprise: 0.1,
    }
    // const analyzeSpy = jest.spyOn(AnalyzerClass, 'analyze').mockReturnValueOnce(emotionValue);
    AnalyzerClassMock.mockImplementationOnce(() => {
        return {
            data: 1,
            analyze: (): => {
                return emotionValue;
            }
        }
    })

    expect(AnalyzerClassMock)
})