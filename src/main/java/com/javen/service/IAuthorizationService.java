package com.javen.service;

import com.javen.model.Authorization;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jay on 2017/6/21.
 */
@Transactional
public interface IAuthorizationService extends IBaseService {

    Authorization getByRole(int role);

}
