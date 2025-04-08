module.exports = {
  root: true,
  env: {
    node: true,
    browser: true,
  },
  extends: [
    'eslint:recommended',
    'plugin:vue/vue3-recommended',
    'plugin:prettier/recommended',
  ],
  parserOptions: {
    parser: 'babel-eslint',
  },
  rules: {
    // Override or add your custom rules here
    'vue/valid-v-bind': 'error',
    'vue/no-unused-vars': 'warn',
    'vue/no-v-model-argument': 'off',
    'vue/singleline-html-element-content-newline': 'off',
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    // Add any other custom rules you need
  },
}
