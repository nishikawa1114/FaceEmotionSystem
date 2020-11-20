import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';


interface HomeState {
  inputUrl: string;
}

export default class Home extends React.Component<{}, HomeState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      inputUrl: 'abc',
    }
  }

  private handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    this.setState({ inputUrl: e.target.value });
    console.log(this.state.inputUrl);
  }

  private handleClick(e: React.ChangeEvent<HTMLButtonElement>) {
    console.log(this.state.inputUrl);
  }

  public render() {
    return (
      <><h1>Face Emotion System</h1>
        {/* URL入力フォーム，表示ボタン */}
        <div className="url_form">
          <form>
            <input type="text" name="url" value={this.state.inputUrl} onChange={(e) => this.handleChange}></input>
            <button className="display_button" onClick={() => this.handleClick}>表示</button>
          </form>
        </div>
        {/* 画像表示領域 */}
        <div className="images">

        </div>
        {/* 分析ボタン */}
        <div className="analuze_button">
          <form>
            <button>分析</button>
          </form>
        </div>

      </>
    )
  }


}

// ========================================

ReactDOM.render(
  <Home />,
  document.getElementById('root')
);


