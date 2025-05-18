import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HolidayPeriodService } from '../../services/holiday-period.service';
import { AuthService } from '../../services/auth.service';
import { HolidayPeriod } from '../../models/holiday-period';

@Component({
  selector: 'app-holiday-period-list',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  template: `
    <div class="container">
      <h2>Périodes de congés</h2>

      <div *ngIf="isManager" class="card mb-3">
        <div class="card-body">
          <h3 class="card-title">Ajouter une période de congé</h3>
          <form [formGroup]="periodForm" (ngSubmit)="onSubmit()">
            <div class="row">
              <div class="col-md-3 mb-3">
                <label for="name" class="form-label">Nom</label>
                <input type="text" class="form-control" id="name" formControlName="name" placeholder="Ex: Noël 2025">
              </div>
              <div class="col-md-3 mb-3">
                <label for="startDate" class="form-label">Date de début</label>
                <input type="date" class="form-control" id="startDate" formControlName="startDate">
              </div>
              <div class="col-md-3 mb-3">
                <label for="endDate" class="form-label">Date de fin</label>
                <input type="date" class="form-control" id="endDate" formControlName="endDate">
              </div>
              <div class="col-md-3 d-flex align-items-end">
                <button type="submit" class="btn btn-primary w-100" [disabled]="periodForm.invalid">Ajouter</button>
              </div>
            </div>
          </form>
        </div>
      </div>

      <div *ngIf="periods.length === 0" class="alert alert-info">
        Aucune période de congé disponible.
      </div>

      <div class="row">
        <div *ngFor="let period of periods" class="col-md-4 mb-3">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">{{ period.name }}</h5>
              <p class="card-text">Du {{ period.startDate | date:'dd/MM/yyyy' }} au {{ period.endDate | date:'dd/MM/yyyy' }}</p>
              <div *ngIf="isManager" class="d-flex justify-content-between">
                <button class="btn btn-sm btn-info" [routerLink]="['/holiday-planning']" [queryParams]="{period: period.id}">Voir planifications</button>
                <button class="btn btn-sm btn-danger" (click)="deletePeriod(period.id)">Supprimer</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class HolidayPeriodListComponent implements OnInit {
  periods: HolidayPeriod[] = [];
  periodForm!: FormGroup;
  isManager = false;

  constructor(
    private fb: FormBuilder,
    private holidayPeriodService: HolidayPeriodService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadPeriods();

    // Vérifier les rôles
    this.isManager = this.authService.isManager();
  }

  initForm(): void {
    this.periodForm = this.fb.group({
      name: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });
  }

  loadPeriods(): void {
    this.holidayPeriodService.getAll().subscribe({
      next: (data) => {
        this.periods = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des périodes de congé', err);
      }
    });
  }

  onSubmit(): void {
    if (this.periodForm.invalid) return;

    const periodData: HolidayPeriod = {
      name: this.periodForm.value.name,
      startDate: new Date(this.periodForm.value.startDate),
      endDate: new Date(this.periodForm.value.endDate)
    };

    this.holidayPeriodService.create(periodData).subscribe({
      next: (newPeriod) => {
        this.periods.push(newPeriod);
        this.periodForm.reset();
      },
      error: (err) => {
        console.error('Erreur lors de la création de la période de congé', err);
      }
    });
  }

  deletePeriod(id?: number): void {
    if (!id) return;

    if (confirm('Êtes-vous sûr de vouloir supprimer cette période de congé ?')) {
      this.holidayPeriodService.delete(id).subscribe({
        next: () => {
          this.periods = this.periods.filter(p => p.id !== id);
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de la période de congé', err);
        }
      });
    }
  }
}
