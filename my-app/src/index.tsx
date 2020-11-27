import React from 'react';
import ReactDOM from 'react-dom';
import { ImageArea } from './ImageArea';
import './index.css';
import { Util } from './Util';
import { Error } from './Error';
import { AnalysResult } from './AnalysResult';

interface Image {
  id: number;
  url: string;
}

interface HomeState {
  inputUrl: string;
  images: Array<Image>
  checkedImage: Array<boolean>
  errorId: number;
  isError: boolean;
  displayId: number;
}

export default class Home extends React.Component<{}, HomeState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      inputUrl: '',
      images: [],
      checkedImage: [],
      errorId: 0,
      isError: false,
      displayId: 1
    }
  }

  // 表示ボタンを押下した場合の処理
  private handleSubmit = async (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
    const images = this.state.images;
    const checkedImage = this.state.checkedImage;
    const exit = await Util.exitImage(this.state.inputUrl);

    if (exit) { // 画像が存在する場合
      this.setState({
        images: images.concat({
          id: images.length + 1,
          url: this.state.inputUrl,
        }),
        checkedImage: checkedImage.concat(false),
        inputUrl: ''
      })
    } else { // 画像が存在しない場合
      this.setState({
        errorId: 1,
        isError: true,
        displayId: 0,
      })
    }
  }

  // URLフォームの内容を変更した場合の処理
  private handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({ inputUrl: e.target.value });
  }

  // 画像をクリックした場合の処理
  private handleClick = (i: number) => {
    const checkedImage = this.state.checkedImage;
    checkedImage[i] = !checkedImage[i];
    this.setState({ checkedImage: checkedImage });
  }

  // ホームへ戻るボタンを押下した場合の処理 (エラー画面)
  private handleSubmitToHome = (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
    const checkedImage = this.state.checkedImage;
    checkedImage.fill(false);
    this.setState({
      isError: false,
      inputUrl: '',
      checkedImage: checkedImage,
      displayId: 1,
    })
  }

  // 分析ボタンを押下した場合の処理
  // 今は選択画像の情報をコンソールに表示するだけ
  // 分析機能は今後実装予定
  private handleSubmitAnalyze = (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault(); // 
    let count: number = 0;
    // let checkId: number = 0;
    for (let i: number = 0; i < this.state.checkedImage.length; ++i) {
      if (this.state.checkedImage[i] === true) {
        count++;
        // checkId = i + 1;
      }
    }

    this.setState({
      displayId: 2,
    })

    // console.log('チェック済:' + count + '個');
    // console.log('画像ID : ' + this.state.images[checkId - 1].id);
    // console.log('画像URL : ' + this.state.images[checkId - 1].url);
  }

  public render() {
    const isInputUrl: boolean = Util.isInput(this.state.inputUrl); // URLの入力済確認
    const images = this.state.images;
    const displayId = this.state.displayId;
    // 画像のチェック数のカウント
    let count: number = 0;
    let checkedId: number = 0;
    for (let i: number = 0; i < this.state.checkedImage.length; ++i) {
      if (this.state.checkedImage[i] === true) {
        count++;
        checkedId = i + 1;
      }
    }
    const canAnalyze: boolean = count === 1 ? true : false;

    if (displayId === 1) {
      // ホーム画面
      return (
        <div>
          <header>
            <h1>Face Emotion System</h1>
          </header>
          {/* URL入力フォーム，表示ボタン */}
          <div className="url_form">
            <form onSubmit={this.handleSubmit}>
              <input type="text" name="url"
                placeholder="URLを入力してください"
                value={this.state.inputUrl}
                onChange={this.handleChange}
              />
              <button type="submit"
                className="display_button"
                disabled={isInputUrl}
              >
                表示
            </button>
            </form>
          </div>
          {/* 画像表示領域 */}
          <div className="images">
            {
              images ?
                <ImageArea
                  images={images}
                  onClick={(i) => this.handleClick(i)}
                  checkedImage={this.state.checkedImage}
                />
                :
                <div></div>
            }
          </div>
          {/* 分析ボタン */}
          <div className="analyze_button">
            <form onSubmit={this.handleSubmitAnalyze}>
              <button disabled={!canAnalyze}>分析</button>
            </form>
          </div>
          {/* 後で消す
          <div>
            <form onSubmit={this.handleSubmitToError}>
              <button type="submit">エラー画面へ移動</button>
            </form>
          </div> */}
        </div>
      )
    } else if (displayId === 2) {
      // 分析画面
      return (
        <AnalysResult
          checkedimage={images[checkedId - 1]}
          onSubmit={this.handleSubmitToHome}
          onClick={() => {}}
        />
      )
    } else if (displayId === 0) {
        // エラー画面
        return (
        <Error
          errorId={this.state.errorId}
          onSubmit={this.handleSubmitToHome}
        />
      )
    }
  }
}

// ========================================

ReactDOM.render(
  <Home />,
  document.getElementById('root')
);


