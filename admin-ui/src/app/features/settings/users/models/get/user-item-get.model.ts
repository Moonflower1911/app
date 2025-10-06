import {RoleEnum} from '../enum/role.enum';
import {AuditGetModel} from '../../../../../shared/models/audit-get.model';

export interface UserItemGetModel {
  id: string;
  fullName: string;
  mobile: string;
  email: string;
  enabled: boolean;
  activated: boolean;
  roles: RoleEnum[];
  audit: AuditGetModel;
}
