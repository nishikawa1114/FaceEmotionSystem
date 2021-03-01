import React from 'react';
import './index.css';
import { ImageUrl } from './types';
import { ImageArea } from './ImageArea';
import { Util } from './Util';
import AppBar from '@material-ui/core/AppBar';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { TextField } from '@material-ui/core';



interface HomeProps {
    inputUrl: string;
    images: Array<ImageUrl>;
    checkedImages: Array<boolean>
    canAnalyze: boolean;
    handleSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
    handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleClick: (i: number) => void;
    handleSubmitAnalyze: (e: React.ChangeEvent<HTMLFormElement>) => void;

}

interface HomeState {
    canDisplayImage: boolean;
}

// ホーム画面(最初の画面)を表示するコンポーネント
export class Home extends React.Component<HomeProps, HomeState> {

    public render() {
        const isInputUrl: boolean = Util.isInput(this.props.inputUrl); // URLの入力済確認
        const isMaxImages: boolean = this.props.images.length >= 40;

        return (
            <div className="back">
                <AppBar>
                    <Toolbar>
                        <Typography variant="h6" color="inherit">
                            Face Emotion System
                        </Typography>
                    </Toolbar>
                </AppBar>
                <Toolbar />

                {/* URL入力フォーム，表示ボタン */}
                <Grid/>

                <Grid className="input_area">
                     <form onSubmit={this.props.handleSubmit} autoComplete="on">
                        <TextField id="standard-basic" value={this.props.inputUrl} label="URLを入力してください" onChange={this.props.handleChange} className="url_form"/>
                        <Button type="submit" disabled={!isInputUrl || isMaxImages} variant="contained" color="primary" className="display_button">表示</Button>
                    </form>
                </Grid>
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
                        <Button type="submit" disabled={!this.props.canAnalyze} variant="contained" color="primary">分析</Button>
                    </form>
                </div>
            </div>
        )
    }
}