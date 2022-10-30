import VueI18n from 'vue-i18n';
import vue from 'vue';

const en = require("./en.json");
const fr = require('./fr.json');

vue.use(VueI18n);

export default new VueI18n({
    locale: "en-US",
    fallbackLocale: ['en-US'],
    messages: {
        "en-US": en,
        "fr-FR": fr,
    }
});
