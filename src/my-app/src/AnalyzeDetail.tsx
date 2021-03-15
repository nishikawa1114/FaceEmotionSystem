import React from 'react';
import { Graph } from './Graph';
import './index.css';
import { ResultTable } from './ResultTable';
import { ImageUrl, ResultData } from './types';

interface DetailProps {
    img: ImageUrl;
    resultData: ResultData;
    id: number;
}

// 各々の分析結果の画像・グラフ・表を表示するコンポーネント
export class AnalyzeDetail extends React.Component<DetailProps>  {

    public componentDidMount() {
        document.addEventListener('DOMContentLoaded',  () => {
            const canvas = document.getElementById('canvas' + this.props.id) as HTMLCanvasElement;
            const context = canvas.getContext('2d');
            const image = new Image();
            image.src = this.props.img.url;
            image.onload = () => {
                // 画像から分析対象の人物の顔を切り取って表示する
                // 分析領域のみを切り取ると顔の中心のみがアップの画像になるため頭全体が表示されやすくするために縦横2倍の領域を切り取ることとした。
                context!.drawImage(
                    image,
                    this.props.resultData.faceRectangle.left - (this.props.resultData.faceRectangle.width / 2), // 切り取る領域の左上のｘ座標
                    this.props.resultData.faceRectangle.top - (this.props.resultData.faceRectangle.height / 2), // 切り取る領域の左上のｙ座標
                    this.props.resultData.faceRectangle.width * 2, // 切り取る横幅
                    this.props.resultData.faceRectangle.height * 2, // 切り取る縦幅
                    20, // 表示する位置のx座標
                    20, // 表示する位置のy座標
                    200, // 表示する幅
                    200); // 表示する高さ
            } // 対象者の顔をトリミング
        });

    }

    public render() {
        return (
            <div className="result">
                {/* 顔の画像 */}
                <canvas width="300px" height="300px" id={"canvas" + this.props.id}></canvas>
                {/* 分析結果のグラフ表示 */}
                <Graph
                    emotion={this.props.resultData.faceAttributes.emotion}
                />
                {/* 分析結果の表表示 */}
                <ResultTable
                    emotion={this.props.resultData.faceAttributes.emotion}
                />
            </div>
        )
    }
}