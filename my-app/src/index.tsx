import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Util } from './Util';
import { Error } from './Error';
import { AnalyzeResult } from './AnalyzeResult';
import { Image } from './types';
import { ErrorId } from './types';
import { Home } from './Home'

interface IndexState {
  inputUrl: string; // フォームに入力されたurl
  images: Array<Image> // 表示している画像のリスト
  checkedImages: Array<boolean> // 画像のチェック状態
  errorId: number; // 1:画像が存在しない, 2:分析結果が取得できない, 0:エラーなし
  displayId: number; // 1:ホーム画面, 2:分析結果画面, 0:エラー画面
}

enum DisplayId {
  HOME,
  ANALYZE,
  ERROR
}

export default class Index extends React.Component<{}, IndexState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      inputUrl: '',
      images: [],
      checkedImages: [],
      errorId: ErrorId.NOT_ERROR,
      displayId: DisplayId.HOME,
    }
  }

  // 表示ボタンを押下して、画像を表示
  private handleSubmit = async (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault();
    const { images, checkedImages } = this.state;
    const imageExists = await Util.imageExists(this.state.inputUrl);

    if (!imageExists) { // 画像が存在しない場合
      this.setState({
        errorId: ErrorId.ERROR_IMAGE_NOT_EXIST,
        displayId: DisplayId.ERROR,
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
      displayId: DisplayId.HOME, // ホーム画面へ
    })
  }

  // 分析ボタンを押下した場合の処理
  private handleSubmitAnalyze = (e: React.ChangeEvent<HTMLFormElement>) => {
    e.preventDefault(); // 
    this.setState({
      displayId: DisplayId.ANALYZE, // 分析画面へ
    })
  }

  // エラーIDをセットしてエラー画面に遷移
  public setErrrorId = (id: number) => {
    this.setState({
      errorId: id,
      displayId: DisplayId.ERROR, // エラー画面へ
    })
  }

  public render() {

    const isInputUrl: boolean = Util.isInput(this.state.inputUrl); // URLの入力済確認
    const images: Array<Image> = this.state.images;
    const displayId:number = this.state.displayId;
    // 画像のチェック数のカウント
    let countChecked: number = 0;
    let checkedId: number = 0;
    this.state.checkedImages.forEach((value, index) => {
      if (value === true) {
        countChecked++;
        checkedId = index + 1;
      }
    })
    const canAnalyze: boolean = countChecked === 1 ? true : false;

    if (displayId === DisplayId.HOME) {
      // ホーム画面
      return (
        <Home
          inputUrl={this.state.inputUrl}
          isInputUrl={isInputUrl}
          images={this.state.images}
          checkedImages={this.state.checkedImages}
          canAnalyze={canAnalyze}
          handleSubmit={this.handleSubmit}
          handleChange={this.handleChange}
          handleClick={this.handleClick}
          handleSubmitAnalyze={this.handleSubmitAnalyze}
        />
      )
    } else if (displayId === DisplayId.ANALYZE) {
      // 分析画面
      return (
        <AnalyzeResult
          checkedimage={images[checkedId - 1]}
          onSubmit={this.handleSubmitToHome}
          onClick={() => { }}
          setErrorId={this.setErrrorId}
        />
      )
    } else if (displayId === DisplayId.ERROR) {
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
  <Index />,
  document.getElementById('root')
);


