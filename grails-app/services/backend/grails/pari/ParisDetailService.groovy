package backend.grails.pari

import grails.gorm.services.Service

@Service(ParisDetail)
interface ParisDetailService {

    ParisDetail get(Serializable id)

    List<ParisDetail> list(Map args)

    Long count()

    void delete(Serializable id)

    ParisDetail save(ParisDetail parisDetail)

}