# ベースイメージの作成
FROM node:12.18.4
# コンテナ内で作業するディレクトリを指定
WORKDIR /usr/src/app
# package.jsonとyarn.lockを/usr/src/appにコピー
COPY ["package.json", "package-lock.json", "./"]
# パッケージをインストール
RUN npm install
# ファイルを全部作業用ディレクトリにコピー
COPY . .
# コンテナを起動する際に実行されるコマンド
ENTRYPOINT [ "npm", "start" ]