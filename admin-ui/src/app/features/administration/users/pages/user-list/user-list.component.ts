import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule, DatePipe, TitleCasePipe } from '@angular/common';
import { TranslatePipe } from '@ngx-translate/core';
import { ToastrService } from 'ngx-toastr';
import { BsModalService } from 'ngx-bootstrap/modal';
import { Subscription } from 'rxjs';
import {
  AvatarComponent,
  ButtonDirective,
  ColComponent,
  FormControlDirective,
  InputGroupComponent,
  InputGroupTextDirective,
  RowComponent,
  SpinnerComponent,
  TableDirective
} from '@coreui/angular';
import { IconDirective } from '@coreui/icons-angular';
import { cilSearch, cilPen, cilTrash } from '@coreui/icons';
import { TooltipDirective } from 'ngx-bootstrap/tooltip';


import { UserCuModalComponent } from '../user-cu-modal/user-cu-modal.component';
import { TableControlComponent } from '../../../../../shared/components/table-control/table-control.component';
import { BadgeComponent } from '../../../../../shared/components/badge/badge.component';
import { EmptyDataComponent } from '../../../../../shared/components/empty-data/empty-data.component';
import { ConfirmModalComponent } from '../../../../../shared/components/confirm-modal/confirm-modal.component';
import { AuditNamePipe } from '../../../../../shared/pipes/audit-name.pipe';
import { PhoneFormatPipe } from '../../../../../shared/pipes/phone-format.pipe';
import { UtilsService } from '../../../../../shared/services/utils.service';
import {UserItemGetModel} from '../../models/get/user-item-get.model';
import {UserApiService} from '../../services/user-api.service';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    CommonModule,
    TranslatePipe,
    RowComponent,
    ColComponent,
    ButtonDirective,
    InputGroupComponent,
    InputGroupTextDirective,
    IconDirective,
    FormControlDirective,
    TableControlComponent,
    TableDirective,
    AvatarComponent,
    BadgeComponent,
    DatePipe,
    AuditNamePipe,
    TooltipDirective,
    EmptyDataComponent,
    SpinnerComponent
  ],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  providers: [BsModalService]
})
export class UserListComponent implements OnInit, OnDestroy {

  icons = { cilSearch, cilPen, cilTrash };

  users: UserItemGetModel[] = [];
  isLoading = false;
  isSearchActive = false;
  firstCallDone = false;
  isListEmpty = true;

  // Pagination
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  currentNumberOfElements = 0;
  hasNext = false;
  hasPrevious = false;

  // Search
  searchValue = '';

  private subscriptions: Subscription[] = [];

  constructor(
    private readonly userApiService: UserApiService,
    private readonly toastrService: ToastrService,
    private readonly modalService: BsModalService
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  loadUsers(): void {
    this.isLoading = true;

    this.subscriptions.push(
      this.userApiService.getUsersByPage(
        this.currentPage,
        this.pageSize,
        'modifiedAt',
        'desc',
        this.searchValue || undefined
      ).subscribe({
        next: (response) => {
          this.users = response.content;
          this.totalElements = response.totalElements;
          this.totalPages = response.totalPages;
          this.currentNumberOfElements = response.pageable.offset + response.numberOfElements;
          this.isListEmpty = response.empty;
          this.hasNext = !response.last;
          this.hasPrevious = !response.first;
          this.isLoading = false;
          this.firstCallDone = true;
        },
        error: (error) => {
          console.error('Error loading users:', error);
          this.toastrService.error(
            'An error occurred while loading users',
            'Loading Error'
          );
          this.isLoading = false;
          this.firstCallDone = true;
        }
      })
    );
  }

  onSearch(event: any): void {
    const value = (event.target as HTMLInputElement)?.value?.trim() || '';

    if (value.length >= 3 || value.length === 0) {
      this.searchValue = value;
      this.isSearchActive = value.length > 0;
      this.currentPage = 0;
      this.loadUsers();
    }
  }

  changeSize(newSize: number): void {
    this.pageSize = newSize;
    this.currentPage = 0;
    this.loadUsers();
  }

  nextPage(): void {
    if (this.hasNext) {
      this.currentPage++;
      this.loadUsers();
    }
  }

  previousPage(): void {
    if (this.hasPrevious) {
      this.currentPage--;
      this.loadUsers();
    }
  }

  openUserCuModal(user?: UserItemGetModel): void {
    const initialState = user ? { userToEdit: user } : {};

    const modalRef = this.modalService.show(UserCuModalComponent, {
      initialState
    });

    this.subscriptions.push(
      (modalRef.content as UserCuModalComponent).actionConfirmed.subscribe(() => {
        this.loadUsers();
      })
    );
  }

  deleteUser(user: UserItemGetModel): void {
    const initialState = {
      title: 'Delete User',
      message: `Are you sure you want to delete the user "${user.fullName}"? This action cannot be undone.`
    };

    const confirmModalRef = this.modalService.show(ConfirmModalComponent, { initialState });

    this.subscriptions.push(
      (confirmModalRef.content as ConfirmModalComponent).actionConfirmed.subscribe(() => {
        this.performDeleteUser(user);
      })
    );
  }

  private performDeleteUser(user: UserItemGetModel): void {
    this.subscriptions.push(
      this.userApiService.deleteUser(user.id).subscribe({
        next: () => {
          this.loadUsers();
          this.toastrService.success(
            `User "${user.fullName}" has been successfully deleted`,
            'User Deleted'
          );
        },
        error: (error) => {
          console.error('Error deleting user:', error);
          const errorMessage = error?.error?.detail ||
            'An error occurred while deleting the user. Please try again.';
          this.toastrService.error(errorMessage, 'Delete Failed');
        }
      })
    );
  }

  getNameInitials(name: string): string {
    return UtilsService.getNameInitials(name);
  }

  getRoleLabel(role: string): string {
    return role.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, l => l.toUpperCase());
  }
}
