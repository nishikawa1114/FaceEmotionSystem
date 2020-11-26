import React from 'react';
import './index.css';

interface Image {
    id: number;
    url: string;
    // name: string;
    // date: string;
}


interface ImageInfoState {
    id: number
    name: string
    date: string
    url: string
}

interface ImageInfoProps {
    image: Image;
    onClick: () => void;
    checked: boolean;
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

    getDate(str: string) {
        let strDate = String(str.match(/\d{4}\/\d{2}\/\d{2}/));
        return strDate;
    }

    getName(str: string) {
        let temp: string = String(str.split('images/').pop());
        let name: string = String(temp.split('/').shift());
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
                {this.props.image.id}<br />
                {this.state.name}<br />
                {this.state.date}
            </div>
        )
    }
}