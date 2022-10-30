export default {
  preset: "@vue/cli-plugin-unit-jest/presets/typescript",
  setupFiles: ["<rootDir>/tests/unit/index.ts"],
  moduleFileExtensions: ["js", "ts", "json", "vue"],
  collectCoverageFrom: [
    "!<rootDir>/src/**/*.vue",
    "!<rootDir>/*.config.ts",
    "!<rootDir>/coverage/*/*",
    "!<rootDir>/coverage/*",
    "!<rootDir>/*.json",
    "!<rootDir>/src/lang/*.*",
    "!<rootDir>/src/main.ts",
  ],
};
