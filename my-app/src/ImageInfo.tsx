import React from 'react';
import { getSupportedCodeFixes } from 'typescript';
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
    image: Image
}

export class ImageInfo extends React.Component<ImageInfoProps, ImageInfoState> {
    root: HTMLDivElement | null | undefined;

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
        // let strDate: string = String(str.match(/(\d{4}\/\d{2}\/\d{2})/));
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
                <div ref={ref => this.root = ref}>
                    <img 
                        src={this.props.image.url}
                    />
                    <br />
                    <form>
                        <input type="checkbox" value={this.props.image.id} />
                    </form>
                {/* ユーザー名,日付 */}
                    {this.props.image.id}<br/>
                    {this.state.name}<br/>
                    {this.state.date}
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