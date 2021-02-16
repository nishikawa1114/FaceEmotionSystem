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

        document.addEventListener('DOMContentLoaded', () => {
            const cnvs = document.getElementById('canvas' + this.props.id) as HTMLCanvasElement;
            const ctx = cnvs.getContext('2d');
            const img = new Image();
            img.src = this.props.img.url;
            img.onload = () => {
                ctx!.drawImage(img,
                    this.props.resultData.faceRectangle.left - (this.props.resultData.faceRectangle.width / 2), this.props.resultData.faceRectangle.top - (this.props.resultData.faceRectangle.height / 2),
                    this.props.resultData.faceRectangle.width * 2, this.props.resultData.faceRectangle.height * 2,
                    20, 20, 200, 100);
            } // 対象者の顔をトリミング
        });

    }

    public render() {
        return (
            <div className="result">
                {/* 顔の画像 */}
                <canvas id={"canvas" + this.props.id}></canvas>
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