import React from 'react';
import './index.css';
import { Analyzer } from './Analyzer';
import { ImageUrl, ResultData, ResponseData } from './types';
import { ErrorId } from './types';
import { AnalyzeDetail } from './AnalyzeDetail';
import { AppBar, Button, CardContent, Grid, Typography } from '@material-ui/core';
import Toolbar from '@material-ui/core/Toolbar';
import Card from '@material-ui/core/Card';
import { AnalyzeDetailMean } from './AnalyzeDetailMean';

interface AnalyzeProps {
    checkedimage: ImageUrl;
    onSubmit: (e: React.ChangeEvent<HTMLFormElement>) => void;
    onClick: () => void;
    setErrorId: (id: number) => void;
}

interface AnalyzeResultState {
    responseData: ResponseData;
}

// 分析結果画面を表示するコンポーネント
export class AnalyzeResult extends React.Component<AnalyzeProps, AnalyzeResultState> {

    public constructor(props: AnalyzeProps) {
        super(props);
        this.state = {
            responseData: {
                total: 0,
                mean: {
                    faceAttributes: {
                        emotion: {
                            anger: 0,
                            contempt: 0,
                            disgust: 0,
                            fear: 0,
                            happiness: 0,
                            neutral: 0,
                            sadness: 0,
                            surprise: 0,
                        }
                    }
                },
                resultData: []
            }
        }
    }

    // 期待するURLの形であるかのチェック
    // 期待するURL
    //  ~~~/images/(ユーザー名)/(年)/(月)/(日)/(画像ファイル名)~~~ の形
    // ex) https://sample/images/nishikawa/2020/02/16/sample.jpg/~~~
    validateUrl(url: string) {
        const regex = /(images){1}\/.*\d{4}\/\d{2}\/\d{2}/;
        const str = String(url.match(regex));
        if (str === "null") {
            return false;
        }

        return true;
    }

    // URLから日付を返す
    // 期待するURL
    //  ~~~/images/(ユーザー名)/(年)/(月)/(日)/(画像ファイル名)~~~ の形
    // ex) https://sample/images/nishikawa/2020/02/16/sample.jpg/~~~
    getDate = (url: string) => {
        if (!this.validateUrl(url)) {
            return "-";
        }
        let strDate = String(url.match(/\d{4}\/\d{2}\/\d{2}/));
        return strDate;
    }

    // URLからユーザー名を返す
    // 期待するURL
    // ~~~/images//(ユーザー名)/(年)/(月)/(日)/(画像ファイル名)~~~ の形
    // ex) https://sample/images/nishikawa/2020/02/16/sample.jpg/~~~
    getName = (url: string) => {
        if (!this.validateUrl(url)) { // images/~~ の後にユーザー名が来ることを想定しているため"images/"を確認
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
                responseData: response,
            })
        })
    }

    public render() {
        const MAX_ANALYSIS_DISPLAY_LENGTH = 10;
        const resultData10 = this.state.responseData.resultData.slice(0, MAX_ANALYSIS_DISPLAY_LENGTH) || null;

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
                {resultData10.length > 0 &&
                    <div>
                        {
                            this.state.responseData.total > 1 ?
                                <div>
                                    <Typography component="h1" variant="h4" className="head_item">
                                        平均
                                    </Typography>
                                    <hr></hr>
                                    <Grid container spacing={1}>
                                        <Grid item className="analyze_grid_item">
                                            <Card>
                                                <AnalyzeDetailMean
                                                    img={this.props.checkedimage}
                                                    mean={this.state.responseData.mean}
                                                />
                                            </Card>
                                        </Grid>
                                    </Grid>

                                </div>
                                :
                                <div></div>
                        }
                        <Typography component="h1" variant="h4" className="head_item">
                            全員
                        </Typography>
                        <hr></hr>
                        {
                            resultData10.map((value, i: number) => {
                                return (
                                    <div key={i}>
                                        <Grid container spacing={1}>
                                            <Grid item className="analyze_grid_item">
                                                <Card>
                                                    <AnalyzeDetail
                                                        img={this.props.checkedimage}
                                                        resultData={value}
                                                        id={i}
                                                    />
                                                </Card>
                                            </Grid>
                                        </Grid>
                                        <CardContent />
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