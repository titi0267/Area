export const FORMAT = {
  youtubeChannelUrl: /https:\/+www.youtube.com\/(c|channel)\/(\w+)/,
  youtubeVideoUrl: /(\w+:\/+[\w+.]+\/)(watch\?v=)?([\w\-_]+)/,
  email: /[a-z._A-Z0-9]+@\w+.\w+/,
  githubIssueFormat: /([a-zA-Z0-9-%]+)\/([a-zA-Z0-9-%]+)\/([a-zA-Z0-9-% ]+)/,
  time: /([0-2][0-9]):([0-5][0-9])/,
  mailContent: /([a-z._A-Z0-9]+@\w+.\w+)\/([a-zA-Z0-9-%]+)\/([a-zA-Z0-9-% ]+)/,
  githubPullRequestFormat: /([a-zA-Z0-9-%]+)\/([a-zA-Z0-9-%]+)/,
  volumeFormat: /[1-9][0-9]?$|^100/,
};
