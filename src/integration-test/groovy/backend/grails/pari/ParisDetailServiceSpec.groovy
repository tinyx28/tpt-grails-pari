package backend.grails.pari

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ParisDetailServiceSpec extends Specification {

    ParisDetailService parisDetailService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ParisDetail(...).save(flush: true, failOnError: true)
        //new ParisDetail(...).save(flush: true, failOnError: true)
        //ParisDetail parisDetail = new ParisDetail(...).save(flush: true, failOnError: true)
        //new ParisDetail(...).save(flush: true, failOnError: true)
        //new ParisDetail(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //parisDetail.id
    }

    void "test get"() {
        setupData()

        expect:
        parisDetailService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ParisDetail> parisDetailList = parisDetailService.list(max: 2, offset: 2)

        then:
        parisDetailList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        parisDetailService.count() == 5
    }

    void "test delete"() {
        Long parisDetailId = setupData()

        expect:
        parisDetailService.count() == 5

        when:
        parisDetailService.delete(parisDetailId)
        sessionFactory.currentSession.flush()

        then:
        parisDetailService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ParisDetail parisDetail = new ParisDetail()
        parisDetailService.save(parisDetail)

        then:
        parisDetail.id != null
    }
}
