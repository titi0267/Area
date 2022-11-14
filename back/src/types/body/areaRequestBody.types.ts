export interface AreaBody {
  actionServiceId: number;
  actionId: number;
  actionParam: string;
  reactionServiceId: number;
  reactionId: number;
  reactionParam: string;
}

export interface EditAreaBody {
  areaId: number;
  enabled?: boolean;
  actionParam?: string;
  reactionParam?: string;
}
