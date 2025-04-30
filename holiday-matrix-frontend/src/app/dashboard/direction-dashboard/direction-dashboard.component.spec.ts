import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DirectionDashboardComponent } from './direction-dashboard.component';

describe('DirectionDashboardComponent', () => {
  let component: DirectionDashboardComponent;
  let fixture: ComponentFixture<DirectionDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DirectionDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DirectionDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
