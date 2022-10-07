export interface RawAreaBody {
  actionServiceId: string | undefined;
  actionId: string | undefined;
  actionParam: string | undefined;
  reactionServiceId: string | undefined;
  reactionId: string | undefined;
  reactionParam: string | undefined;
  userId: string | undefined;
}

export interface FormatedAreaBody {
  actionServiceId: number;
  actionId: number;
  actionParam: string;
  reactionServiceId: number;
  reactionId: number;
  reactionParam: string;
  userId: number;
}
