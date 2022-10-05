export interface RawAreaBody {
  actionService: string | undefined;
  action: string | undefined;
  actionParam: string | undefined;
  reactionService: string | undefined;
  reaction: string | undefined;
  reactionParam: string | undefined;
  userId: number | undefined;
}

export interface FormatedAreaBody {
  actionService: string;
  action: string;
  actionParam: string;
  reactionService: string;
  reaction: string;
  reactionParam: string;
  userId: number;
}
