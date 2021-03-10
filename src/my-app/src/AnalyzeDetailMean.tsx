import React from 'react';
import { Graph } from './Graph';
import './index.css';
import { ResultTable } from './ResultTable';
import { ImageUrl, MeanfaceAttributes, ResultData } from './types';

interface DetailProps {
    img: ImageUrl;
    mean: MeanfaceAttributes;
}

// 各々の分析結果の画像・グラフ・表を表示するコンポーネント
export class AnalyzeDetailMean extends React.Component<DetailProps>  {

    public componentDidMount() {
        document.addEventListener('DOMContentLoaded',  () => {
            const canvas = document.getElementById('canvas_mean') as HTMLCanvasElement;
            const context = canvas.getContext('2d');
            const image = new Image();
            image.src = this.props.img.url;


            context!.drawImage(
                image,
                20, // 表示する位置のx座標
                20, // 表示する位置のy座標
                200, // 表示する幅
                100); // 表示する高さ
        });


    }

    public render() {
        return (
            <div className="result">
                {/* 顔の画像 */}
                <canvas width="300px" height="300px" id={"canvas_mean"}></canvas>
                {/* 分析結果のグラフ表示 */}
                <Graph
                    emotion={this.props.mean.faceAttributes.emotion}
                />
                {/* 分析結果の表表示 */}
                <ResultTable
                    emotion={this.props.mean.faceAttributes.emotion}
                />
            </div>
        )
    }
}