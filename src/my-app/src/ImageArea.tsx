import React from 'react';
import './index.css';
import { ImageUrl } from './types';
import { Checkbox, GridList, GridListTile, GridListTileBar } from '@material-ui/core';

interface BoardProps {
    images: Array<ImageUrl>;
    checkedImages: Array<boolean>
    onClick: (i: number) => void;
}

// ホーム画面内に画像リストを表示するコンポーネント
export class ImageArea extends React.Component<BoardProps>  {

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
    //  ~~~/images/(ユーザー名)/(年)/(月)/(日)/(画像ファイル名)~~~ の形
    // ex) https://sample/images/nishikawa/2020/02/16/sample.jpg/~~~
    getName = (url: string) => {
        // if (!url.includes("/images/")) { // images/~~ の後にユーザー名が来ることを想定しているため"images/"を確認
        //     return "-";
        // }
        const regex = /(images)\/.*\d{4}\/\d{2}\/\d{2}/;
        let tmp = String(url.match(regex));
        if (tmp === "null") { // images/~~ の後にユーザー名が来ることを想定しているため"images/"を確認
            return "-";
        }
        const temp: string = String(url.split('images/').pop());
        const name: string = String(temp.split('/').shift());
        return name;
    }

    public render() {
        // 全ての画像を表示する
        const length = this.props.images.length;

        return (
            <div className="image_area">
                <GridList cols={4} key="image_list">
                    {
                        Array(length).fill(this.props.images).map((value, i: number) => (                            
                            <GridListTile key={i}>
                                <img src={value[i].url} onClick={() => this.props.onClick(i)} className="image" key={i} />
                                {/* 画像のユーザー名と日付を表示 */}
                                <GridListTileBar
                                    title={this.getName(value[i].url)}
                                    subtitle={this.getDate(value[i].url)}
                                    actionIcon={
                                        <Checkbox
                                            edge="end"
                                            color="secondary"
                                            value={this.props.images[i].url}
                                            checked={this.props.checkedImages[i]}
                                            className="image_checkbox"
                                            key={i}
                                        />
                                    }
                                />
                            </GridListTile>
                        ))
                    }
                </GridList>
            </div>
        )
    }
}