module.exports = {
  verbose: true,// 結果の出力が見やすくなる
  transform: {
      '.*\\.(ts|tsx)$' : '<rootDir>/node_modules/ts-jest',    // TypeScriptファイルをテストする場合
      '.+\\.(css|styl|less|sass|scss|png|jpg|ttf|woff|woff2)$': 'jest-transform-stub', // CSSはtranceformしない
  },
  moduleFileExtensions: ['js', 'jsx', 'ts', 'tsx', "css", 'json'] 
}