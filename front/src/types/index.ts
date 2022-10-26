
/** Area interface */

export interface Area {
    state: Number,
    actionServiceId: Number,
    actionId: Number,
    actionParam: String,
    reactionServiceId: Number,
    reactionId: Number,
    reactionParam: String,
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
    id: Number;
    userId: Number;
    actionServiceId: Number;
    actionId: Number;
    actionParam: String;
    lastActionFetch: String;
    reactionServiceId: Number;
    reactionId: Number;
    reactionParam: String;
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
    value: String;
    error: String;
    valide: Boolean;
}
