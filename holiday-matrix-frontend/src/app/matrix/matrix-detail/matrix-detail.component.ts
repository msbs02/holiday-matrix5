/*import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MatrixService } from '../../services/matrix.service';
import { AuthService } from '../../services/auth.service';
import { Matrix } from '../../models/matrix';
import {AnnualMatrix} from '../../models/annual-matrix.model';

@Component({
  selector: 'app-matrix-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="container" *ngIf="matrix">
      <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>Matrice Annuelle {{ matrix.year }}</h2>
        <div>
          <button *ngIf="canEdit" class="btn btn-primary me-2" [routerLink]="['/matrix/edit', matrix.id]">Modifier</button>
          <button *ngIf="canValidateHOS && !matrix.hosValidated" class="btn btn-success me-2" (click)="validateHOS()">Valider (HOS)</button>
          <button *ngIf="canValidateDG && !matrix.dgValidated" class="btn btn-success me-2" (click)="validateDG()">Valider (DG)</button>
          <button class="btn btn-secondary" routerLink="/matrix">Retour</button>
        </div>
      </div>

      <div class="card mb-3">
        <div class="card-body">
          <div class="row">
            <div class="col-md-4">
              <p><strong>Créée par:</strong> {{ matrix.createdByUsername }}</p>
              <p><strong>Date de création:</strong> {{ matrix.createdAt | date:'dd/MM/yyyy' }}</p>
            </div>
            <div class="col-md-4">
              <p>
                <strong>État HOS:</strong>
                <span [ngClass]="matrix.hosValidated ? 'text-success' : 'text-warning'">
                  {{ matrix.hosValidated ? 'Validée' : 'En attente' }}
                </span>
              </p>
              <p *ngIf="matrix.hosValidated">
                <strong>Validée par:</strong> {{ matrix.hosValidatedByUsername }}
              </p>
              <p *ngIf="matrix.hosValidated">
                <strong>Date de validation:</strong> {{ matrix.hosValidatedAt | date:'dd/MM/yyyy' }}
              </p>
            </div>
            <div class="col-md-4">
              <p>
                <strong>État DG:</strong>
                <span [ngClass]="matrix.dgValidated ? 'text-success' : 'text-warning'">
                  {{ matrix.dgValidated ? 'Validée' : 'En attente' }}
                </span>
              </p>
              <p *ngIf="matrix.dgValidated">
                <strong>Validée par:</strong> {{ matrix.dgValidatedByUsername }}
              </p>
              <p *ngIf="matrix.dgValidated">
                <strong>Date de validation:</strong> {{ matrix.dgValidatedAt | date:'dd/MM/yyyy' }}
              </p>
            </div>
          </div>
        </div>
      </div>

      <h3>Entrées de la matrice</h3>
      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>Position</th>
              <th>Nombre d'employés</th>
              <th colspan="3" class="text-center">Congés planifiés</th>
              <th colspan="3" class="text-center">Congés non planifiés</th>
            </tr>
            <tr>
              <th></th>
              <th></th>
              <th class="text-danger">Critique</th>
              <th class="text-warning">Moyen</th>
              <th class="text-success">Faible</th>
              <th class="text-danger">Critique</th>
              <th class="text-warning">Moyen</th>
              <th class="text-success">Faible</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let entry of matrix.entries">
              <td>{{ entry.positionTitle }}</td>
              <td>{{ entry.headcount }}</td>
              <td class="text-danger">{{ entry.plannedHolidayCritical }}</td>
              <td class="text-warning">{{ entry.plannedHolidayMedium }}</td>
              <td class="text-success">{{ entry.plannedHolidayLow }}</td>
              <td class="text-danger">{{ entry.nonPlannedHolidayCritical }}</td>
              <td class="text-warning">{{ entry.nonPlannedHolidayMedium }}</td>
              <td class="text-success">{{ entry.nonPlannedHolidayLow }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  `,
  styles: []
})
export class MatrixDetailComponent implements OnInit {
  matrix?: AnnualMatrix; // Changé de Matrix à AnnualMatrix pour correspondre au type du service
  canEdit = false;
  canValidateHOS = false;
  canValidateDG = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private matrixService: MatrixService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.loadMatrix(+params['id']);
      }
    });

    // Définir les permissions
    this.canEdit = this.authService.isManager();
    this.canValidateHOS = this.authService.isHOS();
    this.canValidateDG = this.authService.isDG();
  }

  loadMatrix(id: number): void {
    this.matrixService.getById(id).subscribe({
      next: (data: AnnualMatrix) => {
        this.matrix = data;
      },
      error: (err: any) => {
        console.error('Erreur lors du chargement de la matrice', err);
      }
    });
  }

  validateHOS(): void {
    if (!this.matrix || !this.matrix.id) return;

    this.matrixService.validateByHOS(matrixId, this.matrix.id).subscribe({
      next: (data: AnnualMatrix) => {
        this.matrix = data;
      },
      error: (err: any) => {
        console.error('Erreur lors de la validation HOS', err);
      }
    });
  }

  validateDG(): void {
    if (!this.matrix || !this.matrix.id) return;

    this.matrixService.validateByDG(matrixId, this.matrix.id).subscribe({
      next: (data: AnnualMatrix) => {
        this.matrix = data;
      },
      error: (err: any) => {
        console.error('Erreur lors de la validation DG', err);
      }
    });
  }
}
*/
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormArray } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { MatrixService } from '../../services/matrix.service';
import { PositionService } from '../../services/position.service';
import { Position } from '../../models/position';

@Component({
  selector: 'app-matrix-detail',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  template: `
    <div class="container">
      <h2>{{ editMode ? 'Modifier' : 'Créer' }} une matrice</h2>

      <form [formGroup]="matrixForm" (ngSubmit)="onSubmit()">
        <div class="mb-3">
          <label for="year" class="form-label">Année</label>
          <input type="number" class="form-control" id="year" formControlName="year">
        </div>

        <h3>Entrées de la matrice</h3>
        <div formArrayName="entries">
          <div *ngFor="let entry of entries.controls; let i = index" [formGroupName]="i" class="card mb-3 p-3">
            <div class="mb-3">
              <label [for]="'position-' + i" class="form-label">Position</label>
              <select [id]="'position-' + i" class="form-select" formControlName="positionId">
                <option value="">Sélectionner une position</option>
                <option *ngFor="let position of positions" [value]="position.id">{{ position.title }}</option>
              </select>
            </div>

            <div class="mb-3">
              <label [for]="'headcount-' + i" class="form-label">Nombre d'employés</label>
              <input type="number" class="form-control" [id]="'headcount-' + i" formControlName="headcount">
            </div>

            <div class="row">
              <div class="col-md-4">
                <h4>Congés planifiés</h4>
                <div class="mb-3">
                  <label [for]="'planned-critical-' + i" class="form-label">Critique</label>
                  <input type="number" class="form-control" [id]="'planned-critical-' + i" formControlName="plannedHolidayCritical">
                </div>
                <div class="mb-3">
                  <label [for]="'planned-medium-' + i" class="form-label">Moyen</label>
                  <input type="number" class="form-control" [id]="'planned-medium-' + i" formControlName="plannedHolidayMedium">
                </div>
                <div class="mb-3">
                  <label [for]="'planned-low-' + i" class="form-label">Faible</label>
                  <input type="number" class="form-control" [id]="'planned-low-' + i" formControlName="plannedHolidayLow">
                </div>
              </div>

              <div class="col-md-4">
                <h4>Congés non planifiés</h4>
                <div class="mb-3">
                  <label [for]="'non-planned-critical-' + i" class="form-label">Critique</label>
                  <input type="number" class="form-control" [id]="'non-planned-critical-' + i" formControlName="nonPlannedHolidayCritical">
                </div>
                <div class="mb-3">
                  <label [for]="'non-planned-medium-' + i" class="form-label">Moyen</label>
                  <input type="number" class="form-control" [id]="'non-planned-medium-' + i" formControlName="nonPlannedHolidayMedium">
                </div>
                <div class="mb-3">
                  <label [for]="'non-planned-low-' + i" class="form-label">Faible</label>
                  <input type="number" class="form-control" [id]="'non-planned-low-' + i" formControlName="nonPlannedHolidayLow">
                </div>
              </div>

              <div class="col-md-4 d-flex align-items-center">
                <button type="button" class="btn btn-danger btn-sm" (click)="removeEntry(i)">Supprimer</button>
              </div>
            </div>
          </div>

          <button type="button" class="btn btn-secondary mb-3" (click)="addEntry()">Ajouter une entrée</button>
        </div>

        <div>
          <button type="submit" class="btn btn-primary me-2" [disabled]="matrixForm.invalid">{{ editMode ? 'Mettre à jour' : 'Créer' }}</button>
          <button type="button" class="btn btn-secondary" routerLink="/matrix">Annuler</button>
        </div>
      </form>
    </div>
  `,
  styles: []
})
export class MatrixDetailComponent implements OnInit {
  matrixForm!: FormGroup;
  positions: Position[] = [];
  editMode = false;
  matrixId?: number;

  constructor(
    private fb: FormBuilder,
    private matrixService: MatrixService,
    private positionService: PositionService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadPositions();

    // Vérifier s'il s'agit d'une mise à jour
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.editMode = true;
        this.matrixId = +params['id'];
        this.loadMatrixData();
      }
    });
  }

  get entries() {
    return this.matrixForm.get('entries') as FormArray;
  }

  initForm(): void {
    this.matrixForm = this.fb.group({
      year: [new Date().getFullYear(), [Validators.required, Validators.min(2000), Validators.max(2100)]],
      entries: this.fb.array([])
    });

    // Ajouter une entrée par défaut
    this.addEntry();
  }

  loadPositions(): void {
    this.positionService.getAll().subscribe({
      next: (data) => {
        this.positions = data;
      },
      error: (err: Error) => {
        console.error('Erreur lors du chargement des positions', err);
      }
    });
  }

  loadMatrixData(): void {
    if (!this.matrixId) return;

    this.matrixService.getById(this.matrixId).subscribe({
      next: (matrix) => {
        this.matrixForm.patchValue({
          year: matrix.year
        });

        // Nettoyer les entrées existantes
        while (this.entries.length > 0) {
          this.entries.removeAt(0);
        }

        // Ajouter les entrées de la matrice
        if (matrix.entries && matrix.entries.length > 0) {
          matrix.entries.forEach(entry => {
            this.entries.push(this.createEntryFormGroup(entry));
          });
        } else {
          this.addEntry();
        }
      },
      error: (err: Error) => {
        console.error('Erreur lors du chargement de la matrice', err);
      }
    });
  }

  createEntryFormGroup(entry?: any): FormGroup {
    return this.fb.group({
      id: [entry?.id],
      positionId: [entry?.positionId || '', Validators.required],
      headcount: [entry?.headcount || 0, [Validators.required, Validators.min(0)]],
      plannedHolidayCritical: [entry?.plannedHolidayCritical || 0, [Validators.min(0)]],
      plannedHolidayMedium: [entry?.plannedHolidayMedium || 0, [Validators.min(0)]],
      plannedHolidayLow: [entry?.plannedHolidayLow || 0, [Validators.min(0)]],
      nonPlannedHolidayCritical: [entry?.nonPlannedHolidayCritical || 0, [Validators.min(0)]],
      nonPlannedHolidayMedium: [entry?.nonPlannedHolidayMedium || 0, [Validators.min(0)]],
      nonPlannedHolidayLow: [entry?.nonPlannedHolidayLow || 0, [Validators.min(0)]]
    });
  }

  addEntry(): void {
    this.entries.push(this.createEntryFormGroup());
  }

  removeEntry(index: number): void {
    this.entries.removeAt(index);
  }

  onSubmit(): void {
    if (this.matrixForm.invalid) return;

    const matrixData: any = {
      year: this.matrixForm.value.year,
      hosValidated: false,
      dgValidated: false,
      entries: this.matrixForm.value.entries
    };

    if (this.editMode && this.matrixId) {
      // En mode édition
      // Redirection après traitement de toutes les entrées
      let processedEntries = 0;
      const totalEntries = matrixData.entries?.length || 0;

      if (totalEntries > 0) {
        matrixData.entries?.forEach((entry: any) => {
          // Ici nous supposons que MatrixService a une méthode pour sauvegarder une entrée
          // Si cette méthode n'existe pas, il faudra l'adapter à votre service réel
          this.saveEntry(entry, this.matrixId).subscribe({
            next: () => {
              processedEntries++;
              if (processedEntries === totalEntries) {
                this.router.navigate(['/matrix']);
              }
            },
            error: (err: Error) => {
              console.error('Erreur lors de la sauvegarde de l\'entrée', err);
            }
          });
        });
      } else {
        // S'il n'y a pas d'entrées, rediriger directement
        this.router.navigate(['/matrix']);
      }
    } else {
      // En mode création
      this.matrixService.createMatrix(matrixData).subscribe({
        next: () => {
          this.router.navigate(['/matrix']);
        },
        error: (err: Error) => {
          console.error('Erreur lors de la création de la matrice', err);
        }
      });
    }
  }

  // Méthode pour sauvegarder une entrée
  // Cette méthode est abstraite et doit être adaptée à votre service réel
  saveEntry(entry: any, matrixId: number | undefined): any {
    // Si l'entrée a un ID, c'est une mise à jour
    if (entry.id) {
      return this.matrixService.validateByHOS(matrixId, entry.id); // À adapter
    } else {
      // Sinon c'est une nouvelle entrée
      return this.matrixService.validateByDG(matrixId, entry.id); // À adapter
    }
    // Note: Il est fort probable que ces méthodes ne soient pas les bonnes.
    // Vous devez les remplacer par les méthodes appropriées de votre service.
  }

  // Si vous avez besoin de cette méthode pour créer une matrice
  createMatrix(matrixData: any): any {
    return this.matrixService.validateByHOS(matrixData.id, matrixData); // À adapter
  }
}
