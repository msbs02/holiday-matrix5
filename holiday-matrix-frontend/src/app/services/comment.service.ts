import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment, CommentRequest } from '../models/comment.model';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiUrl = 'http://localhost:8080/api/comments';

  constructor(private http: HttpClient) { }

  addComment(comment: CommentRequest): Observable<Comment> {
    return this.http.post<Comment>(this.apiUrl, comment);
  }

  getMatrixComments(matrixId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/matrix/${matrixId}`);
  }

  getHolidayComments(holidayId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/holiday/${holidayId}`);
  }
}
