import React from 'react';
import ReactDOM from 'react-dom';
import { ImageArea } from './ImageArea';
import './index.css';
import { Util } from './Util';

interface Image {
  id: number;
  url: string;
  // name: string;
  // date: Date;
}

interface HomeState {
  inputUrl: string;
  // images: Array<Image>
  images: Array<Image>
}

export default class Home extends React.Component<{}, HomeState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      inputUrl: '',
      images: []
    }
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  private handleSubmit(e: React.ChangeEvent<HTMLFormElement>) {
    e.preventDefault();
    const images = this.state.images;
    // const img = new Image();
    Util.exitImageUrl(this.state.inputUrl);
    this.setState({
      images: images.concat({
        id: images.length + 1,
        url: this.state.inputUrl,
      }),
      inputUrl: ''
    })

  }

  private handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    this.setState({ inputUrl: e.target.value });
  }

  public render() {

    let isInputUrl: boolean = Util.isInput(this.state.inputUrl); // URLが入力済確認
    const images = this.state.images;
    let isError = true;

    return (
      isError?
      <div>
        <header>
          <h1>Face Emotion System</h1>
        </header>
        {/* URL入力フォーム，表示ボタン */}
        <div className="url_form">
          <form onSubmit={this.handleSubmit}>
            <input type="text" name="url" placeholder="URLを入力してください" value={this.state.inputUrl} onChange={this.handleChange} />
            <button type="submit" className="display_button" disabled={isInputUrl}>表示</button>
          </form>
        </div>
        {/* 画像表示領域 */}
        <div className="images">
          {
            images?
            (<ImageArea
              images={images}
            />)
            :
            <div></div>
          }
        </div>
        {/* 分析ボタン */}
        <div className="analyze_button">
          <form>
            <button>分析</button>
          </form>
        </div>
      </div>
      :
      <div>エラー画面</div>
    )
  }
}


// ========================================

ReactDOM.render(
  <Home />,
  document.getElementById('root')
);


