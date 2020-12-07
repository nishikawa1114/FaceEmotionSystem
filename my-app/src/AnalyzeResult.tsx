import React from 'react';
import './index.css';
import { ImageInfo } from './ImageInfo';
import { Analyzer } from './Analyzer';
import { Graph } from './Graph';
import { Table } from './Table';
import { Image } from './types';
import { Emotion } from './types';
import { ErrorId } from './types';

interface AnalyzeProps {
    checkedimage: Image;
    onSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
    onClick: () => void;
    setErrorId: (id: number) => void;
}

interface AnalyzeResultState {
    emotion: Emotion;
}

export class AnalyzeResult extends React.Component<AnalyzeProps, AnalyzeResultState> {

    private constructor(props: AnalyzeProps) {
        super(props);
        this.state = {
            emotion: {
                anger: 0,
                contempt: 0,
                disgust: 0,
                fear: 0,
                happiness: 0,
                neutral: 0,
                sadness: 0,
                surprise: 0,
            },
        }
    }

    private analyzeImage = async (str: string) => {
        const emotion = await Analyzer.analyze(str);
        return emotion;
    }

    public async componentDidMount() {
        this.analyzeImage(this.props.checkedimage.url).then((response: any) => {
            // 分析結果が空の場合
            if (!Object.keys(response).length) {
                this.props.setErrorId(ErrorId.ERROR_ANALYZE_RESULT_EMPTY);
                return;
            }

            this.setState({
                emotion: response,
            })
        })
    }

    public render() {
        return (
            <div>
                <header>
                    <h1>Face Emotion System</h1>
                </header>
                <h2>分析結果</h2>

                <div className="result">
                    <div className="hidden_box">
                        <ImageInfo
                            image={this.props.checkedimage}
                            onClick={() => this.props.onClick()}
                            checked={false}
                        />
                    </div>

                    <div className="graph">
                        <Graph
                            emotion={this.state.emotion}
                        />
                    </div>

                    <div>
                        <Table
                            emotion={this.state.emotion}
                        />
                    </div>
                </div>

                <form onSubmit={this.props.onSubmit} className="home_button">
                    <button type="submit">ホーム画面へ戻る</button>
                </form>
            </div>
        )
    }

}