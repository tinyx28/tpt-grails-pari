package backend.grails.pari

import grails.gorm.services.Service

@Service(Paris)
interface ParisService {

    Paris get(Serializable id)

    List<Paris> list(Map args)

    Long count()

    void delete(Serializable id)

    Paris save(Paris paris)

}