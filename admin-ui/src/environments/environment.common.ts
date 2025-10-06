export const UAA_SERVICE = 'authMgtApi/';
export const UNIT_SERVICE = 'unitMgtApi/';
export const GUEST_SERVICE = 'guestMgtApi/';
export const MEDIA_SERVICE = 'mediaMgtApi/';
export const PRICING_SERVICE = 'rateMgtApi/'
export const CRM_SERVICE = 'guestMgtApi/';
export const BOOKING_SERVICE = 'bookingMgtApi/';
export const INVOICE_SERVICE = 'invoiceMgtApi/';
export const commonProperties = {
  /*===========UAA Service===========*/
  // authentication resource
  login: UAA_SERVICE + 'login',
  forgotPassword: UAA_SERVICE + 'forgot-password',
  refreshToken: UAA_SERVICE + 'refresh-token',
  resetPassword: UAA_SERVICE + 'reset-password',
  changePassword: UAA_SERVICE + 'change-password',
  validateAccount: UAA_SERVICE + 'validate-account',
  // user resource
  userList: UAA_SERVICE + 'users',
  userById: UAA_SERVICE + 'users/:userId',

  /*===========UNIT Service===========*/
  // Unit resource
  unitList: UNIT_SERVICE + 'units',
  unitById: UNIT_SERVICE + 'units/:unitId',
  unitInfosById: UNIT_SERVICE + 'units/:unitId/infos',
  unitDetailsById: UNIT_SERVICE + 'units/:unitId/details',
  unitInstructionsById: UNIT_SERVICE + 'units/:unitId/inst',
  unitRoomsById: UNIT_SERVICE + 'units/:unitId/rooms',
  unitRoomById: UNIT_SERVICE + 'units/:unitId/rooms/:roomId',
  //unit subUnits resource
  unitSubUnits: UNIT_SERVICE + 'units/:unitId/sub-units',
  unitDetach: UNIT_SERVICE + 'units/:unitId/detach',
  // Image resource
  unitImages: UNIT_SERVICE + 'images',
  unitImageById: UNIT_SERVICE + 'images/:imageId',

  /*===========Booking===========*/
  inventory: UNIT_SERVICE + 'inventory',
  booking: BOOKING_SERVICE + 'bookings',
  bookingById: BOOKING_SERVICE + 'bookings/:bookingId',
  bookingItems: BOOKING_SERVICE + 'bookings/:parentId/items',
  bookingDrafts: BOOKING_SERVICE + 'bookings/drafts',
  bookingList: BOOKING_SERVICE + 'bookings',

  /*===========Rates===========*/
  ratePlanList: PRICING_SERVICE + 'rate-plans',
  ratePlanById: PRICING_SERVICE + 'rate-plans/:ratePlanId',
  rateList: PRICING_SERVICE + 'rates',
  extraGuestChargeList: PRICING_SERVICE + 'additional-guest-strategies',
  extraGuestChargeById: PRICING_SERVICE + 'additional-guest-strategies/:additionalGuestStrategyId',
  chargeList: PRICING_SERVICE + 'charges',
  chargeById: PRICING_SERVICE + 'charges/:chargeId',
  inclusionList: PRICING_SERVICE + 'inclusions',
  inclusionById: PRICING_SERVICE + 'inclusions/:inclusionId',
  ratePlanUnitList: PRICING_SERVICE + 'rate-plan-units',
  ratePlanUnitById: PRICING_SERVICE+'rate-plan-units/:ratePlanUnitId',
  cancellationPolicyList: PRICING_SERVICE+'cancellation-policies',
  cancellationPolicyById: PRICING_SERVICE+'cancellation-policies/:cancellationPolicyId',

  // Default resource
  unitBaseRateById: PRICING_SERVICE + 'default-rates',
  // Rate plans
  RatePlanList: PRICING_SERVICE + 'rate-plans',
  RatePlanById: PRICING_SERVICE + 'rate-plans/:ratePlanId',
  // Rate plans
  RateTable: PRICING_SERVICE + 'rate-tables',
  feeCalculate: PRICING_SERVICE + 'calculate-fee',
  //Rate management

  /*===========Fees===========*/
  // Fee resource
  fees: PRICING_SERVICE + 'fees',
  feesApply: PRICING_SERVICE + 'fees/copyTo',

  /*===========MEDIA Service===========*/
  mediaById: MEDIA_SERVICE + 'medias/:mediaId',

  /*===========CRM Service===========*/
  segmentList: CRM_SERVICE + 'segments',
  segmentById: CRM_SERVICE + 'segments/:segmentId',

  // Party resource
  partyList: GUEST_SERVICE + 'parties',
  partyById: GUEST_SERVICE + 'parties/:partyId',

  // Document resource
  identityDocuments: GUEST_SERVICE + 'documents',
  identityDocumentById: GUEST_SERVICE + 'documents/:identityDocumentId',
  identityDocumentImageById: GUEST_SERVICE + 'documents/:identityDocumentId/image',

  // Document image resource
  idDocumentImages: GUEST_SERVICE + 'images',
  idDocumentImageById: GUEST_SERVICE + 'images/:imageId',

  /*===========INVOICE Service===========*/
  invoiceGeneratePdf: INVOICE_SERVICE + 'invoices/generate-pdf/:bookingId',
  accountClassList: INVOICE_SERVICE + 'account-classes',
  accountClassById: INVOICE_SERVICE + 'account-classes/:accountClassId',
  ledgerGroupList: INVOICE_SERVICE + 'ledger-groups',
  ledgerGroupById: INVOICE_SERVICE + 'ledger-groups/:ledgerGroupId',
  postingAccountList: INVOICE_SERVICE + 'posting-accounts',
  postingAccountById: INVOICE_SERVICE + 'posting-accounts/:postingAccountId',
  vatRuleList: INVOICE_SERVICE + 'vat-rules',
  vatRuleById: INVOICE_SERVICE + 'vat-rules/:vatRuleId',
}
