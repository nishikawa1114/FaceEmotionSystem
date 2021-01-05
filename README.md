# FaceEmotionSystem

## 後半課題①概要
* AzureFaceAPI呼び出し部分をJava(Spring Boot)に置き換える
* UIの仕様変更はなし

## 前半課題概要
* Azureに保存されたファイルのURLを指定してAzure FaceAPIをたたき、表情の分析結果を取得する
* 分析結果をReactで表示する

* URLを入力するフォームを表示する（画像はあらかじめAzure上に配置しておき、URLを指定するとアクセスできるようにしておく）
フォームに入力した画像を表示したい
* ユーザー名・日付を表示したい
  ~/agawa/2020/10/20/test.jpg 
* 画像は複数枚表示することができ、複数枚表示されている場合はセレクトボックスで選択することができる
* 画像を選択して分析ボタンを押下するとAzureのFaceAPIを使用して感情分析を行う
https://azure.microsoft.com/ja-jp/services/cognitive-services/face
* 分析結果をjsonで受け取り、結果をグラフで表示する（レーダーチャート？）
* グラフの表示にはライブラリを使用する
http://recharts.org/en-US
  * 選定理由: Reactのグラフ描画で最も使われているライブラリとのことなので。（githubのスター数が一番多い）

## want要件
1. 複数人が映っている画像に対しても、一つのグラフで表示することができる
2. 複数枚の画像を選択して分析を行うことができる