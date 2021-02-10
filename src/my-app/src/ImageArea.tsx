import React from 'react';
import './index.css';
import { ImageInfo } from './ImageInfo';
import { ImageUrl } from './types';
import { Checkbox, GridList, GridListTile, GridListTileBar, IconButton, ListSubheader } from '@material-ui/core';

interface BoardProps {
    images: Array<ImageUrl>;
    checkedImages: Array<boolean>
    onClick: (i: number) => void;
}

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

    // それぞれの画像を表示する
    private renderImageInfo(i: number) {
        return (
            <ImageInfo
                image={this.props.images[i]}
                onClick={() => this.props.onClick(i)}
                checked={this.props.checkedImages[i]}
            />
        );
    }


    public render() {
        // 全ての画像を表示する
        const length = this.props.images.length;
        return (
            <div className="image_area">
                {/* {
                    Array(length).fill(this.props.images).map((value, i: number) => {
                        return (
                            this.renderImageInfo(i)
                        );
                    })
                } */}


                <div >
                    <GridList cols={4}>
                        {/* <GridListTile key="Subheader" rows={2} style={{ height: 'auto' }}>
                            <ListSubheader component="div">December</ListSubheader>
                        </GridListTile> */}
                        {
                            Array(length).fill(this.props.images).map((value, i: number) => (
                                <GridListTile key={value.url}>
                                    <img src={value[i].url} onClick={() => this.props.onClick(i)} className="image" />
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
                                            />
                                        }
                                    />
                                </GridListTile>
                            ))
                        }
                    </GridList>
                </div>

            </div>
        )
    }
}