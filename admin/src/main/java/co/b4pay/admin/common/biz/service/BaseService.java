package co.b4pay.admin.common.biz.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Service基类
 *
 * @author cc
 */
@Transactional(readOnly = true)
public interface BaseService {
}
