package backend.grails.pari

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ParisServiceSpec extends Specification {

    ParisService parisService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Paris(...).save(flush: true, failOnError: true)
        //new Paris(...).save(flush: true, failOnError: true)
        //Paris paris = new Paris(...).save(flush: true, failOnError: true)
        //new Paris(...).save(flush: true, failOnError: true)
        //new Paris(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //paris.id
    }

    void "test get"() {
        setupData()

        expect:
        parisService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Paris> parisList = parisService.list(max: 2, offset: 2)

        then:
        parisList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        parisService.count() == 5
    }

    void "test delete"() {
        Long parisId = setupData()

        expect:
        parisService.count() == 5

        when:
        parisService.delete(parisId)
        sessionFactory.currentSession.flush()

        then:
        parisService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Paris paris = new Paris()
        parisService.save(paris)

        then:
        paris.id != null
    }
}
