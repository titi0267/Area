
/** Area interface */

export interface Area {
    state: number,
    actionServiceId: number,
    actionId: number,
    actionParam: string,
    reactionServiceId: number,
    reactionId: number,
    reactionParam: string,
}

/** About.json interface */

export interface Service {
    id: number;
    serviceName: ServiceName;
    imageUrl: string;
    backgroundColor: string;
    actions: Action[];
    reactions: Reaction[];
}

interface Action {
    id: number;
    actionName: string;
    actionParamName: string;
}

interface Reaction {
    id: number;
    reactionName: string;
    reactionParamName: string;
}

export type ServiceName =
    | "Youtube"
    | "Twitter"
    | "Discord"
    | "Spotify"
    | "Github";

/** Area interface */

export interface Areas {
    id: number;
    userId: number;
    actionServiceId: number;
    actionId: number;
    actionParam: string;
    lastActionFetch: string;
    reactionServiceId: number;
    reactionId: number;
    reactionParam: string;
}

/** Authentification interfaces */

export interface Login {
    email: Field;
    password: Field;
}

export interface Register {
    firstName: Field,
    lastName: Field,
    email: Field,
    password: Field,
    confirmPassword: Field,
}

interface Field {
    value: string;
    error: string;
    valide: Boolean;
}
