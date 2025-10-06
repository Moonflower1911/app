/**
 * Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this file,
 * via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.uaa.service;

import com.smsmode.uaa.model.TokenModel;
import com.smsmode.uaa.model.UserModel;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 25 Dec 2024
 */
public interface TokenService {

    TokenModel generatePasswordToken(UserModel user);

    TokenModel generateAccountValidationToken(UserModel user);
}
