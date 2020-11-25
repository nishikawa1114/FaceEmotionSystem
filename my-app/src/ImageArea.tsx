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
    images: Array<Image>
}

export class ImageArea extends React.Component<BoardProps>  {

    private renderImageInfo(i: number) {
        // console.log(this.props.images[i])
        return (
            <ImageInfo
                // url={this.props.images[i].url}
                image={this.props.images[i]}
            />
        );
    }

    public render() {
        const length = this.props.images.length;
        const maxRow = 4;

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