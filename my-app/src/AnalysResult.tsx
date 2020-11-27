import React from 'react';
import './index.css';
import { ImageInfo } from './ImageInfo';
import { Analyser } from './Analyser';
import { resolve } from 'dns';

interface Image {
    id: number;
    url: string;
}

interface abalyzeProps {
    checkedimage: Image;
    onSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
    onClick: () => void;
}

export class AnalysResult extends React.Component<abalyzeProps> {

    private analyzeImage = async (str: string) => {
        let emotion = await Analyser.analyse(this.props.checkedimage.url);
        return emotion;
    }

    public async render() {
        let emotion: {} = await this.analyzeImage(this.props.checkedimage.url);

        console.log(Object.keys(emotion));

        return (
            <div>
                <header>
                    <h1>Face Emotion System</h1>
                </header>
                <h2>分析結果</h2>

                <div className="hidden_box">
                    <ImageInfo
                        image={this.props.checkedimage}
                        onClick={() => this.props.onClick()}
                        checked={false}
                    />
                    <ul>
                        {/* {Object.keys(emotion)} */}
                    </ul>
                </div>

                <form onSubmit={this.props.onSubmit}>
                    <button type="submit">ホーム画面へ戻る</button>
                </form>
            </div>
        )
    }

}