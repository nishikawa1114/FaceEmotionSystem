import React from 'react';
import './index.css';
import { Radar, RadarChart, PolarGrid, Legend, PolarAngleAxis, PolarRadiusAxis } from "recharts";
import { Emotion } from './types';

interface GraphProps {
    emotion: Emotion;
}

// 分析結果のemotionのグラフを表示するコンポーネント
export class Graph extends React.Component<GraphProps> {

    render() {

        const emotionData = [
            {
                subject: 'anger', A: this.props.emotion.anger, fullMark: 1,
            },
            {
                subject: 'contempt', A: this.props.emotion.contempt, fullMark: 1,
            },
            {
                subject: 'disgust', A: this.props.emotion.disgust, fullMark: 1,
            },
            {
                subject: 'fear', A: this.props.emotion.fear, fullMark: 1,
            },
            {
                subject: 'happiness', A: this.props.emotion.happiness, fullMark: 1,
            },
            {
                subject: 'neutral', A: this.props.emotion.neutral, fullMark: 1,
            },
            {
                subject: 'sadness', A: this.props.emotion.sadness, fullMark: 1,
            },
            {
                subject: 'surprise', A: this.props.emotion.surprise, fullMark: 1,
            },
        ];

        return (
            <div>
                <div>
                    <RadarChart outerRadius={100} width={400} height={300} data={emotionData}>
                        <PolarGrid />
                        <PolarAngleAxis dataKey="subject" />
                        <PolarRadiusAxis angle={90} domain={[0, 1]} />
                        <Radar dataKey="A" stroke="#8884d8" fill="#8884d8" fillOpacity={0.8} legendType="none" />
                        <Legend />
                    </RadarChart>
                </div>
            </div>
        )
    }
}