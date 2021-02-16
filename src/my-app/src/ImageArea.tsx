import React from 'react';
import './index.css';
import { ImageUrl } from './types';
import { Checkbox, GridList, GridListTile, GridListTileBar, IconButton, ListSubheader } from '@material-ui/core';

interface BoardProps {
    images: Array<ImageUrl>;
    checkedImages: Array<boolean>
    onClick: (i: number) => void;
}

// ホーム画面内に画像リストを表示するコンポーネント
export class ImageArea extends React.Component<BoardProps>  {

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