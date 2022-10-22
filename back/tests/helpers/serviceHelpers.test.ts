import { describe, test, expect } from "@jest/globals";
import httpStatus from "http-status";

import * as ServiceHelper from "../../src/helpers/service.helpers";

describe("Test reject invalid Area", () => {
  describe("Test working cases", () => {
    test("Reject invalid area body", async () => {
      try {
        ServiceHelper.rejectInvalidArea(12, 12, 12, 12);
      } catch (e) {
        expect(e.status).toBe(httpStatus.BAD_REQUEST);
      }
    });
  });
});

describe("Test getActionFct", () => {
  describe("Test valid cases", () => {
    test("Test if valid return a fct", () => {
      const action = ServiceHelper.getActionFct(1, 1);

      expect(typeof action).toBe("function");
    });

    test("Test with invalid action service", () => {
      const action = ServiceHelper.getActionFct(12, 12);

      expect(action).toBeNull();
    });

    test("Test with invalid action", () => {
      const action = ServiceHelper.getActionFct(1, 12);

      expect(action).toBeNull();
    });
  });
});

describe("Test getReactionFct", () => {
  describe("Test valid cases", () => {
    test("Test if valid return a fct", () => {
      const reaction = ServiceHelper.getReactionFct(3, 1);

      expect(typeof reaction).toBe("function");
    });

    test("Test with invalid reaction service", () => {
      const action = ServiceHelper.getReactionFct(12, 12);

      expect(action).toBeNull();
    });

    test("Test with invalid reaction", () => {
      const action = ServiceHelper.getReactionFct(1, 12);

      expect(action).toBeNull();
    });
  });
});

describe("Test getYoutubeVideoId", () => {
  describe("Test valid cases", () => {
    test("Test if return video id", () => {
      const videoId = ServiceHelper.getYoutubeVideoId(
        "https://www.youtube.com/watch?v=iC2i9n00C68",
      );

      expect(videoId).toBe("iC2i9n00C68");
    });

    test("Test with random link", () => {
      const videoId = ServiceHelper.getYoutubeVideoId(
        "https://prettier.io/docs/en/ignore.html",
      );
      expect(videoId).toBeNull();
    });
  });
});

describe("Test getYoutubeChannelName", () => {
  describe("Test valid cases", () => {
    test("Test if return channel name", () => {
      const channelName = ServiceHelper.getYoutubeChannelName(
        "https://www.youtube.com/user/Floowmecofficiel",
      );

      expect(channelName).toBe("Floowmecofficiel");
    });

    test("Test with random link", () => {
      const channelName = ServiceHelper.getYoutubeChannelName(
        "https://prettier.io/docs/en/ignore.html",
      );
      expect(channelName).toBeNull();
    });
  });
});
