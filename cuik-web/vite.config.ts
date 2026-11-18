import { defineConfig } from 'vite';
import preact from '@preact/preset-vite';

import svgr from "vite-plugin-svgr";

export default defineConfig({
  build: {
    outDir: '../public',
    rollupOptions: {
      output: {
        entryFileNames: `[name].js`,
        chunkFileNames: `[name].js`,
        assetFileNames: `[name].[ext]`
      }
    }
  },
  plugins: [preact(), svgr()],
  // server: {
  //   proxy: {
  //     '/api': {
  //       target: 'http://localhost:8080/api',
  //       changeOrigin: true,
  //       rewrite: (path) => path.replace(/^\/api/, '')
  //     }
  //   }
  // }
});
