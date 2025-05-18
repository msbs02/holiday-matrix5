export interface Comment {
  id?: number;
  content: string;
  author?: any;
  matrix?: any;
  holiday?: any;
  createdAt?: string;
}

export interface CommentRequest {
  content: string;
  matrixId?: number;
  holidayId?: number;
}
