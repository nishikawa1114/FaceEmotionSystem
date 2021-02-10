import React from 'react';
import { Graph } from './Graph';
import './index.css';
import { ResultTable } from './ResultTable';
import { ImageUrl, ResultData } from './types';

interface DetailProps {
    img: ImageUrl;
    resultData: ResultData;
    index: number;
}

export class AnalyzedDetail extends React.Component<DetailProps>  {

    public componentDidMount() {
        const cnvs = document.getElementById('canvas' + this.props.index) as HTMLCanvasElement;
        const ctx = cnvs.getContext('2d');
        const img = new Image();
        img.src = this.props.img.url;
        img.onload = () => {
            ctx!.drawImage(img,
                this.props.resultData.faceRectangle.left - (this.props.resultData.faceRectangle.width / 2), this.props.resultData.faceRectangle.top - (this.props.resultData.faceRectangle.height / 2),
                this.props.resultData.faceRectangle.width * 2, this.props.resultData.faceRectangle.height * 2,
                20, 20, 200, 100);
        }
    }

    public render() {
        return (
            <div className="result">
                <canvas id={"canvas" + this.props.index}></canvas>
                <Graph
                    emotion={this.props.resultData.faceAttributes.emotion}
                />
                <ResultTable
                    emotion={this.props.resultData.faceAttributes.emotion}
                />
            </div>
        )
    }
}