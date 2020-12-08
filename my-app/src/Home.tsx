import React from 'react';
import './index.css';
import { Image } from './types';
import { ImageArea } from './ImageArea';
import { Util } from './Util';

interface HomeProps {
    inputUrl: string;
    images: Array<Image>;
    checkedImages: Array<boolean>
    canAnalyze: boolean;
    handleSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
    handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleClick: (i: number) => void;
    handleSubmitAnalyze: (e: React.ChangeEvent<HTMLFormElement>) => void;

}

export class Home extends React.Component<HomeProps> {

    public render() {

    const isInputUrl: boolean = Util.isInput(this.props.inputUrl); // URLの入力済確認


        return (
            <div>
                <header>
                    <h1>Face Emotion System</h1>
                </header>
                {/* URL入力フォーム，表示ボタン */}
                <div className="url_form">
                    <form onSubmit={this.props.handleSubmit}>
                        <input type="text" name="url"
                            placeholder="URLを入力してください"
                            value={this.props.inputUrl}
                            onChange={this.props.handleChange}
                        />
                        <button type="submit"
                            className="display_button"
                            disabled={!isInputUrl}
                        >
                            表示
                </button>
                    </form>
                </div>
                {/* 画像表示領域 */}
                <div className="images">
                    {
                        this.props.images ?
                            <ImageArea
                                images={this.props.images}
                                onClick={(i) => this.props.handleClick(i)}
                                checkedImages={this.props.checkedImages}
                            />
                            :
                            <div></div>
                    }
                </div>
                {/* 分析ボタン */}
                <div className="analyze_button">
                    <form onSubmit={this.props.handleSubmitAnalyze}>
                        <button disabled={!this.props.canAnalyze}>分析</button>
                    </form>
                </div>
            </div>
        )
    }
}