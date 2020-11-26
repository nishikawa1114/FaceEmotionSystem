import React from 'react';
import './index.css';


interface errorProps {
    errorId: number;
    onSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
}

export class Error extends React.Component<errorProps> {

    render() {

        let errorMessage: string;
        if (this.props.errorId == 1) {
            errorMessage = '画像が存在しません';
        } else {
            errorMessage = 'エラー'
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