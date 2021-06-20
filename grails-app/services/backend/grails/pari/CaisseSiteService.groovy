package backend.grails.pari

import grails.gorm.services.Service

@Service(CaisseSite)
interface CaisseSiteService {

    CaisseSite get(Serializable id)

    List<CaisseSite> list(Map args)

    Long count()

    void delete(Serializable id)

    CaisseSite save(CaisseSite caisseSite)

}