import React from 'react';
import './index.css';
import { Image } from './types';

interface ImageInfoState {
    id: number
    name: string
    date: string
    url: string
}

interface ImageInfoProps {
    image: Image;
    checked: boolean;
    onClick: () => void;
}

export class ImageInfo extends React.Component<ImageInfoProps, ImageInfoState> {
    public constructor(props: ImageInfoProps) {
        super(props);
        this.state = {
            id: props.image.id,
            name: this.getName(props.image.url),
            date: this.getDate(props.image.url),
            url: this.props.image.url,
        }
    }

    // URLから日付を返す
    getDate(str: string) {
        const strDate = String(str.match(/\d{4}\/\d{2}\/\d{2}/));
        return strDate;
    }

    // URLからユーザー名を返す
    getName(str: string) {
        const temp: string = String(str.split('images/').pop());
        const name: string = String(temp.split('/').shift());
        return name;
    }

    render() {
        return (
            <div className="image_info">
                {/* 画像 */}
                <img
                    src={this.props.image.url}
                    className="image"
                    onClick={this.props.onClick}
                    alt={this.props.image.url}
                />
                <form>
                    <input
                        type="checkbox"
                        className="image_checkbox"
                        value={this.props.image.url}
                        checked={this.props.checked}
                    />
                </form>
                {/* ユーザー名,日付 */}
                {this.state.name}<br />
                {this.state.date}
            </div>
        )
    }
}