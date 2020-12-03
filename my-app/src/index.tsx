import React from 'react';
import ReactDOM from 'react-dom';
import { ImageArea } from './ImageArea';
import './index.css';
import { Util } from './Util';
import { Error } from './Error';
import { AnalysResult } from './AnalyzeResult';

interface Image {
  id: number;
  url: string;
}

interface HomeState {
  inputUrl: string; // フォームに入力されたurl
  images: Array<Image> // 表示している画像のリスト
  checkedImages: Array<boolean>
  errorId: number; // 1:画像が存在しない, 2:分析結果が取得できない, 0:エラーなし
  displayId: number; // 1:ホーム画面, 2:分析結果画面, 0:エラー画面
}

export default class Home extends React.Component<{}, HomeState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      inputUrl: '',
      images: [],
      checkedImages: [],
      errorId: 0,
      displayId: 1
    }
  }

  // 表示ボタンを押下して、画像を表示
  private handleSubmit = async (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
    const { images, checkedImages } = this.state;
    const exit = await Util.exitImage(this.state.inputUrl);

    if (!exit) { // 画像が存在しない場合
      this.setState({
        errorId: 1,
        displayId: 0,
      })
      return;
    }

    // 画像が存在する場合
    this.setState({
      images: images.concat({
        id: images.length + 1,
        url: this.state.inputUrl,
      }),
      checkedImages: checkedImages.concat(false),
      inputUrl: ''
    })

  }

  // URLフォームの内容をStateに反映
  private handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    this.setState({ inputUrl: e.target.value });
  }

  // 画像をクリックして画像を選択
  private handleClick = (i: number) => {
    const checkedImages = this.state.checkedImages;
    checkedImages[i] = !checkedImages[i];
    this.setState({ checkedImages: checkedImages });
  }

  // ホームへ戻るボタンを押下した場合の処理 (エラー画面)
  private handleSubmitToHome = (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
    const checkedImages = this.state.checkedImages;
    checkedImages.fill(false);
    this.setState({
      inputUrl: '',
      checkedImages: checkedImages,
      displayId: 1, // ホーム画面へ
    })
  }

  // 分析ボタンを押下した場合の処理
  private handleSubmitAnalyze = (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault(); // 
    this.setState({
      displayId: 2, // 分析画面へ
    })
  }

  // エラーIDをセットしてエラー画面に遷移
  public setErrrorId = (id: number) => {
    this.setState({
      errorId: id,
      displayId: 0, // エラー画面へ
    })
  }

  public render() {
    const isInputUrl: boolean = Util.isInput(this.state.inputUrl); // URLの入力済確認
    const images = this.state.images;
    const displayId = this.state.displayId;
    // 画像のチェック数のカウント
    let count: number = 0;
    let checkedId: number = 0;
    this.state.checkedImages.filter((value, index) => {
      if(value === true) {
        count++;
        checkedId = index + 1;
      }
    })
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
                disabled={!isInputUrl}
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
                  checkedImages={this.state.checkedImages}
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
        </div>
      )
    } else if (displayId === 2) {
      // 分析画面
      return (
        <AnalysResult
          checkedimage={images[checkedId - 1]}
          onSubmit={this.handleSubmitToHome}
          onClick={() => { }}
          setErrorId={this.setErrrorId}
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


