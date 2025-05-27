
//make en comment le 26/05/20252 a 6:17
/*
export interface User {
  id?: number;
  username: string;
  role: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  department?: string;
  position?: string;
  managerId?: number;
  managerName?: string;
}

export interface UserDetails extends User {
  notificationCount: number;
}*/

export interface User {
  id?: number;
  username: string;
  role: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  department?: string;
  position?: string;
  managerId?: number;
  managerName?: string;
}

export interface UserDetails extends User {
  notificationCount: number;
}

// Classe utilitaire pour les méthodes User (optionnel)
export class UserHelper {
  static getDisplayName(user: User): string {
    if (user.firstName && user.lastName) {
      return `${user.firstName} ${user.lastName}`;
    }
    return user.username;
  }

  static isValidUser(user: User | null): user is User {
    return !!(user && user.id && user.username && user.role);
  }

  static hasRole(user: User | null, role: string): boolean {
    return user?.role?.toLowerCase() === role.toLowerCase();
  }

  static isManager(user: User | null): boolean {
    return UserHelper.hasRole(user, 'MANAGER');
  }

  static isHOS(user: User | null): boolean {
    return UserHelper.hasRole(user, 'HEAD_OF_SERVICE');
  }

  static isDG(user: User | null): boolean {
    return UserHelper.hasRole(user, 'DIRECTION_GENERAL');
  }

  static isEmployee(user: User | null): boolean {
    return UserHelper.hasRole(user, 'EMPLOYEE');
  }
}

