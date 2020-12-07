import React from 'react';
import './index.css';
import { Image } from './types';
import { ImageArea } from './ImageArea';

// interface HomeState {
//     inputUrl: string; // フォームに入力されたurl
//     images: Array<Image> // 表示している画像のリスト
//     checkedImages: Array<boolean>
//     errorId: number; // 1:画像が存在しない, 2:分析結果が取得できない, 0:エラーなし
//     displayId: number; // 1:ホーム画面, 2:分析結果画面, 0:エラー画面
// }


interface HomeProps {
    inputUrl: string;
    isInputUrl: boolean;
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
                            disabled={!this.props.isInputUrl}
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