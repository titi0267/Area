import { describe, test, expect } from "@jest/globals";
import httpStatus from "http-status";

import * as ServiceHelper from "../../src/helpers/service.helpers";
import { TokenService, UserService } from "../../src/services";

describe("Test reject invalid Area", () => {
  describe("Test working cases", () => {
    test("Reject invalid area body", async () => {
      try {
        ServiceHelper.rejectInvalidArea(12, 12, "", 12, 12, "");
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
      const reaction = ServiceHelper.getReactionFct(2, 1);

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
        "https://www.youtube.com/c/Floowmecofficiel",
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

describe("Test getGithubIssueParams", () => {
  describe("Test valid cases", () => {
    test("Test if return return issue params", () => {
      const channelName = ServiceHelper.getGithubIssueParams(
        "ludovic-str/test/lol",
      );

      expect(channelName?.owner).toBe("ludovic-str");
    });

    test("Test with missing text", () => {
      const channelName = ServiceHelper.getGithubIssueParams("ludovic-str/");
      expect(channelName).toBeNull();
    });
  });
});

describe("Test getMailContentParams", () => {
  describe("Test valid cases", () => {
    test("Test if return mail params", () => {
      const mailContentParam = ServiceHelper.getMailContentParams(
        "ludovic@gmail.com/test/lol",
      );

      expect(mailContentParam?.to).toBe("ludovic@gmail.com");
    });

    test("Test with missing text", () => {
      const mailContentParam = ServiceHelper.getMailContentParams("ludo");
      expect(mailContentParam).toBeNull();
    });
  });
});

describe("Test injectParamInReaction", () => {
  describe("Test valid cases", () => {
    test("Test inject valid string field", () => {
      const test = { name: "Ludo" };
      const str = ServiceHelper.injectParamInReaction<typeof test>(
        "My name is %name% !",
        test,
      );

      expect(str).toBe("My name is Ludo !");
    });

    test("Test inject valid number field", () => {
      const test = { age: 12 };
      const str = ServiceHelper.injectParamInReaction<typeof test>(
        "I'm %age% !",
        test,
      );

      expect(str).toBe("I'm 12 !");
    });

    test("Test inject multiple valid fields", () => {
      const test = { age: 12, name: "ludo" };
      const str = ServiceHelper.injectParamInReaction<typeof test>(
        "I'm %age% et mon nom est %name% !",
        test,
      );

      expect(str).toBe("I'm 12 et mon nom est ludo !");
    });

    test("Test inject empty object field", () => {
      const test = {};
      const str = ServiceHelper.injectParamInReaction<typeof test>(
        "I'm %age% !",
        test,
      );

      expect(str).toBe("I'm %age% !");
    });

    test("Test with no insertion tokens", () => {
      const test = {};
      const str = ServiceHelper.injectParamInReaction<typeof test>(
        "I'm age !",
        test,
      );

      expect(str).toBe("I'm age !");
    });
  });
});

describe("Test getGoogleOauthClient", () => {
  describe("Test valid cases", () => {
    test("Test to get a valid oauth client", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      await TokenService.setGoogleToken(users[0].id, "test");

      const oauthClient = await ServiceHelper.getGoogleOauthClient(users[0].id);

      expect(oauthClient).not.toBeNull();

      await UserService.removeUserById(users[0].id);
    });

    describe("Test Error cases", () => {
      test("Test to get a valid oauth client with invalid user", async () => {
        const oauthClient = await ServiceHelper.getGoogleOauthClient(12);

        expect(oauthClient).toBeNull();
      });
    });
  });
});

describe("Test getGithubClient", () => {
  describe("Test valid cases", () => {
    test("Test to get a valid oauth client", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      await TokenService.setGithubToken(users[0].id, "test");

      const oauthClient = await ServiceHelper.getGithubClient(users[0].id);

      expect(oauthClient).not.toBeNull();

      await UserService.removeUserById(users[0].id);
    });

    describe("Test Error cases", () => {
      test("Test to get a valid oauth client with invalid user", async () => {
        const oauthClient = await ServiceHelper.getGithubClient(12);

        expect(oauthClient).toBeNull();
      });
    });
  });
});

describe("Test getSpotifyClient", () => {
  describe("Test valid cases", () => {
    test("Test to get a valid oauth client", async () => {
      await UserService.createUser("Ludo", "Str", "test@mail.com", "passwd");
      const users = await UserService.getAllUsers();

      await TokenService.setSpotifyToken(users[0].id, "test");

      const spotifyCredential = await ServiceHelper.getSpotifyClient(
        users[0].id,
      );

      expect(spotifyCredential).not.toBeNull();

      await UserService.removeUserById(users[0].id);
    });

    describe("Test Error cases", () => {
      test("Test to get a valid oauth client with invalid user", async () => {
        const oauthClient = await ServiceHelper.getSpotifyClient(12);

        expect(oauthClient).toBeNull();
      });
    });
  });
});

describe("Test action Format", () => {
  describe("Test valid cases", () => {
    test("Test with a valid action format", async () => {
      const isWellFormated = ServiceHelper.checkActionFormat(
        1,
        1,
        "https://www.youtube.com/c/VilebrequinAuto",
      );

      expect(isWellFormated).toBe(true);
    });

    test("Test with a valid action with no require format", async () => {
      const isWellFormated = ServiceHelper.checkActionFormat(1, 3, "");

      expect(isWellFormated).toBe(true);
    });

    test("Test with a invalid action", async () => {
      const isWellFormated = ServiceHelper.checkActionFormat(40, 40, "test");

      expect(isWellFormated).toBe(false);
    });

    test("Test with a invalid action format", async () => {
      const isWellFormated = ServiceHelper.checkActionFormat(1, 1, "test");

      expect(isWellFormated).toBe(false);
    });
  });
});

describe("Test reaction Format", () => {
  describe("Test valid cases", () => {
    test("Test with a valid reaction format", async () => {
      const isWellFormated = ServiceHelper.checkReactionFormat(
        4,
        1,
        "ludovic-str/test/test",
      );

      expect(isWellFormated).toBe(true);
    });

    test("Test with a invalid reaction", async () => {
      const isWellFormated = ServiceHelper.checkReactionFormat(40, 90, "test");

      expect(isWellFormated).toBe(false);
    });

    test("Test with a invalid reaction format", async () => {
      const isWellFormated = ServiceHelper.checkReactionFormat(4, 1, "test");

      expect(isWellFormated).toBe(false);
    });
  });
});
