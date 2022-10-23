module.exports = {
    preset: '@vue/cli-plugin-unit-jest/presets/typescript',
    setupFiles: ["<rootDir>/tests/unit/index.ts"],
    moduleFileExtensions: [
        "js",
        "ts",
        "json",
        "vue"
    ],
}
