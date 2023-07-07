const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  mode: 'development',
  entry: './src/index.tsx',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist'),
    clean: true,
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js', 'jsx'],
  },
  module: {
    rules: [
      {
        test: /\.(js|ts|tsx)$/i,
        exclude: /node_modules/,
        use: {
          loader: 'ts-loader',
          options: {
            compilerOptions: { noEmit: false },
          },
        },
      },
      {
        test: /\.(jpg|png|gif|svg)$/,
        type: 'asset/resource',
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
    }),
  ],
  devtool: 'inline-source-map',
  devServer: {
    static: './dist',
    hot: true,
    open: true,
  },
};
