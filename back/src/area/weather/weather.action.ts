import { Area } from "@prisma/client";
import axios from "axios";

import ENV from "../../env";
import { AreaService } from "../../services";
import { OpenWheatherCityInfos } from "../../types/areaServices/openWeather.types";
import * as ServiceHelper from "../../helpers/service.helpers";

const weatherBecameClear = async (area: Area): Promise<string | null> => {
  const weatherInfos = (
    await axios.get<OpenWheatherCityInfos>(
      `https://api.openweathermap.org/data/2.5/weather?q=${area.actionParam}&appid=${ENV.openWeatherApiKey}&units=metric`,
    )
  ).data;

  const params = {
    temperature: weatherInfos.main.temp,
    clouds: weatherInfos.clouds.all,
  };

  if (area.lastActionValue === null) {
    await AreaService.updateAreaValues(area.id, weatherInfos.weather.main);
    return null;
  }

  if (
    area.lastActionValue !== "Clear" &&
    weatherInfos.weather.main === "Clear"
  ) {
    await AreaService.updateAreaValues(area.id, weatherInfos.weather.main);
    return ServiceHelper.injectParamInReaction<typeof params>(
      area.reactionParam,
      params,
    );
  }

  await AreaService.updateAreaValues(area.id, weatherInfos.weather.main);

  return null;
};

export { weatherBecameClear };
