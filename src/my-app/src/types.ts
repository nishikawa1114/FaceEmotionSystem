export interface ImageUrl {
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

export interface FaceAttributes {
    emotion: Emotion;
}

export interface FaceRectangle {
    top: number;
    left: number;
    width: number;
    height: number;
}

export interface ResultData {
    faceID: string;
    faceRectangle: FaceRectangle;
    faceAttributes: FaceAttributes;
}

export enum ErrorId {
    NOT_ERROR,
    ERROR_IMAGE_NOT_EXIST,
    ERROR_ANALYZE_RESULT_EMPTY
}