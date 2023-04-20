import {resolve} from 'path'
import {createHtmlPlugin} from 'vite-plugin-html'
import commonjs from '@rollup/plugin-commonjs';
import viteCompression from 'vite-plugin-compression';

import scalaVersion from './scala-version'

// https://vitejs.dev/config/
export default ({mode}) => {
  const mainJS = `modules/frontend/target/scala-${scalaVersion}/frontend-${mode === 'production' ? 'opt' : 'fastopt'}/main.js`
  const script = `<script type="module" src="/${mainJS}"></script>`

  /** @type {import('vite').UserConfig} */
  return {
    server: {
      port: 6080,
    },
    publicDir: './modules/frontend/src/main/public',
    optimizeDeps: {
      disabled: mode === 'production',
    },
    plugins: [
      ...(mode === 'production' ? [
        commonjs(),
        viteCompression({
          filter: /\.(js|css|html)$/i,
          algorithm: 'gzip'
        }),
        viteCompression({
          filter: /\.(js|css|html)$/i,
          algorithm: 'brotliCompress'
        }),
      ] : []),
      createHtmlPlugin({
        minify: mode === 'production',
        inject: {
          data: {
            script,
          },
        },
      }),
    ],
    resolve: {
      alias: {
        'stylesheets': resolve(__dirname, './modules/frontend/src/main/static/stylesheets'),
        'svg': resolve(__dirname, './modules/frontend/src/main/static/svg'),
      }
    }
  }
}
