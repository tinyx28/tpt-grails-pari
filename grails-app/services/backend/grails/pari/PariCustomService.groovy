package backend.grails.pari

import grails.gorm.transactions.Transactional

@Transactional
class PariCustomService {

    def getNotPayed(Serializable id){
        return Paris.createCriteria().list(){
            eq("isPayed", false)
            and {
                eq("idUser", id.toLong())
            }
        }
    }

    def getBetInProgressByIdUser(long iduser){
        return Paris.executeQuery("Select count(pd.id) as number from Paris p join ParisDetail pd on p.id = pd.paris.id where p.idUser =:id and p.isPayed = false", [id: iduser])[0]
    }

    def getBetInProgress(){
        return Paris.executeQuery("Select count(pd.id) as number from Paris p join ParisDetail pd on p.id = pd.paris.id where p.isPayed = false")[0]
    }

    def mostBet(){
        return ParisDetail.executeQuery("select count(idTeamParie), idTeamParie from ParisDetail GROUP BY idTeamParie")
    }

    // à retester
    def udpateStatusIsFinishedByIdMatch(long idmatch){
        return ParisDetail.executeUpdate("UPDATE ParisDetail p set p.isFinished = true where p.idMatch =:idmatch", [idmatch: idmatch]);
    }

    def findAllPariDetailByIdmatch(long idmatch){
        return ParisDetail.findAllByIdMatch(idmatch)
    }

}
