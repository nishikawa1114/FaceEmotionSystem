import React from 'react';
import './index.css';
import { ImageInfo } from './ImageInfo';
import { Image } from './types';

interface BoardProps {
    images: Array<Image>;
    checkedImages: Array<boolean>
    onClick: (i: number) => void;
}

export class ImageArea extends React.Component<BoardProps>  {

    // それぞれの画像を表示する
    private renderImageInfo(i: number) {
        return (
            <div key={this.props.images[i].id}>
                <ImageInfo
                    image={this.props.images[i]}
                    onClick={() => this.props.onClick(i)}
                    checked={this.props.checkedImages[i]}
                />
            </div>
        );
    }

    public render() {
        // 全ての画像を表示する
        const length = this.props.images.length;
        return (
            <div className="image_area">
                {
                    Array(length).fill(this.props.images).map((value, i: number) => {
                        return (
                            this.renderImageInfo(i)
                        );
                    })
                }
            </div>
        )
    }
}