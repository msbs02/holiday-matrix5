
// make en comment le 10/05/2025 a 21:52
/*import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Matrix, MatrixEntry } from '../models/matrix';

@Injectable({
  providedIn: 'root'
})
export class MatrixService {
  private apiUrl = 'http://localhost:8080/api/matrices';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Matrix[]> {
    return this.http.get<Matrix[]>(this.apiUrl);
  }

  getById(id: number): Observable<Matrix> {
    return this.http.get<Matrix>(`${this.apiUrl}/${id}`);
  }

  getByYear(year: number): Observable<Matrix> {
    return this.http.get<Matrix>(`${this.apiUrl}/year/${year}`);
  }

  create(matrix: Matrix): Observable<Matrix> {
    return this.http.post<Matrix>(this.apiUrl, matrix);
  }

  addEntry(matrixId: number, entry: MatrixEntry): Observable<MatrixEntry> {
    return this.http.post<MatrixEntry>(`${this.apiUrl}/${matrixId}/entries`, entry);
  }

  validateByHOS(matrixId: number): Observable<Matrix> {
    return this.http.post<Matrix>(`${this.apiUrl}/${matrixId}/validate/hos`, {});
  }

  validateByDG(matrixId: number): Observable<Matrix> {
    return this.http.post<Matrix>(`${this.apiUrl}/${matrixId}/validate/dg`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}*/

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AnnualMatrix, AnnualMatrixRequest, ValidationStatus } from '../models/annual-matrix.model';
import {Matrix, MatrixEntry} from '../models/matrix';

@Injectable({
  providedIn: 'root'
})
export class MatrixService {
  private apiUrl = 'http://localhost:8080/api/matrices';

  constructor(private http: HttpClient) { }

  createMatrix(matrix: AnnualMatrixRequest): Observable<AnnualMatrix> {
    return this.http.post<AnnualMatrix>(this.apiUrl, matrix);
  }

  getMyMatrices(): Observable<AnnualMatrix[]> {
    return this.http.get<AnnualMatrix[]>(`${this.apiUrl}/my`);
  }

  getYearMatrices(year: number): Observable<AnnualMatrix[]> {
    return this.http.get<AnnualMatrix[]>(`${this.apiUrl}/year/${year}`);
  }

  updateHosValidation(id: number, status: ValidationStatus): Observable<AnnualMatrix> {
    return this.http.put<AnnualMatrix>(`${this.apiUrl}/${id}/hos-validation`, null, {
      params: { status: status.toString() }
    });
  }

  updateDgValidation(id: number, status: ValidationStatus): Observable<AnnualMatrix> {
    return this.http.put<AnnualMatrix>(`${this.apiUrl}/${id}/dg-validation`, null, {
      params: { status: status.toString() }
    });
  }

  // Ajouter cette méthode pour récupérer une matrice par ID
  /*getById(id: number): Observable<AnnualMatrix> {
    return this.http.get<AnnualMatrix>(`${this.apiUrl}/${id}`);
  }*/

// Ces méthodes sont des wrappers autour des méthodes existantes pour simplifier l'interface
  validateByHOS(matrixId: number | undefined, id: number): Observable<AnnualMatrix> {
    return this.updateHosValidation(id, ValidationStatus.APPROVED);
  }

  validateByDG(matrixId: number | undefined, id: number): Observable<AnnualMatrix> {
    return this.updateDgValidation(id, ValidationStatus.APPROVED);
  }

  getAll(): Observable<Matrix[]> {
    return this.http.get<Matrix[]>(this.apiUrl);
  }

  getById(id: number): Observable<Matrix> {
    return this.http.get<Matrix>(`${this.apiUrl}/${id}`);
  }

  getByYear(year: number): Observable<Matrix> {
    return this.http.get<Matrix>(`${this.apiUrl}/year/${year}`);
  }

  create(matrix: Matrix): Observable<Matrix> {
    return this.http.post<Matrix>(this.apiUrl, matrix);
  }

  addEntry(matrixId: number, entry: MatrixEntry): Observable<MatrixEntry> {
    return this.http.post<MatrixEntry>(`${this.apiUrl}/${matrixId}/entries`, entry);
  }
  // Ajoutez cette méthode pour implémenter la fonctionnalité de suppression
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}

