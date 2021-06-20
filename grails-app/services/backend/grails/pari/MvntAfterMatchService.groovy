package backend.grails.pari

import grails.gorm.services.Service

@Service(MvntAfterMatch)
interface MvntAfterMatchService {

    MvntAfterMatch get(Serializable id)

    List<MvntAfterMatch> list(Map args)

    Long count()

    void delete(Serializable id)

    MvntAfterMatch save(MvntAfterMatch mvntAfterMatch)

}