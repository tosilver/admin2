package co.b4pay.admin.service;

import co.b4pay.admin.common.biz.service.CrudService;
import co.b4pay.admin.dao.SubscriberDao;
import co.b4pay.admin.entity.Subscriber;
import org.springframework.stereotype.Service;

@Service
public class SubscriberService extends CrudService<SubscriberDao, Subscriber> {

}
