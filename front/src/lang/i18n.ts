import VueI18n from 'vue-i18n';
import vue from 'vue';

const en = require("./en.json");
const fr = require('./fr.json');

vue.use(VueI18n);

export default new VueI18n({
    locale: "en",
    fallbackLocale: ['en'],
    messages: {
        en: en,
        fr: fr,
    }
});
