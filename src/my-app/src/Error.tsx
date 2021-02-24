import React from 'react';
import './index.css';
import { ErrorId } from './types';

interface errorProps {
    errorId: number;
    onSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
}

// エラー画面を表示するコンポーネント
export class Error extends React.Component<errorProps> {

    render() {

        let errorMessage: string;
        if (this.props.errorId === ErrorId.ERROR_IMAGE_NOT_EXIST) {
            errorMessage = '画像が存在しません';
        }else if (this.props.errorId === ErrorId.ERROR_ANALYZE_RESULT_EMPTY) {
            errorMessage = '分析結果が取得できませんでした';
        } else {
            errorMessage = '予期しないエラーが発生しました'
        }

        return (
            <div>
                <header>
                    <h1>Face Emotion System</h1>
                </header>
                <div className="error_message">
                    <h2>{errorMessage}</h2>
                    <form onSubmit={this.props.onSubmit}>
                        <button type="submit">ホーム画面へ戻る</button>
                    </form>
                </div>
            </div>
        )
    }
}