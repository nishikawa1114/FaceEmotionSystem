import React from 'react';
import './index.css';

interface Image {
    id: number;
    url: string;
    // name: string;
    // date: Date;
}


interface ImageInfoState {
    id: number
    name: string
    date: Date
    url: string
}

interface ImageInfoProps {
    // id: number
    url: string
    // image: Image
}

export class ImageInfo extends React.Component<ImageInfoProps, ImageInfoState> {

    render() {
        return (
            <div className="image_info">
                {/* 画像 */}
                <div>
                    <img src="this.props.url" alt="face"/><br/>
                    {this.props.url}
                {/* ユーザー名,日付 */}
                </div>
                <div>

                </div>
            </div>
        )
    }
}
















// class ImageIngo {
//     private _id: number
//     private _name: string
//     private _date: Date
//     private _url: string

//     constructor(id: number, name: string, date: Date, url: string) {
//         this._id = id
//         this._name = name
//         this._date = date
//         this._url = url
//     }

//     get id() {
//         return this._id
//     }

//     get name() {
//         return this._name
//     }

//     get date() {
//         return this._date
//     }

//     get url() {
//         return this._url
//     }
// }