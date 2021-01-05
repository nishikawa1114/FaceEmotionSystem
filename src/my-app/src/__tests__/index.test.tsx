import React from "react";
import ReactDOM from 'react-dom';
import { create } from "react-test-renderer";
import { Index } from "../index";

// 初期画面のテスト
it("display initial page", () => {
    const tree = create(
        <Index />
    ).toJSON();
    expect(tree).toMatchSnapshot();
})