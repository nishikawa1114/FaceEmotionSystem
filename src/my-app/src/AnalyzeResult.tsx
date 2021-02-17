import React, { useState } from 'react';
import './index.css';
import { Analyzer } from './Analyzer';
import { ImageUrl, ResultData } from './types';
import { ErrorId } from './types';
import { AnalyzeDetail } from './AnalyzeDetail';
import { AppBar, Button, CardContent, Grid, Paper, Typography } from '@material-ui/core';
import Toolbar from '@material-ui/core/Toolbar';
import Card from '@material-ui/core/Card';
import { url } from 'inspector';

interface AnalyzeProps {
    checkedimage: ImageUrl;
    onSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
    onClick: () => void;
    setErrorId: (id: number) => void;
}

interface AnalyzeResultState {
    resultData: Array<ResultData>;
}

// 分析結果画面を表示するコンポーネント
export class AnalyzeResult extends React.Component<AnalyzeProps, AnalyzeResultState> {

    public constructor(props: AnalyzeProps) {
        super(props);
        this.state = {
            resultData: []
        }
    }

    // URLから日付を返す
    // 期待するURL
    //  ~~~/images/(ユーザー名)/(年)/(月)/(日)/(画像ファイル名)~~~ の形
    // ex) https://sample/images/nishikawa/2020/02/16/sample.jpg/~~~
    getDate = (url: string) => {
        let strDate = String(url.match(/\d{4}\/\d{2}\/\d{2}/));
        if (strDate === "null") {
            strDate = "-";
        }
        return strDate;
    }

    // URLからユーザー名を返す
    // 期待するURL
    // ~~~/images//(ユーザー名)/(年)/(月)/(日)/(画像ファイル名)~~~ の形
    // ex) https://sample/images/nishikawa/2020/02/16/sample.jpg/~~~
    getName = (url: string) => {
        const regex = /(images){1}\/.*\d{4}\/\d{2}\/\d{2}/;
        let tmp = String(url.match(regex));
        if (tmp === "null") { // images/~~ の後にユーザー名が来ることを想定しているため"images/"を確認
            return "-";
        }
        const temp: string = String(url.split('images/').pop());
        const name: string = String(temp.split('/').shift());
        return name;
    }

    // urlの画像を分析する
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
        const MAX_ANALYSIS_DISPLAY_LENGTH = 10;

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

                {/* 画像のユーザー名と日付を表示 */}
                <Grid className="image_info">
                    ユーザー：{this.getName(this.props.checkedimage.url)} | 日付: {this.getDate(this.props.checkedimage.url)}
                </Grid>

                {/* 分析結果を表示 */}
                {this.state.resultData.length > 0 &&
                    <div>
                        {

                            Array(this.state.resultData.slice(0, MAX_ANALYSIS_DISPLAY_LENGTH).length).fill(this.state.resultData.slice(0,10)).map((value, i: number) => {
                                return (
                                    <div key={i}>
                                        <Grid container spacing={1}>
                                            <Grid item className="analyze_grid_item">
                                                <Card>
                                                    <AnalyzeDetail
                                                        img={this.props.checkedimage}
                                                        resultData={value[i]}
                                                        id={i}
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
                {/* ホームへ戻るボタンの表示 */}
                <form onSubmit={this.props.onSubmit} className="home_button">
                    <Button type="submit" variant="contained" color="primary">ホーム画面へ戻る</Button>
                </form>
            </div>
        )
    }

}