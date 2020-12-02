import React from 'react';
import './index.css';
import { ImageInfo } from './ImageInfo';

interface Image {
    id: number;
    url: string;
}

interface BoardProps {
    images: Array<Image>;
    onClick: (i: number) => void;
    checkedImage: Array<boolean>
}

export class ImageArea extends React.Component<BoardProps>  {

    // それぞれの画像を表示する
    private renderImageInfo(i: number) {
        return (
            <ImageInfo
                image={this.props.images[i]}
                onClick={() => this.props.onClick(i)}
                checked={this.props.checkedImage[i]}
            />
        );
    }

    public render() {
        const length = this.props.images.length;
        // 全ての画像を表示する
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