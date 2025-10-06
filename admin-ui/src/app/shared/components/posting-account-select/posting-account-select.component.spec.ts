import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostingAccountSelectComponent } from './posting-account-select.component';

describe('PostingAccountSelectComponent', () => {
  let component: PostingAccountSelectComponent;
  let fixture: ComponentFixture<PostingAccountSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostingAccountSelectComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostingAccountSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
