import React from 'react';
import './index.css';
import { ErrorId } from './types';
import { AppBar, Button, Typography } from '@material-ui/core';
import Toolbar from '@material-ui/core/Toolbar';

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
                <AppBar>
                    <Toolbar>
                        <Typography variant="h6" color="inherit">
                            Face Emotion System
                        </Typography>
                    </Toolbar>
                </AppBar>
                <Toolbar />
                <div className="error_message">
                    <h2>{errorMessage}</h2>
                    <form onSubmit={this.props.onSubmit}>
                        <Button type="submit" variant="contained" color="primary">ホーム画面へ戻る</Button>
                    </form>
                </div>
            </div>
        )
    }
}