export interface Image {
    id: number;
    url: string;
}

export interface Emotion {
    anger: number;
    contempt: number;
    disgust: number;
    fear: number;
    happiness: number;
    neutral: number;
    sadness: number;
    surprise: number;
}

export enum ErrorId {
    NOT_ERROR,
    ERROR_IMAGE_NOT_EXIST,
    ERROR_ANALYZE_RESULT_EMPTY
}



