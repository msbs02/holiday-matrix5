import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatrixService } from '../../services/matrix.service';
import { Matrix } from '../../models/matrix';

@Component({
  selector: 'app-matrix-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="container">
      <h2>Matrices annuelles</h2>
      <button class="btn btn-primary" [routerLink]="['/matrix/create']">Créer une nouvelle matrice</button>

      <div *ngIf="matrices.length === 0" class="alert alert-info mt-3">
        Aucune matrice disponible.
      </div>

      <table *ngIf="matrices.length > 0" class="table mt-3">
        <thead>
          <tr>
            <th>Année</th>
            <th>Créée par</th>
            <th>État HOS</th>
            <th>État DG</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let matrix of matrices">
            <td>{{ matrix.year }}</td>
            <td>{{ matrix.createdByUsername }}</td>
            <td>
              <span [ngClass]="matrix.hosValidated ? 'text-success' : 'text-warning'">
                {{ matrix.hosValidated ? 'Validée' : 'En attente' }}
              </span>
            </td>
            <td>
              <span [ngClass]="matrix.dgValidated ? 'text-success' : 'text-warning'">
                {{ matrix.dgValidated ? 'Validée' : 'En attente' }}
              </span>
            </td>
            <td>
              <button class="btn btn-sm btn-info me-2" [routerLink]="['/matrix', matrix.id]">Voir</button>
              <button class="btn btn-sm btn-danger" (click)="deleteMatrix(matrix.id)">Supprimer</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `,
  styles: []
})
export class MatrixListComponent implements OnInit {
  matrices: Matrix[] = [];

  constructor(private matrixService: MatrixService) { }

  ngOnInit(): void {
    this.loadMatrices();
  }

  loadMatrices(): void {
    this.matrixService.getAll().subscribe({
      next: (data) => {
        this.matrices = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des matrices', err);
      }
    });
  }

  deleteMatrix(id?: number): void {
    if (!id) return;

    if (confirm('Êtes-vous sûr de vouloir supprimer cette matrice ?')) {
      this.matrixService.delete(id).subscribe({
        next: () => {
          this.matrices = this.matrices.filter(m => m.id !== id);
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de la matrice', err);
        }
      });
    }
  }
}
