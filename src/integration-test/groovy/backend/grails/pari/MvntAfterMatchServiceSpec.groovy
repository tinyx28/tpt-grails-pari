package backend.grails.pari

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MvntAfterMatchServiceSpec extends Specification {

    MvntAfterMatchService mvntAfterMatchService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MvntAfterMatch(...).save(flush: true, failOnError: true)
        //new MvntAfterMatch(...).save(flush: true, failOnError: true)
        //MvntAfterMatch mvntAfterMatch = new MvntAfterMatch(...).save(flush: true, failOnError: true)
        //new MvntAfterMatch(...).save(flush: true, failOnError: true)
        //new MvntAfterMatch(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //mvntAfterMatch.id
    }

    void "test get"() {
        setupData()

        expect:
        mvntAfterMatchService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MvntAfterMatch> mvntAfterMatchList = mvntAfterMatchService.list(max: 2, offset: 2)

        then:
        mvntAfterMatchList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        mvntAfterMatchService.count() == 5
    }

    void "test delete"() {
        Long mvntAfterMatchId = setupData()

        expect:
        mvntAfterMatchService.count() == 5

        when:
        mvntAfterMatchService.delete(mvntAfterMatchId)
        sessionFactory.currentSession.flush()

        then:
        mvntAfterMatchService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MvntAfterMatch mvntAfterMatch = new MvntAfterMatch()
        mvntAfterMatchService.save(mvntAfterMatch)

        then:
        mvntAfterMatch.id != null
    }
}
