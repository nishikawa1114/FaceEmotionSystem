import React, { useState } from 'react';
import './index.css';
import { ImageInfo } from './ImageInfo';
import { Analyzer } from './Analyzer';
import { ImageUrl, ResultData } from './types';
import { ErrorId } from './types';
import { AnalyzedDetail } from './AnalyzedDetail';
import { AppBar, Button, CardContent, Grid, Paper, Typography } from '@material-ui/core';
import Toolbar from '@material-ui/core/Toolbar';
import Card from '@material-ui/core/Card';

interface AnalyzeProps {
    checkedimage: ImageUrl;
    onSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
    onClick: () => void;
    setErrorId: (id: number) => void;
}

interface AnalyzeResultState {
    resultData: Array<ResultData>;
}

export class AnalyzeResult extends React.Component<AnalyzeProps, AnalyzeResultState> {

    private constructor(props: AnalyzeProps) {
        super(props);
        this.state = {
            resultData: []
        }
    }

    // URLから日付を返す
    getDate = (str: string) => {
        const strDate = String(str.match(/\d{4}\/\d{2}\/\d{2}/));
        return strDate;
    }

    // URLからユーザー名を返す
    getName = (str: string) => {
        const temp: string = String(str.split('images/').pop());
        const name: string = String(temp.split('/').shift());
        return name;
    }

    private analyzeImage = async (imageUrl: string) => {
        const emotion = await Analyzer.analyze(imageUrl);
        return emotion;
    }

    public async componentDidMount() {
        this.analyzeImage(this.props.checkedimage.url).then((response: any) => {
            // 画像の分析ができない場合、APIにアクセスできない場合
            if (!Object.keys(response).length) {
                this.props.setErrorId(ErrorId.ERROR_ANALYZE_RESULT_EMPTY);
                return;
            }

            this.setState({
                resultData: response,
            })
        })
    }

    public render() {
        return (
            <div className="back">

                <AppBar className="title_bar">
                    <Toolbar>
                        <Typography variant="h6" color="inherit">
                            Face Emotion System
                        </Typography>
                    </Toolbar>
                    <Toolbar>
                        <Typography variant="h6" color="inherit">
                            分析結果
                        </Typography>
                    </Toolbar>
                </AppBar>

                <Toolbar />
                <Toolbar />

                <Grid className="image_info">
                    ユーザー：{this.getName(this.props.checkedimage.url)} | 日付: {this.getDate(this.props.checkedimage.url)}
                </Grid>

                {this.state.resultData.length > 0 &&
                    <div className="">
                        {
                            Array(this.state.resultData.length).fill(this.state.resultData).map((value, i: number) => {
                                return (
                                    <div>
                                        <Grid container spacing={1}>
                                            <Grid item className="analyze_grid_item">
                                                <Card>
                                                    <AnalyzedDetail
                                                        img={this.props.checkedimage}
                                                        resultData={value[i]}
                                                        index={i}
                                                    />
                                                </Card>
                                            </Grid>
                                        </Grid>
                                        <CardContent>

                                        </CardContent>

                                    </div>
                                )
                            })
                        }

                    </div>
                }
                <form onSubmit={this.props.onSubmit} className="home_button">
                    <Button type="submit" variant="contained" color="primary">ホーム画面へ戻る</Button>
                </form>
            </div>
        )
    }

}