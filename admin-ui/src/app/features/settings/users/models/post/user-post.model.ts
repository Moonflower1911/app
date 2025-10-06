import {RoleEnum} from '../enum/role.enum';

export interface UserPostModel {
  fullName: string;
  email: string;
  mobile: string;
  roles: RoleEnum[];
}
