import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HolidayPeriodListComponent } from './holiday-period-list.component';

describe('HolidayPeriodListComponent', () => {
  let component: HolidayPeriodListComponent;
  let fixture: ComponentFixture<HolidayPeriodListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HolidayPeriodListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HolidayPeriodListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
