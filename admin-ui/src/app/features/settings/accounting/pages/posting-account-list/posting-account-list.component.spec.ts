import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostingAccountListComponent } from './posting-account-list.component';

describe('PostingAccountListComponent', () => {
  let component: PostingAccountListComponent;
  let fixture: ComponentFixture<PostingAccountListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostingAccountListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostingAccountListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
