import React from 'react';
import './index.css';
import { ImageInfo } from './ImageInfo';

interface Image {
    id: number;
    url: string;
    // name: string;
    // date: Date;
}

interface BoardProps {
    // id: number;
    // url: string;
    // images: Array<Image>
    images: Array<Image>;
    onClick: (i: number) => void;
    checkedImage: Array<boolean>
}

export class ImageArea extends React.Component<BoardProps>  {

    private renderImageInfo(i: number) {
        return (
            <ImageInfo
                image={this.props.images[i]}
                onClick={() => this.props.onClick(i)}    
                checked={this.props.checkedImage[i]}            />
        );
    }

    public render() {
        const length = this.props.images.length;

        return (
            <div className="image_area">
                {/* {length}<br/> */}
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