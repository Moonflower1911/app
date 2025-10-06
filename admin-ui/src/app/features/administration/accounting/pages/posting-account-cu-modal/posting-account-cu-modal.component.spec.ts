import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostingAccountCuModalComponent } from './posting-account-cu-modal.component';

describe('PostingAccountCuModalComponent', () => {
  let component: PostingAccountCuModalComponent;
  let fixture: ComponentFixture<PostingAccountCuModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostingAccountCuModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostingAccountCuModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
